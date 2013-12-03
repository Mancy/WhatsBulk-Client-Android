package info.mancy.whatsbulkclient;

import android.content.pm.PackageManager.NameNotFoundException;

import info.mancy.android.MCXApplication;
import info.mancy.android.db.sqlite.SQLiteDatabaseFactory;
import info.mancy.whatsbulkclient.tutorial.sync.TutorialSyncHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class YourApplication extends MCXApplication {

    public static final String LOG_TAG = "YAI";

    @Inject
    TutorialSyncHelper tutorialSyncHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        this.injectSelf();

        // Enable tutorial sync
        this.tutorialSyncHelper.createTutorialAccount(this);
    }

    @Override
    public void buildDaggerModules(List<Object> modules) {
        modules.add(new YourAppModule());
    }

    @Override
    public void onObjectGraphCreated(ObjectGraph objectGraph) {
        super.onObjectGraphCreated(objectGraph);
        SQLiteDatabaseFactory sqliteDbFactory = objectGraph.get(SQLiteDatabaseFactory.class);
        try {
            sqliteDbFactory.init(this, true, true);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
