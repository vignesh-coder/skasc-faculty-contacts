package in.ac.skasc.skascfacultycontacts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;

public class VersionCheckService extends Service {

    private static final String URL = "https://skascfacultycontacts.firebaseio.com/.json";
    private final String TAG = getClass().getSimpleName();
    private DatabaseReference versionDB;
    private ValueEventListener listener;
    private MyJSONParser jsonParser;

    public VersionCheckService() {
        versionDB = FirebaseDatabase.getInstance().getReference().child(DBConstants.DBVERSIONCODE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                jsonParser = new MyJSONParser(VersionCheckService.this);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int version = dataSnapshot.getValue(Integer.class);
                if (version != jsonParser.getVersionCode()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            downloadJSON();
                        }
                    }).start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        versionDB.addValueEventListener(listener);
        return START_STICKY;
    }

    private void downloadJSON() {

        HttpHandler hh = new HttpHandler();
        String JSONStr = "";
        try {
            JSONStr = hh.makeServiceCall(URL);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(DBConstants.JSON_FILENAME, MODE_PRIVATE);
            fos.write(JSONStr.getBytes());
            Log.d(TAG, "JSON Updated.");
            final String finalJSONStr = JSONStr;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jsonParser = new MyJSONParser(finalJSONStr);
                }
            }).start();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service Stopped.");
        versionDB.removeEventListener(listener);
    }
}
