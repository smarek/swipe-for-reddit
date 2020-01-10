package mareksebera.cz.redditswipe.core;

import android.util.Log;

import androidx.annotation.Nullable;

import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItemData;
import mareksebera.cz.redditswipe.immutables.ImmutableItemDataMediaVideo;

public final class UrlUtils {

    @Nullable
    public static ImmutableItemDataMediaVideo getVideoFromData(ImmutableGeneralListingItemData DATA) {
        ImmutableItemDataMediaVideo iidmv = null;
        if (DATA.getSecureMedia() != null && DATA.getSecureMedia().getRedditVideo() != null) {
            iidmv = DATA.getSecureMedia().getRedditVideo();
        } else if (DATA.getMedia() != null && DATA.getMedia().getRedditVideo() != null) {
            iidmv = DATA.getMedia().getRedditVideo();
        }
        return iidmv;
    }

    public static String extractRedditMediaUrl(RedditItem item) {
        String url = null;
        ImmutableItemDataMediaVideo iidmv = getVideoFromData(item.DATA);

        if (iidmv == null && item.DATA.getCrosspostParentList() != null && !item.DATA.getCrosspostParentList().isEmpty()) {
            int counter = 0;
            while (iidmv == null) {
                iidmv = getVideoFromData(item.DATA.getCrosspostParentList().get(counter));
                counter++;
            }
        }

        if (iidmv != null) {
            Log.d("UrlUtils.extractReddit", String.format("%s %s %s", iidmv.getDashUrl(), iidmv.getHlsUrl(), iidmv.getFallbackUrl()));
            if (null != iidmv.getDashUrl()) {
                url = iidmv.getDashUrl();
            } else if (null != iidmv.getHlsUrl()) {
                url = iidmv.getHlsUrl();
            } else if (null != iidmv.getFallbackUrl()) {
                url = iidmv.getFallbackUrl();
            }
        }

        if (url == null) {
            Log.d("UrlUtils.extractReddit", String.format("%s %s", item.DATA.getMedia(), item.DATA.getSecureMedia()));
            url = item.DATA.getUrl();
        }

        return url;
    }

}
