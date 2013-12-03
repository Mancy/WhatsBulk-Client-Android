package info.mancy.android;

import info.mancy.android.db.sqlite.SQLiteDatabaseFactory;

import dagger.Module;

@Module(injects = { SQLiteDatabaseFactory.class })
public class MCXModule {

}
