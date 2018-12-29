package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableGfycatItem.class)
public interface GfycatItem extends Serializable {

    @Nullable
    @JsonProperty("mp4Url")
    String getMP4Url();

    @Nullable
    @JsonProperty("webmUrl")
    String getWEBMUrl();

    @Nullable
    @JsonProperty("webpUrl")
    String getWEBPUrl();

}
