package mareksebera.cz.redditswipe;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import mareksebera.cz.redditswipe.core.SwipeViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private final int MENU_ITEM_FULLSCREEN = 1;
    private boolean isFullscreen = true;
    private DrawerLayout mDrawerLayout;
    private Menu mSideMenu;
    private ViewPager mViewPager;

    NavigationView.OnNavigationItemSelectedListener sideMenuItemSelectedListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case MENU_ITEM_FULLSCREEN:
                toggleFullscreen(MainActivity.this, isFullscreen);
                isFullscreen = !isFullscreen;
                return true;
            case 1337:
                setCurrentAdapterUrl("https://www.reddit.com/r/all.json");
                return true;
            case 1338:
                setCurrentAdapterUrl("https://www.reddit.com/r/funny.json");
                return true;
            case 1339:
                setCurrentAdapterUrl("https://www.reddit.com/r/gifs.json");
                return true;

        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);

        mViewPager = findViewById(R.id.viewpager);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        setCurrentAdapterUrl("https://www.reddit.com/r/all.json");

        mNavigationView.setNavigationItemSelectedListener(sideMenuItemSelectedListener);
        mSideMenu = mNavigationView.getMenu();
        mSideMenu.clear();
        createSideMenu();
    }

    private void setCurrentAdapterUrl(String url) {
        FragmentStatePagerAdapter mViewPagerAdapter = new SwipeViewPagerAdapter(
                this,
                getSupportFragmentManager(),
                url
        );
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private void createSideMenu() {
        mSideMenu.add(Menu.NONE, MENU_ITEM_FULLSCREEN, Menu.NONE, "Toggle fullscreen")
                .setIcon(R.drawable.ic_fullscreen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
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
        boolean isImmersiveModeEnabled = false;
        isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(activity.getPackageName(), "Turning immersive mode mode off. ");
        } else {
            Log.i(activity.getPackageName(), "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        // Status bar hiding: Backwards compatible to Jellybean
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);


        try {
            ActionBar ab = activity.getSupportActionBar();
            if (ab != null) {
                if (fullscreen) {
                    ab.hide();
                } else {
                    ab.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
