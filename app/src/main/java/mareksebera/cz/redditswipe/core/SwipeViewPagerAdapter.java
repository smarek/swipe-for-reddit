package mareksebera.cz.redditswipe.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.volley.VolleyError;

import mareksebera.cz.redditswipe.fragments.BasicItemFragment;
import mareksebera.cz.redditswipe.fragments.ImageItemFragment;
import mareksebera.cz.redditswipe.fragments.TextItemFragment;
import mareksebera.cz.redditswipe.fragments.VideoItemFragment;
import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListing;

public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private boolean loaded = false;
    private int currentCount = 0;
    private ImmutableGeneralListing response = null;
    private PagerAdapterLoader redditLoader;

    public SwipeViewPagerAdapter(Context ctx, FragmentManager fm, String baseUrl) {
        super(fm);
        redditLoader = new PagerAdapterLoader();
        redditLoader.load(ctx, baseUrl, new PagerAdapterLoader.LoaderCallback() {
            @Override
            public void success(@NonNull ImmutableGeneralListing response) {
                loaded = true;
                SwipeViewPagerAdapter.this.response = response;
                SwipeViewPagerAdapter.this.currentCount += response.getData().getChildren().size();
                SwipeViewPagerAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void failure(@NonNull VolleyError error) {
                loaded = false;
            }
        });
    }

    private RedditItem internalGetItem(int position) {
        RedditItem ri = RedditItem.DUMMY;
        if (response != null &&
                response.getData() != null &&
                response.getData().getChildren() != null &&
                response.getData().getChildren().size() > position) {
            ri = RedditItem.fromGeneralListingItem(response.getData().getChildren().get(position));
        }
        return ri;
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
        return loaded ? currentCount : 1;
    }
}
