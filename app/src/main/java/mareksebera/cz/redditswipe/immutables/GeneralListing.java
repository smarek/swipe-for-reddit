package mareksebera.cz.redditswipe.immutables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListing.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListing extends Serializable {
    String getKind();

    ImmutableGeneralData getData();

    class Builder extends ImmutableGeneralListing.Builder {
    }
}
