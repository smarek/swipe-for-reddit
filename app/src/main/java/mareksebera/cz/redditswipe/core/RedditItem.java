package mareksebera.cz.redditswipe.core;

import java.io.Serializable;

public class RedditItem implements Serializable {
    public RedditItemType TYPE;

    public static final RedditItem DUMMY = new RedditItem();

    public RedditItem() {
        TYPE = RedditItemType.TYPE_DUMMY;
    }
}
