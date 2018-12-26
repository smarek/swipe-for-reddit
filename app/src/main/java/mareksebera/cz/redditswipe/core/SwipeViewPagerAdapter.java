package mareksebera.cz.redditswipe.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import mareksebera.cz.redditswipe.fragments.BasicItemFragment;
import mareksebera.cz.redditswipe.fragments.ImageItemFragment;
import mareksebera.cz.redditswipe.fragments.TextItemFragment;
import mareksebera.cz.redditswipe.fragments.VideoItemFragment;

public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private boolean loaded = false;
    private ArrayList<RedditItem> items = new ArrayList<>();

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        RedditItem item = items.size() > i ? items.get(i) : RedditItem.DUMMY;
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
        return loaded ? items.size() : 1;
    }
}
