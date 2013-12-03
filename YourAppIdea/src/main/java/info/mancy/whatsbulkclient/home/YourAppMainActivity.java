package info.mancy.whatsbulkclient.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import info.mancy.android.ui.navdrawer.AbstractNavDrawerActivity;
import info.mancy.android.ui.navdrawer.NavDrawerActivityConfiguration;
import info.mancy.android.ui.navdrawer.NavDrawerAdapter;
import info.mancy.android.ui.navdrawer.NavDrawerItem;
import info.mancy.android.ui.navdrawer.NavMenuItem;
import info.mancy.android.ui.navdrawer.NavMenuSection;
import info.mancy.whatsbulkclient.NavigationController;
import info.mancy.whatsbulkclient.R;
import info.mancy.whatsbulkclient.YourApplication;
import info.mancy.whatsbulkclient.lists.ListsListFragment;
import info.mancy.whatsbulkclient.donations.DonateFragment;
import info.mancy.whatsbulkclient.friends.FriendMainFragment;
import info.mancy.whatsbulkclient.map.SimpleMapFragment;
import info.mancy.whatsbulkclient.tutorial.TutorialListFragment;

import javax.inject.Inject;

public class YourAppMainActivity extends AbstractNavDrawerActivity {

    @Inject
    NavigationController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YourApplication) getApplication()).inject(this);

        if (savedInstanceState == null) {
            this.navController.goHomeFragment(this);
            this.navController.confirmEulaAndShowChangeLog(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
        NavDrawerItem[] menu = new NavDrawerItem[] {
                NavMenuSection.create(100, "General"),
                NavMenuItem.create(104, "Lists", "navdrawer_tutorial", true, true, this),
                NavMenuItem.create(101, "Single Send", "navdrawer_friends", true, true, this),
                NavMenuItem.create(102, "Bulk Send", "navdrawer_airport", true, true, this),
                NavMenuItem.create(103, "Statistics", "navdrawer_map", true, true, this),
                NavMenuSection.create(200, "General"),
                NavMenuItem.create(201, "Settings", "navdrawer_settings", true, true, this),
                NavMenuItem.create(202, "Rate this app", "navdrawer_rating", false, false, this),
                NavMenuItem.create(203, "Donate", "navdrawer_donations", true, true, this),
                NavMenuItem.create(204, "ChangeLog", "navdrawer_changelog", false, false, this),
                NavMenuItem.create(205, "Eula", "navdrawer_eula", false, false, this) };

        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setMainLayout(R.layout.main);
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
        navDrawerActivityConfiguration.setNavItems(menu);
        navDrawerActivityConfiguration
                .setDrawerShadow(R.drawable.drawer_shadow);
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerActivityConfiguration
                .setDrawerCloseDesc(R.string.drawer_close);
        navDrawerActivityConfiguration.setBaseAdapter(new NavDrawerAdapter(
                this, R.layout.navdrawer_item, menu));
        navDrawerActivityConfiguration.setDrawerIcon(R.drawable.ic_drawer);
        return navDrawerActivityConfiguration;
    }

    @Override
    protected void onNavItemSelected(int id) {
        switch (id) {
            case 101:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new FriendMainFragment())
                        .commit();
                break;
            case 102:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new TutorialListFragment())
                        .commit();
                break;
            case 103:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new SimpleMapFragment())
                        .commit();
                break;
            case 104:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ListsListFragment())
                        .commit();
                break;
            case 201:
                this.navController.showSettings(this);
                break;
            case 202:
                this.navController.startAppRating(this);
                break;
            case 203:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new DonateFragment())
                        .commit();
                break;
            case 204:
                this.navController.showChangeLog(this);
                break;
            case 205:
                this.navController.showEula(this);
                break;
            case 206:
                this.navController.showExitDialog(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {

        // See bug: http://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments/14030872#14030872
        // If the fragment exists and has some back-stack entry
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.content_frame);
        if (currentFragment != null && currentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            // Get the fragment fragment manager - and pop the backstack
            currentFragment.getChildFragmentManager().popBackStack();
        }
        // Else, nothing in the direct fragment back stack
        else {
            if ( !NavigationController.HOME_FRAGMENT_TAG.equals(currentFragment.getTag())) {
                this.navController.goHomeFragment(this);
            }
            else {
                super.onBackPressed();
            }
        }
    }
}
