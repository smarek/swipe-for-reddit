package mareksebera.cz.redditswipe.immutables;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableGeneralData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GeneralData extends Serializable {
    String getModhash();

    int getDist();

    @Nullable
    String getAfter();

    @Nullable
    String getBefore();

    List<ImmutableGeneralListingItem> getChildren();

    class Builder extends ImmutableGeneralData.Builder {
    }
}
