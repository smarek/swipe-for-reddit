package mareksebera.cz.redditswipe.core;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import mareksebera.cz.redditswipe.fragments.BasicItemFragment;
import mareksebera.cz.redditswipe.fragments.ImageItemFragment;
import mareksebera.cz.redditswipe.fragments.TextItemFragment;
import mareksebera.cz.redditswipe.fragments.VideoItemFragment;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListing;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListingItem;

public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private boolean loaded = false;
    private WeakReference<Context> ctx;
    private String baseUrl;
    private ArrayList<ImmutableGeneralListingItem> items = new ArrayList<>();
    private ImmutableGeneralListing lastResponse = null;
    private PagerAdapterLoader redditLoader;

    public SwipeViewPagerAdapter(Context ctx, FragmentManager fm, String baseUrl) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.ctx = new WeakReference<>(ctx);
        this.baseUrl = baseUrl;
        redditLoader = new PagerAdapterLoader();
        redditLoader.load(ctx, baseUrl, new PagerAdapterLoader.LoaderCallback() {
            @Override
            public void success(@NonNull ImmutableGeneralListing response) {
                loaded = true;
                SwipeViewPagerAdapter.this.items.addAll(response.getData().getChildren());
                SwipeViewPagerAdapter.this.notifyDataSetChanged();
                SwipeViewPagerAdapter.this.lastResponse = response;
            }

            @Override
            public void failure(@NonNull VolleyError error) {
                loaded = false;
            }
        });
    }

    private RedditItem internalGetItem(int position) {
        RedditItem ri = RedditItem.DUMMY;
        if (items.size() > position) {
            ri = RedditItem.fromGeneralListingItem(items.get(position));

            if (position >= (items.size() - 3)) {
                loadMore();
            }
        }
        return ri;
    }

    private void loadMore() {
        redditLoader.loadMore(ctx.get(), baseUrl, lastResponse.getData().getAfter(), new PagerAdapterLoader.LoaderCallback() {
            @Override
            public void success(@NonNull ImmutableGeneralListing response) {
                SwipeViewPagerAdapter.this.items.addAll(response.getData().getChildren());
                SwipeViewPagerAdapter.this.notifyDataSetChanged();
                SwipeViewPagerAdapter.this.lastResponse = response;
            }

            @Override
            public void failure(@NonNull VolleyError error) {

            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("PagerAdapter", "notifyDataSetChanged, new count " + items.size() + ", after: " + (lastResponse != null ? lastResponse.getData().getAfter() : ""));
    }

    @Override
    public Fragment getItem(int i) {
        RedditItem item = internalGetItem(i);
        Fragment fragment;
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        switch (item.TYPE) {
            default:
            case TYPE_DUMMY:
                fragment = new BasicItemFragment();
                break;
            case TYPE_TEXT:
                fragment = new TextItemFragment();
                break;
            case TYPE_IMAGE:
                fragment = new ImageItemFragment();
                break;
            case TYPE_VIDEO:
                fragment = new VideoItemFragment();
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return loaded ? items.size() : 0;
    }
}
