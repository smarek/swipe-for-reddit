package mareksebera.cz.redditswipe.immutables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableItemDataMedia.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemDataMedia {

    @JsonProperty("reddit_video")
    Optional<ImmutableItemDataMediaVideo> getRedditVideo();
}
