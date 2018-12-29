package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListingItemData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListingItemData extends Serializable {
    String getSubreddit();

    String getTitle();

    @Nullable
    @JsonProperty("selftext")
    String getSelftext();

    @Nullable
    @JsonProperty("selftext_html")
    String getSelftextHtml();

    @JsonProperty(value = "is_self")
    boolean getIsSelf();

    @Nullable
    String getThumbnail();

    String getAuthor();

    String getUrl();

    @JsonProperty(value = "is_video", defaultValue = "false")
    @Nullable
    Boolean getIsVideo();

    @JsonProperty(value = "post_hint")
    @Nullable
    String getPostHint();

    @JsonProperty(value = "media")
    @Nullable
    ImmutableItemDataMedia getMedia();

    @JsonProperty(value = "secure_media")
    @Nullable
    ImmutableItemDataMedia getSecureMedia();

    class Builder extends ImmutableGeneralListingItemData.Builder {
    }
}
