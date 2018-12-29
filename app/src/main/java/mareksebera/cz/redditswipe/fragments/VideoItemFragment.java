package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.Volley;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.util.RepeatModeUtil;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;
import mareksebera.cz.redditswipe.core.RedditItemType;
import mareksebera.cz.redditswipe.core.UrlUtils;
import mareksebera.cz.redditswipe.core.VolleyJacksonRequest;
import mareksebera.cz.redditswipe.immutables.ImmutableGfycatBase;

public class VideoItemFragment extends CommonItemFragment implements OnPreparedListener {

    VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        videoView = v.findViewById(R.id.video_fragment_video);

        loadVideo();

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadVideo();
        } else {
            if (videoView != null) {
                videoView.stopPlayback();
            }
        }
    }

    protected void loadVideo() {
        if (isUserVisible && videoView != null && item != null) {
            videoView.setOnPreparedListener(this);

            String url = RedditItem.getImageVideoUrl(item.DATA.getUrl());
            URI uri = URI.create(url);

            if (!RedditItemType.isGifLoadInstantly(uri)) {

                if (RedditItemType.isGfycat(uri)) {
                    Log.d("loadVideo", "loading GFYCAT");
                    loadGfycat();
                    return;
                }

                url = UrlUtils.extractRedditMediaUrl(item);
            }
            Log.d("VideoItemFragment", String.format("final chosen url: %s", url));
            videoView.setVideoPath(url);
        }

        Log.d("VideoItemFragment", String.format("loadVideo isUSerVisible:%b", isUserVisible));
    }

    protected void loadGfycat() {
        String gfycatApiUrl = item.DATA.getUrl();
        Pattern p = Pattern.compile(".*gfycat.com\\/(gifs\\/detail\\/)?(\\w+)");
        Matcher m = p.matcher(gfycatApiUrl);
        if (m.matches()) {
            gfycatApiUrl = "https://api.gfycat.com/v1/gfycats/" + m.group(2);
        }
        Volley.newRequestQueue(getContext()).add(new VolleyJacksonRequest<ImmutableGfycatBase>(gfycatApiUrl, error -> Log.d("loadGfycat", "error", error), ImmutableGfycatBase.class) {
            @Override
            protected void deliverResponse(ImmutableGfycatBase response) {
                Log.d("loadGfycat", "response: " + response.getGfyItem().getMP4Url());
                videoView.setVideoPath(response.getGfyItem().getMP4Url());
            }
        });
    }

    @Override
    public void onPrepared() {
        if (isUserVisible) {
            if (videoView != null) {
                videoView.setRepeatMode(RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL);
                videoView.start();
            }
        } else {
            Log.d("VideoItemFragment", "not starting video");
        }
    }
}
