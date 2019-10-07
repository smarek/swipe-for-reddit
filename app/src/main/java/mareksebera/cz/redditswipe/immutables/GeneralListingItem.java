package mareksebera.cz.redditswipe.immutables;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListingItem.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListingItem extends Serializable {
    @NonNull
    String getKind();

    @NonNull
    ImmutableGeneralListingItemData getData();

    class Builder extends ImmutableGeneralListingItem.Builder {
    }
}
