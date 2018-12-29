package mareksebera.cz.redditswipe.immutables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableItemDataMediaVideo.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemDataMediaVideo {
    @JsonProperty("fallback_url")
    String getFallbackUrl();

    @JsonProperty("dash_url")
    String getDashUrl();

    @JsonProperty("hls_url")
    String getHlsUrl();

    @JsonProperty("is_gif")
    Boolean isGif();

    @JsonProperty("transcoding_status")
    String getTranscodingStatus();
}
