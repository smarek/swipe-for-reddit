package mareksebera.cz.redditswipe.fragments;

import android.net.Uri;
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

        if (videoView != null && item != null) {
            videoView.setOnPreparedListener(this);

            String url = RedditItem.getImageVideoUrl(item.DATA.getUrl());

            if (item.DATA.getMedia().isPresent() && item.DATA.getMedia().get().getRedditVideo().isPresent()) {
                ImmutableItemDataMediaVideo iidmv = item.DATA.getMedia().get().getRedditVideo().get();
                if (iidmv.getDashUrl() != null) {
                    url = iidmv.getDashUrl();
                } else if (iidmv.getHlsUrl() != null) {
                    url = iidmv.getHlsUrl();
                } else if (iidmv.getFallbackUrl() != null) {
                    url = iidmv.getFallbackUrl();
                }
            }
            Log.d("VideoItemFragment", String.format("final chosen url: %s", url));
            videoView.setPreviewImage(Uri.parse(item.DATA.getThumbnail()));
            videoView.setVideoPath(url);
        }

        return v;
    }

    @Override
    public void onPrepared() {
        videoView.setRepeatMode(RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL);
        videoView.start();
    }
}
