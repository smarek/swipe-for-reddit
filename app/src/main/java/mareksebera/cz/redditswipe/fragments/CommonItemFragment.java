package mareksebera.cz.redditswipe.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;

import static mareksebera.cz.redditswipe.core.RedditItemType.TYPE_DUMMY;

public abstract class CommonItemFragment extends Fragment {

    String TAG = "CommonItemFragment";
    TextView type, subreddit, title, author, url, dummy_url;
    RedditItem item;
    boolean isUserVisible = false;
    SimpleDraweeView dummy_thumbnail;
    final int MENU_ITEM_OPEN_EXTERNALLY = -1, MENU_ITEM_OPEN_COMMENTS = -2;

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
        dummy_thumbnail = v.findViewById(R.id.dummy_fragment_thumbnail);

        type.setVisibility(View.GONE);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        if (item != null) {
            type.setText(item.TYPE.toString());
            subreddit.setText(String.format("r/%s", item.DATA.getSubreddit()));
            title.setText(item.DATA.getTitle());
            author.setText(String.format("u/%s", item.DATA.getAuthor()));
            url.setText(item.DATA.getUrl());

            if (dummy_url != null) {
                dummy_url.setText(item.DATA.getUrl());
            }

            if (dummy_thumbnail != null) {
                if (item.DATA.getPreviewData() != null) {
                    String url = item.DATA.getPreviewData().getImages().get(0).getSource().getUrl().replaceAll("&amp;", "&");
                    dummy_thumbnail.setImageURI(url);
                } else if (item.DATA.getThumbnail() != null && URLUtil.isValidUrl(item.DATA.getThumbnail())) {
                    dummy_thumbnail.setImageURI(item.DATA.getThumbnail());
                } else {
                    dummy_thumbnail.setImageResource(R.drawable.ic_link);
                }
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu.findItem(MENU_ITEM_OPEN_EXTERNALLY) == null) {
            menu.add(Menu.NONE, MENU_ITEM_OPEN_EXTERNALLY, Menu.NONE, "Open externally")
                    .setIcon(R.drawable.ic_menu_open_in_new)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        if (menu.findItem(MENU_ITEM_OPEN_COMMENTS) == null) {
            menu.add(Menu.NONE, MENU_ITEM_OPEN_COMMENTS, Menu.NONE, "Open Comments")
                    .setIcon(R.drawable.ic_comments)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case MENU_ITEM_OPEN_EXTERNALLY:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.DATA.getUrl()));
                startActivity(browserIntent);
                return true;
            case MENU_ITEM_OPEN_COMMENTS:
                CommentsFragment f = CommentsFragment.newInstance(item);
                f.show(getChildFragmentManager(), f.getTag());
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        isUserVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isUserVisible = false;
    }
}
