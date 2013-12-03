package info.mancy.whatsbulkclient;

import info.mancy.android.MCXModule;
import info.mancy.whatsbulkclient.donations.DonateFragment;
import info.mancy.whatsbulkclient.friends.FriendContentProvider;
import info.mancy.whatsbulkclient.home.MainFragment;
import info.mancy.whatsbulkclient.home.YourAppMainActivity;
import info.mancy.whatsbulkclient.settings.SettingsFragment;
import info.mancy.whatsbulkclient.tutorial.TutorialListFragment;
import info.mancy.whatsbulkclient.tutorial.contentprovider.TutorialContentProvider;
import info.mancy.whatsbulkclient.tutorial.sync.TutorialSyncAdapter;

import dagger.Module;

@Module(
	    injects = {
            YourApplication.class,

            YourAppMainActivity.class,

    		FriendContentProvider.class,
            TutorialContentProvider.class,

            MainFragment.class,
            DonateFragment.class,
            SettingsFragment.class,
            TutorialListFragment.class,

            TutorialSyncAdapter.class
	    },
	    includes = {
	    	MCXModule.class
	    },
	    overrides=true
	)
public class YourAppModule {

}
