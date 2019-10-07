package mareksebera.cz.redditswipe.immutables;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListing.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListing extends Serializable {
    @NonNull
    String getKind();

    @NonNull
    ImmutableGeneralData getData();

    class Builder extends ImmutableGeneralListing.Builder {
    }
}
