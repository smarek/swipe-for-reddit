package mareksebera.cz.redditswipe.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;

import mareksebera.cz.redditswipe.R;

public class TextItemFragment extends CommonItemFragment {

    TextView selfText, titleText;
    WebView selfHtml;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        assert v != null;

        selfText = v.findViewById(R.id.text_fragment_selftext);
        titleText = v.findViewById(R.id.text_fragment_title);
        selfHtml = v.findViewById(R.id.text_fragment_webview);

        title.setVisibility(View.GONE);
        selfText.setVisibility(View.GONE);
        selfHtml.setVisibility(View.GONE);

        if (item != null) {
            titleText.setText(item.DATA.getTitle());
            if (item.DATA.getSelftextHtml() != null) {
                selfHtml.setVisibility(View.VISIBLE);
                selfHtml.getSettings().setDefaultTextEncodingName("utf-8");
                selfHtml.setBackgroundColor(Color.TRANSPARENT);

                String html = "<html><head><style type=\"text/css\"> a { word-wrap: break-word; color: white; text-decoration: underline;} html,body {color: white;} </style></head><body>";
                html += StringEscapeUtils.unescapeHtml4(Jsoup.parse(item.DATA.getSelftextHtml()).text());
                html += "</body></html>";

                selfHtml.loadData(html, "text/html; charset=utf-8", "utf-8");
            } else {
                selfText.setVisibility(View.VISIBLE);
                selfText.setText(item.DATA.getSelftext());
            }
        }

        return v;
    }
}
