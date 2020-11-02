package mareksebera.cz.redditswipe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.toolbox.Volley;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.util.RepeatModeUtil;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;
import mareksebera.cz.redditswipe.core.RedditItemType;
import mareksebera.cz.redditswipe.core.SwipeVideoControls;
import mareksebera.cz.redditswipe.core.UrlUtils;
import mareksebera.cz.redditswipe.core.VolleyHeadRequest;
import mareksebera.cz.redditswipe.core.VolleyJacksonRequest;
import mareksebera.cz.redditswipe.immutables.ImmutableGfycatBase;

public class VideoItemFragment extends CommonItemFragment implements OnPreparedListener {

    private VideoView videoView;
    private TextView videoSize;
    private boolean isVideoPrepared = false;
    private String chosenVideoUrl = null;
    private long chosenVideoSize = 0;
    private long maximumVideoSizeAutoLoad = 314572800;
    private Handler callbackHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        videoView = v.findViewById(R.id.video_fragment_video);
        videoSize = v.findViewById(R.id.video_fragment_size);

        videoView.setRepeatMode(RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL);
        videoView.setControls(new SwipeVideoControls(v.getContext()));

        loadVideo();

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            if (isVideoPrepared) {
                videoView.start();
            } else {
                loadVideo();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    private void loadVideo() {
        if (videoView != null && item != null) {
            videoView.setOnPreparedListener(this);

            String url = RedditItem.getImageVideoUrl(item.DATA.getUrl());
            URI uri = URI.create(url);

            if (!RedditItemType.isGifLoadInstantly(uri)) {

                if (RedditItemType.isGfycat(uri)) {
                    Log.d("loadVideo", "loading GFYCAT");
                    loadGfycat();
                    return;
                } else if (RedditItemType.isRedgifs(uri)) {
                    Log.d("loadVideo", "loading REDGIFS");
                    loadRedgifs();
                    return;
                } else {
                    Log.d(TAG, "Not gfycat");
                }

                url = UrlUtils.extractRedditMediaUrl(item);
            }
            Log.d("VideoItemFragment", String.format("final chosen url: %s", url));
            chosenVideoUrl = url;
            if (chosenVideoSize == 0) {
                checkVideoSize();
            } else {
                videoView.setVideoPath(chosenVideoUrl);
            }

        }
    }

    private void checkVideoSize() {
        Volley.newRequestQueue(requireContext()).add(new VolleyHeadRequest(chosenVideoUrl, error -> {
            // fallback, do not process error, just pass it on to video player
            afterVideoSizeRetrieved();
        }) {

            @Override
            public void deliverHeaders(List<Header> allHeaders) {
                for (Header h : allHeaders) {
                    if ("content-length".equalsIgnoreCase(h.getName())) {
                        chosenVideoSize = Long.parseLong(h.getValue());
                        break;
                    }
                }
                callbackHandler.post(() -> afterVideoSizeRetrieved());
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void afterVideoSizeRetrieved() {
        double readableVideoSize = chosenVideoSize == 0 ? 0 : round(chosenVideoSize / (1024 * 1024), 1);
        if (chosenVideoSize == 0 || chosenVideoSize <= maximumVideoSizeAutoLoad) {
            Log.d(TAG, "video load proceed, choseVideoSize: " + readableVideoSize);
            videoView.setVideoPath(chosenVideoUrl);
        } else {
            Log.d(TAG, "Video bigger then max auto allows, " + readableVideoSize);
        }
        if (readableVideoSize != 0) {
            videoSize.setText(String.format("%.2f MB", readableVideoSize));
            videoSize.setVisibility(View.VISIBLE);
        }
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private void loadGfycat() {
        String gfycatApiUrl = item.DATA.getUrl();
        Pattern p = Pattern.compile(".*gfycat.com\\/(gifs\\/detail\\/)?([\\w]+).*");
        Matcher m = p.matcher(gfycatApiUrl);
        if (m.matches()) {
            gfycatApiUrl = "https://api.gfycat.com/v1/gfycats/" + m.group(2);
        } else {
            Pattern p2 = Pattern.compile(".*thumbs.gfycat.com/([\\w]+)-.*");
            Matcher m2 = p2.matcher(gfycatApiUrl);
            if (m2.matches()) {
                gfycatApiUrl = "https://api.gfycat.com/v1/gfycats/" + m2.group(2);
            }
        }
        Log.d(TAG, "gfycat url: " + gfycatApiUrl);
        Volley.newRequestQueue(requireContext()).add(new VolleyJacksonRequest<ImmutableGfycatBase>(gfycatApiUrl, error -> Log.d("loadGfycat", "error", error), ImmutableGfycatBase.class) {
            @Override
            protected void deliverResponse(ImmutableGfycatBase response) {
                Log.d("loadGfycat", "response: " + response.getGfyItem().getMP4Url());
                videoView.setVideoPath(response.getGfyItem().getMP4Url());
            }
        });
    }

    private void loadRedgifs() {
        String redgifsApiUrl = item.DATA.getUrl();
        Pattern p = Pattern.compile(".*redgifs.com\\/(watch\\/)?([\\w]+).*");
        Matcher m = p.matcher(redgifsApiUrl);
        if (m.matches()) {
            redgifsApiUrl = "https://api.redgifs.com/v1/gfycats/" + m.group(2);
            Log.d(TAG, "redgifs url: " + redgifsApiUrl);
            Volley.newRequestQueue(requireContext()).add(new VolleyJacksonRequest<ImmutableGfycatBase>(redgifsApiUrl, error -> Log.d("loadGfycat", "error", error), ImmutableGfycatBase.class) {
                @Override
                protected void deliverResponse(ImmutableGfycatBase response) {
                    Log.d("loadGfycat", "response: " + response.getGfyItem().getMP4Url());
                    videoView.setVideoPath(response.getGfyItem().getMP4Url());
                }
            });
        }
    }

    @Override
    public void onPrepared() {
        this.isVideoPrepared = true;
        if (isUserVisible && videoView != null) {
            videoView.start();
        }
    }
}
