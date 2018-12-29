package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;

import static mareksebera.cz.redditswipe.core.RedditItemType.TYPE_DUMMY;

public abstract class CommonItemFragment extends Fragment {

    TextView type, subreddit, title, author, url, dummy_url;
    RedditItem item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(RedditItem.getLayoutResourceIdForType(item == null ? TYPE_DUMMY : item.TYPE), container, false);

        type = v.findViewById(R.id.common_fragment_type);
        subreddit = v.findViewById(R.id.common_fragment_subreddit);
        title = v.findViewById(R.id.common_fragment_title);
        author = v.findViewById(R.id.common_fragment_author);
        url = v.findViewById(R.id.common_fragment_url);

        dummy_url = v.findViewById(R.id.dummy_fragment_url);

        if (item != null) {
            type.setText(item.TYPE.toString());
            subreddit.setText(String.format("r/%s", item.DATA.getSubreddit()));
            title.setText(item.DATA.getTitle());
            author.setText(String.format("u/%s", item.DATA.getAuthor()));
            url.setText(item.DATA.getUrl());

            if (dummy_url != null) {
                dummy_url.setText(item.DATA.getUrl());
            }

            Log.d("CommonItemFragment", String.format("format:%s, url:%s", item.TYPE.toString(), item.DATA.getUrl()));
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        item = (RedditItem) args.get("item");
    }
}
