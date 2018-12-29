package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralListingItem.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralListingItem {
    @NonNull
    String getKind();

    ImmutableGeneralListingItemData getData();

    class Builder extends ImmutableGeneralListingItem.Builder {
    }
}
