package mareksebera.cz.redditswipe.core;

import android.support.annotation.NonNull;

import java.io.Serializable;

import mareksebera.cz.redditswipe.immutables.GeneralListingItemData;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItem;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItemData;

public class RedditItem implements Serializable {
    public final RedditItemType TYPE;
    public final ImmutableGeneralListingItemData DATA;

    public static final RedditItem DUMMY = new RedditItem();

    public RedditItem() {
        TYPE = RedditItemType.TYPE_DUMMY;
        DATA = new GeneralListingItemData.Builder().author("DUMMY").thumbnail("DUMMY").subreddit("DUMMY").title("DUMY").url("DUMMY").isVideo(false).build();
    }

    public RedditItem(@NonNull RedditItemType type, @NonNull ImmutableGeneralListingItemData data) {
        TYPE = type;
        DATA = data;
    }

    public static RedditItem fromGeneralListingItem(ImmutableGeneralListingItem immutableGeneralListingItem) {
        return new RedditItem(detectType(immutableGeneralListingItem), immutableGeneralListingItem.getData());
    }

    @NonNull
    private static RedditItemType detectType(ImmutableGeneralListingItem immutableGeneralListingItem) {
        RedditItemType rit = RedditItemType.TYPE_DUMMY;
        if (immutableGeneralListingItem.getData().getIsVideo()) {
            rit = RedditItemType.TYPE_VIDEO;
        } else if ("t3".equals(immutableGeneralListingItem.getKind())) {
            rit = RedditItemType.TYPE_IMAGE;
        }
        return rit;
    }
}
