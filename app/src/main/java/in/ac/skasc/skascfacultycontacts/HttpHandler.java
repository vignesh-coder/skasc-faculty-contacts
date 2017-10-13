package in.ac.skasc.skascfacultycontacts;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


class HttpHandler {

    HttpHandler() {
    }

    String makeServiceCall(String reqUrl) throws IOException {
        String response;
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        response = MyUtils.convertStreamToString(in);
        return response;
    }


}