package in.ac.skasc.skascfacultycontacts;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class SkascFacultyContacts extends MultiDexApplication {

    @Override
    public void onCreate() {

        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (FirebaseApp.getApps(this).isEmpty()) {

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}