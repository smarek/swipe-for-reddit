package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutablePreviewDataImages.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PreviewDataImages extends Serializable {

    @Nullable
    @JsonProperty("source")
    ImmutableImageData getSource();

    @Nullable
    @JsonProperty("resolutions")
    List<ImmutableImageData> getResolutions();

    @NonNull
    @JsonProperty("id")
    String getID();

}
