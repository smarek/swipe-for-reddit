package mareksebera.cz.redditswipe;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mareksebera.cz.redditswipe.core.SwipeViewPagerAdapter;
import mareksebera.cz.redditswipe.fragments.CommentsFragment;

public class MainActivity extends AppCompatActivity {

    private final int MENU_ITEM_FULLSCREEN = 1;
    private boolean isFullscreen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);

        ViewPager mViewPager = findViewById(R.id.viewpager);
        NavigationView mNavigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        FragmentStatePagerAdapter mViewPagerAdapter = new SwipeViewPagerAdapter(
                this,
                getSupportFragmentManager(),
                "https://reddit.com/r/all.json"
        );
        mViewPager.setAdapter(mViewPagerAdapter);

        Menu mSideMenu = mNavigationView.getMenu();
        mSideMenu.clear();
    }

    public void openCommentsFragment() {
        CommentsFragment commentsFragment = new CommentsFragment();
        commentsFragment.show(getSupportFragmentManager(), commentsFragment.getTag());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.findItem(MENU_ITEM_FULLSCREEN) == null) {
            menu.add(Menu.NONE, MENU_ITEM_FULLSCREEN, Menu.NONE, "Toggle fullscreen")
                    .setIcon(R.drawable.ic_fullscreen)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openCommentsFragment();
                //mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case MENU_ITEM_FULLSCREEN:
                toggleFullscreen(this, isFullscreen);
                isFullscreen = !isFullscreen;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * toggles fullscreen mode
     * <br/>
     * REQUIRE: android:configChanges="orientation|screenSize"
     * <pre>
     * sample:
     *     private boolean fullscreen;
     *     ................
     *     Activity activity = (Activity)context;
     *     toggleFullscreen(activity, !fullscreen);
     *     fullscreen = !fullscreen;
     * </pre>
     */
    private void toggleFullscreen(MainActivity activity, boolean fullscreen) {
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                (Build.VERSION.SDK_INT <= 18) && ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(activity.getPackageName(), "Turning immersive mode mode off. ");
        } else {
            Log.i(activity.getPackageName(), "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT > 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);


        try {
            // hide actionbar
            if (fullscreen) {
                activity.getSupportActionBar().hide();
            } else {
                activity.getSupportActionBar().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
