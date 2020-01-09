package mareksebera.cz.redditswipe;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import mareksebera.cz.redditswipe.dao.ObjectBox;

public class RedditSwipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ObjectBox.init(this);
    }
}
