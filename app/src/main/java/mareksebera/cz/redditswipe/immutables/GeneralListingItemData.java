package mareksebera.cz.redditswipe.immutables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListingItemData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListingItemData {
    String getSubreddit();

    String getTitle();

    String getThumbnail();

    String getAuthor();

    String getUrl();

    @JsonProperty(value = "is_video", defaultValue = "false")
    Boolean getIsVideo();

    @JsonProperty(value = "post_hint")
    String getPostHint();

    Optional<ImmutableItemDataMedia> getMedia();

    class Builder extends ImmutableGeneralListingItemData.Builder {
    }
}
