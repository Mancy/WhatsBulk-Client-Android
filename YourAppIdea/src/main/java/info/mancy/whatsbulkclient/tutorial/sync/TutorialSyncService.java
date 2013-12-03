package info.mancy.whatsbulkclient.tutorial.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import info.mancy.whatsbulkclient.YourApplication;

public class TutorialSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();

    private static TutorialSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new TutorialSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
