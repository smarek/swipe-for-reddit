package mareksebera.cz.redditswipe;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mareksebera.cz.redditswipe.core.SwipeViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mViewPager = findViewById(R.id.viewpager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        mViewPagerAdapter = new SwipeViewPagerAdapter(
                this,
                getSupportFragmentManager(),
                "https://reddit.com/r/JackAndJill+jillingcumsluts+Hecumsshecums+Amateur+Blowjobs+creampies+cumsluts+doublepenetration+dirtysmall+facesitting+NSFW_GIF+nsfw_gifs+GirlsFinishingTheJob+nsfwhardcore+omgbeckylookathiscock+porn_gifs+pornvids+asslick+choking+throatpies+spanking+HappyEmbarrassedGirls+RealGirls+AsiansGoneWild+SheLikesItRough+cfnm+LovingFamily+2for1.json"
//                "https://www.reddit.com/r/funny+gifs+videos+all+nsfw+gonewildaudio+gonewild+jokes+cybersecurity+czech+economy+EOD+europe+geopolitics.json"
        );
        mViewPager.setAdapter(mViewPagerAdapter);

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
}
