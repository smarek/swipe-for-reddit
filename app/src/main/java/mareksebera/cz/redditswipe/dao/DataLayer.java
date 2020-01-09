package mareksebera.cz.redditswipe.dao;

import android.util.Log;

import io.objectbox.Box;
import io.objectbox.exception.UniqueViolationException;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItem;

public class DataLayer {

    private static final String LOG_NAME = "DataLayer";

    public static Box<Subreddit> getSubredditsBox() {
        return ObjectBox.get().boxFor(Subreddit.class);
    }

    public static int saveSubredditNames(Iterable<ImmutableGeneralListingItem> items) {
        int count = 0;
        Box<Subreddit> subredditsBox = getSubredditsBox();
        for (ImmutableGeneralListingItem item : items) {
            count++;
            try {
                subredditsBox.put(Subreddit.withName(item.getData().getSubreddit()));
            } catch (UniqueViolationException e) {
                // ignore
            }
        }

        return count;
    }

}
