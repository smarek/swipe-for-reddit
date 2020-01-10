package mareksebera.cz.redditswipe.immutables;


import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;

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

    @JsonProperty(value = "crosspost_parent_list")
    @Nullable
    List<ImmutableGeneralListingItemData> getCrosspostParentList();

    @JsonProperty(value = "preview")
    @Nullable
    ImmutablePreviewData getPreviewData();

    class Builder extends ImmutableGeneralListingItemData.Builder {
    }
}
