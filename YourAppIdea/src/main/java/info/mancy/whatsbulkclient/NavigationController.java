package info.mancy.whatsbulkclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import info.mancy.android.ui.changelog.ChangeLogHelper;
import info.mancy.android.ui.changelog.EulaChangeLogChainHelper;
import info.mancy.android.ui.eula.EulaHelper;
import info.mancy.whatsbulkclient.R;
import info.mancy.android.ui.fragment.dialog.ConfirmDialog;
import info.mancy.android.ui.navdrawer.AbstractNavDrawerActivity;
import info.mancy.whatsbulkclient.home.MainFragment;
import info.mancy.whatsbulkclient.settings.SettingsFragment;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NavigationController {

    public static final String HOME_FRAGMENT_TAG = "home";

	@Inject public NavigationController() {
		
	}
	
	public void startAppRating(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse("market://details?id=" + context.getPackageName())));
	}

    public void goHomeFragment( AbstractNavDrawerActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), HOME_FRAGMENT_TAG).commit();
        activity.setTitleWithDrawerTitle();
    }

    public void confirmEulaAndShowChangeLog(FragmentActivity activity) {
        EulaChangeLogChainHelper chain = new EulaChangeLogChainHelper(R.string.eula_title, R.string.eula_accept,
                R.string.eula_refuse, R.string.changelog_whatsnew_title, R.string.changelog_close, R.xml.changelog, activity);
        chain.show();
    }

	public void confirmEula(FragmentActivity activity) {
        EulaHelper eulaHelper = new EulaHelper(activity);
        eulaHelper.showAcceptRefuse(R.string.eula_title, R.string.eula_accept,
                R.string.eula_refuse);
	}

	public void showEula(FragmentActivity activity) {
        EulaHelper eulaHelper = new EulaHelper(activity);
        eulaHelper.show(R.string.eula_title, R.string.eula_close);
	}

    public void showWhatsNew( FragmentActivity activity ) {
        ChangeLogHelper changeLogHelper = new ChangeLogHelper();
        changeLogHelper.showWhatsNew(R.string.changelog_title, R.string.changelog_close, R.xml.changelog, activity);
    }

    public void showChangeLog( FragmentActivity activity ) {
        ChangeLogHelper changeLogHelper = new ChangeLogHelper();
        changeLogHelper.showFullChangeLog(R.string.changelog_title, R.string.changelog_close, R.xml.changelog, activity);
    }

	public void showExitDialog(final FragmentActivity activity) {
		ConfirmDialog newFragment = ConfirmDialog.newInstance(
				R.string.confirm_quit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						activity.finish();
					}
				}, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		newFragment.show(activity.getSupportFragmentManager(), "dialog");
	}

    public void showSettings(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment(), null).commit();
    }
}
