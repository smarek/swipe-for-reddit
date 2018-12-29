package mareksebera.cz.redditswipe;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class RedditSwipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
