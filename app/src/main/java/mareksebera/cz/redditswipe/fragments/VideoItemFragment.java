package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.util.RepeatModeUtil;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;
import mareksebera.cz.redditswipe.immutables.ImmutableItemDataMediaVideo;

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
        }
    }

    protected void loadVideo() {
        if (isUserVisible && videoView != null && item != null) {
            videoView.setOnPreparedListener(this);

            String url = RedditItem.getImageVideoUrl(item.DATA.getUrl());

            Log.d("VideoItemFragment", String.format("url:%s, media:%b reddit_video:%b", item.DATA.getUrl(), item.DATA.getMedia() == null, item.DATA.getSecureMedia() == null));
            if ((item.DATA.getMedia() != null && item.DATA.getMedia().getRedditVideo() != null) || (item.DATA.getSecureMedia() != null && item.DATA.getSecureMedia().getRedditVideo() != null)) {
                ImmutableItemDataMediaVideo iidmv = item.DATA.getMedia() == null ? item.DATA.getSecureMedia().getRedditVideo() : item.DATA.getMedia().getRedditVideo();
                if (iidmv == null) {
                    Log.e("VideoItemFragment", "iidmv null, fackup");
                } else {
                    if (null != iidmv.getDashUrl()) {
                        url = iidmv.getDashUrl();
                    } else if (iidmv.getHlsUrl() != null) {
                        url = iidmv.getHlsUrl();
                    } else if (iidmv.getFallbackUrl() != null) {
                        url = iidmv.getFallbackUrl();
                    }
                }
            }
            Log.d("VideoItemFragment", String.format("final chosen url: %s", url));
            videoView.setVideoPath(url);
        }

        Log.d("VideoItemFragment", String.format("loadVideo isUSerVisible:%b", isUserVisible));
    }

    @Override
    public void onPrepared() {
        videoView.setRepeatMode(RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL);
        if (isUserVisible) {
            videoView.start();
        } else {
            Log.d("VideoItemFragment", "not starting video");
        }
    }
}
