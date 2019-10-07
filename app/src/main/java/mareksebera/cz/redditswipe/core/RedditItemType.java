package mareksebera.cz.redditswipe.core;

import android.util.Log;

import androidx.annotation.NonNull;

import java.net.URI;
import java.util.Locale;

import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItem;

public enum RedditItemType {
    TYPE_DUMMY,
    TYPE_IMAGE,
    TYPE_VIDEO,
    TYPE_TEXT,
    TYPE_LINK,
    TYPE_IMAGE_ALBUM;

    public static RedditItemType classify(ImmutableGeneralListingItem item) {
        URI uri;
        try {
            uri = URI.create(item.getData().getUrl());
        } catch (Exception | Error e) {
            Log.e("RedditItemType", "classify", e);
            return TYPE_DUMMY;
        }
        if (isSelfText(item)) {
            return TYPE_TEXT;
        }
        if (isYoutubeVideo(uri)) {
            return TYPE_LINK;
        }
        if (isGif(uri)) {
            return TYPE_IMAGE;
        }
        if (isGifLoadInstantly(uri)) {
            return TYPE_IMAGE;
        }
        if (isImage(uri)) {
            return TYPE_IMAGE;
        }
        if (isVideo(uri)) {
            return TYPE_VIDEO;
        }
        if (isImageAlbum(uri)) {
            return TYPE_IMAGE_ALBUM;
        }
        return TYPE_DUMMY;
    }

    public static boolean isGfycat(URI uri) {
        final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
        return hostContains(host, "gfycat.com");
    }

    public static boolean isSelfText(@NonNull ImmutableGeneralListingItem item) {
        return item.getData().getIsSelf() && (item.getData().getSelftext() != null | item.getData().getSelftextHtml() != null);
    }

    public static boolean isGifLoadInstantly(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            boolean pathIndicator = path.endsWith(".gif") || path.endsWith(
                    ".webm") || path.endsWith(".mp4");

            return (hostContains(host, "gfycat.com") && pathIndicator) || (hostContains(host, "v.redd.it") && pathIndicator) || (
                    hostContains(host, "imgur.com") && pathIndicator);

        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isGif(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            return (hostContains(host, "gfycat.com")
                    || hostContains(host, "v.redd.it")) &&
                    path.endsWith(".gif");

        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isVideo(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            return ((hostContains(host, "gfycat.com") || hostContains(host, "v.redd.it")) && !path.endsWith(".gif"))
                    || path.endsWith(".mp4") || path.endsWith(".webm") || path.endsWith(".gifv");

        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isYoutubeVideo(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            return hostContains(host, "youtu.be", "youtube.com",
                    "youtube.co.uk") && !path.contains("/user/") && !path.contains("/channel/");

        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isImageAlbum(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            return hostContains(host, "imgur.com", "bildgur.de") && (path.startsWith("/a/")
                    || path.startsWith("/gallery/")
                    || path.startsWith("/g/")
                    || path.contains(","));

        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isImage(URI uri) {
        try {
            final String host = uri.getHost().toLowerCase(Locale.ENGLISH);
            final String path = uri.getPath().toLowerCase(Locale.ENGLISH);

            return host.equals("i.reddituploads.com") || path.endsWith(".png") || path.endsWith(
                    ".jpg") || path.endsWith(".jpeg");

        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks if {@code host} is contains by any of the provided {@code bases}
     * <p/>
     * For example "www.youtube.com" contains "youtube.com" but not "notyoutube.com" or
     * "youtube.co.uk"
     *
     * @param host  A hostname from e.g. {@link URI#getHost()}
     * @param bases Any number of hostnames to compare against {@code host}
     * @return If {@code host} contains any of {@code bases}
     */
    public static boolean hostContains(String host, String... bases) {
        if (host == null || host.isEmpty()) return false;

        for (String base : bases) {
            if (base == null || base.isEmpty()) continue;

            final int index = host.lastIndexOf(base);
            if (index < 0 || index + base.length() != host.length()) continue;
            if (base.length() == host.length() || host.charAt(index - 1) == '.') return true;
        }

        return false;
    }

}
