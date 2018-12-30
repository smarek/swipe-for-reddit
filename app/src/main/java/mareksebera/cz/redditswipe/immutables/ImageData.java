package mareksebera.cz.redditswipe.immutables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableImageData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ImageData extends Serializable {

    @JsonProperty("width")
    int getWidth();

    @JsonProperty("height")
    int getHeight();

    @JsonProperty("url")
    String getUrl();

}
