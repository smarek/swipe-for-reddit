package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mareksebera.cz.redditswipe.R;

public class TextItemFragment extends CommonItemFragment {

    TextView selfText, titleText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        selfText = v.findViewById(R.id.text_fragment_selftext);
        titleText = v.findViewById(R.id.text_fragment_title);

        title.setVisibility(View.GONE);

        if (item != null) {
            titleText.setText(item.DATA.getTitle());
            if (item.DATA.getSelftext() != null) {
                selfText.setText(item.DATA.getSelftext());
            } else {
                selfText.setText(Html.fromHtml(item.DATA.getSelftextHtml()));
            }
        }

        return v;
    }
}
