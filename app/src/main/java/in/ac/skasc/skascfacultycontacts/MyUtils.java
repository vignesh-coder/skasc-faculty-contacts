package in.ac.skasc.skascfacultycontacts;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


class MyUtils {

    static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.d("MyUtils", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d("MyUtils", e.getMessage());
            }
        }
        return sb.toString();
    }
}
