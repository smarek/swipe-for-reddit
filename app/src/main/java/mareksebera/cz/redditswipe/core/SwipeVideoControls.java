package mareksebera.cz.redditswipe.core;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.devbrackets.android.exomedia.ui.widget.VideoControlsCore;
import com.devbrackets.android.exomedia.ui.widget.VideoControlsMobile;

import mareksebera.cz.redditswipe.R;

public class SwipeVideoControls extends VideoControlsMobile implements VideoControlsCore {

    private ImageButton controls_fullscreen, controls_mute;
    private boolean muted = false;
    public static final String TAG = "SwipeVideoControls";

    public SwipeVideoControls(Context context) {
        super(context);
    }

    public SwipeVideoControls(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeVideoControls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwipeVideoControls(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.swipe_video_controls;
    }

    @Override
    protected void retrieveViews() {
        super.retrieveViews();
        controls_fullscreen = findViewById(R.id.exomedia_controls_fullscreen_btn);
        controls_mute = findViewById(R.id.exomedia_controls_mute_btn);
    }

    @Override
    protected void registerListeners() {
        super.registerListeners();
        controls_fullscreen.setOnClickListener(v -> onToggleFullscreen());
        controls_mute.setOnClickListener(v -> onToggleSound());
    }

    @Override
    public void finishLoading() {
        super.finishLoading();

        controls_fullscreen.setVisibility(View.VISIBLE);
        controls_mute.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(boolean initialLoad) {
        super.showLoading(initialLoad);

        controls_fullscreen.setVisibility(View.GONE);
        controls_mute.setVisibility(View.GONE);
    }

    @Override
    protected void onPlayPauseClick() {
        super.onPlayPauseClick();
    }

    protected void onToggleSound() {
        if (videoView != null) {
            videoView.setVolume(muted ? 1.0f : 0.0f);
            muted = !muted;
            controls_mute.setImageResource(muted ? R.drawable.ic_volume_off : R.drawable.ic_volume_on);
        } else {
            Log.d(TAG, "videoView is null, cannot toggle sound");
        }
    }

    protected void onToggleFullscreen() {

    }
}
