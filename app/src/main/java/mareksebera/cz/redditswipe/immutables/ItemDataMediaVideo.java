package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableItemDataMediaVideo.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemDataMediaVideo extends Serializable {
    @JsonProperty("fallback_url")
    String getFallbackUrl();

    @JsonProperty("dash_url")
    @Nullable
    String getDashUrl();

    @JsonProperty("hls_url")
    @Nullable
    String getHlsUrl();

    @JsonProperty("is_gif")
    @Nullable
    Boolean isGif();

    @JsonProperty("transcoding_status")
    @Nullable
    String getTranscodingStatus();
}
