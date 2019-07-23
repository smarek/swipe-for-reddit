package mareksebera.cz.redditswipe.core;

import android.util.Log;

import mareksebera.cz.redditswipe.immutables.ImmutableItemDataMediaVideo;

public final class UrlUtils {

    public static String extractRedditMediaUrl(RedditItem item) {
        String url = null;

        if ((item.DATA.getMedia() != null && item.DATA.getMedia().getRedditVideo() != null) || (item.DATA.getSecureMedia() != null && item.DATA.getSecureMedia().getRedditVideo() != null)) {
            ImmutableItemDataMediaVideo iidmv = item.DATA.getMedia() == null ? item.DATA.getSecureMedia().getRedditVideo() : item.DATA.getMedia().getRedditVideo();
            if (iidmv == null) {
                Log.e("UrlUtils.extractReddit", "iidmv null, fackup");
            } else {
                Log.d("UrlUtils.extractReddit", String.format("%s %s %s",iidmv.getDashUrl(),iidmv.getHlsUrl(),iidmv.getFallbackUrl()));
                if (null != iidmv.getDashUrl()) {
                    url = iidmv.getDashUrl();
                } else if (iidmv.getHlsUrl() != null) {
                    url = iidmv.getHlsUrl();
                } else if (iidmv.getFallbackUrl() != null) {
                    url = iidmv.getFallbackUrl();
                }
            }
        }
        if (url == null) {
            url = item.DATA.getUrl();
        }
        return url;
    }

}
