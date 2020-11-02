package mareksebera.cz.redditswipe.immutables;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind", include = JsonTypeInfo.As.EXTERNAL_PROPERTY, defaultImpl = Object.class)
    @JsonSubTypes(value = {
            @JsonSubTypes.Type(value = ImmutableGeneralData.class, name = "Listing"),
            @JsonSubTypes.Type(value = ImmutableGeneralListingItem.class, name = "t3")
    })
    ImmutableGeneralData getData();

    class Builder extends ImmutableGeneralListing.Builder {
    }
}
