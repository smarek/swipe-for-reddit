package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;


@Value.Immutable
@JsonDeserialize(as = ImmutablePreviewData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PreviewData extends Serializable {

    @JsonProperty("images")
    @Nullable
    List<ImmutablePreviewDataImages> getImages();

    boolean getEnabled();
}
