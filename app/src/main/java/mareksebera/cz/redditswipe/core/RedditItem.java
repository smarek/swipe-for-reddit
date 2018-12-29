package mareksebera.cz.redditswipe.core;

import android.support.annotation.NonNull;

import java.io.Serializable;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.immutables.GeneralListingItemData;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItem;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItemData;

public class RedditItem implements Serializable {
    public static final RedditItem DUMMY = new RedditItem();
    public final RedditItemType TYPE;
    public final ImmutableGeneralListingItemData DATA;

    public RedditItem() {
        TYPE = RedditItemType.TYPE_DUMMY;
        DATA = new GeneralListingItemData.Builder()
                .author("DUMMY")
                .thumbnail("DUMMY")
                .subreddit("DUMMY")
                .title("DUMY")
                .url("DUMMY")
                .isVideo(false)
                .postHint("t39")
                .isSelf(false)
                .build();
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
        return RedditItemType.classify(immutableGeneralListingItem);
    }

    public static String getImageVideoUrl(String url) {

        if (url.contains("imgur.com") && url.endsWith(".gifv")) {
            return url.replace(".gifv", ".mp4");
        }
        return url;
    }

    public static int getLayoutResourceIdForType(@NonNull RedditItemType rit) {
        switch (rit) {
            case TYPE_VIDEO:
                return R.layout.video_fragment;
            case TYPE_IMAGE:
                return R.layout.image_fragment;
            default:
            case TYPE_LINK:
            case TYPE_DUMMY:
                return R.layout.dummy_fragment;
            case TYPE_TEXT:
                return R.layout.text_fragment;
        }
    }
}
