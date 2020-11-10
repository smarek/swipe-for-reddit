package mareksebera.cz.redditswipe;

import android.app.Application;

import com.devbrackets.android.exomedia.ExoMedia;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import mareksebera.cz.redditswipe.dao.ObjectBox;

public class RedditSwipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ObjectBox.init(this);
        ExoMedia.setDataSourceFactoryProvider((userAgent, listener) -> new DefaultHttpDataSourceFactory(userAgent, listener, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true));
    }
}
