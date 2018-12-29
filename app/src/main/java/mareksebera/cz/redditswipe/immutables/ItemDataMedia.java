package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableItemDataMedia.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemDataMedia extends Serializable {

    @JsonProperty("reddit_video")
    @Nullable
    ImmutableItemDataMediaVideo getRedditVideo();
}
