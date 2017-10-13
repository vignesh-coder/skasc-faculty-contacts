package in.ac.skasc.skascfacultycontacts;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class MyJSONParser {

    private JSONObject root;
    private String TAG = getClass().getSimpleName();


    MyJSONParser(final Context context) {


        String JSONStr = "";

        try {
            if (context != null) {
                FileInputStream fis = context.openFileInput(DBConstants.JSON_FILENAME);
                JSONStr = MyUtils.convertStreamToString(fis);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            try {
                InputStream is = context.getAssets().open(DBConstants.JSON_FILENAME);
                JSONStr = MyUtils.convertStreamToString(is);
            } catch (IOException e1) {
                Log.e(TAG, e.getMessage());
            }
        }

        try {
            root = new JSONObject(JSONStr);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    MyJSONParser(String json) {

        try {
            root = new JSONObject(json);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    int getVersionCode() {

        try {
            return root.getInt(DBConstants.DBVERSIONCODE);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    List<Department> getDepartments(String nodeName) {

        List<Department> list = new ArrayList<>();
        try {
            JSONObject deptObj = root.getJSONObject(nodeName);
            Iterator itr = deptObj.keys();
            while (itr.hasNext()) {

                Department dept = new Department();
                String key = (String) itr.next();
                JSONObject currDept = deptObj.getJSONObject(key);
                dept.setDeptName(currDept.getString("deptName"));
                dept.setId(currDept.getString("id"));
                list.add(dept);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return list;
    }

    List<Contact> getContacts(String nodeName, String deptID) {

        List<Contact> list = new ArrayList<>();
        JSONObject contacts;
        try {
            contacts = root.getJSONObject(nodeName);
            Iterator<String> itr = contacts.keys();
            while (itr.hasNext()) {

                Contact con = new Contact();
                String key = itr.next();
                JSONObject curr = contacts.getJSONObject(key);
                con.setId(curr.getString("id"));
                con.setTitle(curr.getString("title"));
                con.setFirstName(curr.getString("firstName"));
                con.setLastName(curr.getString("lastName"));
                con.setRole(getRole(curr.getString("role")));
                con.setMobileNumber_1(curr.getString("mobileNumber_1"));
                con.setMobileNumber_2(curr.getString("mobileNumber_2"));
                con.setCUGNumber(curr.getString("cugnumber"));
                String currDeptID = curr.getString("department").trim();
                String deptNode = "";
                if (nodeName.equals(DBConstants.TSCONTACTS))
                    deptNode = DBConstants.TSDEPTS;
                else if (nodeName.equals(DBConstants.NTSCONTACTS))
                    deptNode = DBConstants.NTSDEPTS;
                con.setDepartment(getDeptName(deptNode, currDeptID));
                if (currDeptID.equals(deptID) || deptID.equals("0"))
                    list.add(con);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        Collections.sort(list);
        return list;
    }

    private String getDeptName(String nodeName, String deptID) {

        String deptName = "";
        try {
            JSONObject deptObj = root.getJSONObject(nodeName);
            Iterator itr = deptObj.keys();
            while (itr.hasNext()) {

                String key = (String) itr.next();
                JSONObject currDept = deptObj.getJSONObject(key);
                if (currDept.getString("id").equals(deptID))
                    return currDept.getString("deptName");
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return deptName;
    }

    private String getRole(String roldID) {

        try {
            JSONObject roles = root.getJSONObject(DBConstants.TSROLES);
            return roles.getString(roldID);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            return "";
        }
    }
}
