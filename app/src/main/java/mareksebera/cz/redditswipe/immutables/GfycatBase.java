package mareksebera.cz.redditswipe.immutables;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableGfycatBase.class)
public interface GfycatBase extends Serializable {

    @JsonProperty("gfyItem")
    @NonNull
    ImmutableGfycatItem getGfyItem();

}
