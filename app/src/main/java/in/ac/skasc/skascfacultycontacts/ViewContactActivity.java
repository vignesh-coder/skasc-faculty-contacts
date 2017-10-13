package in.ac.skasc.skascfacultycontacts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewContactActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private TextView nameTV;
    private TextView roleTV;
    private TextView deptTV;
    private CardView mobileNumber1CV;
    private CardView mobileNumber2CV;
    private CardView mobileNumber3CV;
    private TextView mobileNumber1TV;
    private TextView mobileNumber2TV;
    private TextView mobileNumber3TV;
    private ImageView save1;
    private ImageView save2;
    private ImageView save3;
    private ImageView call1;
    private ImageView call2;
    private ImageView call3;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        ActionBar actionBar = getSupportActionBar();
        final Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();
        GoogleAd.loadAd(this);
        if (contact != null) {

            name = contact.getTitle() + " ";
            if (!TextUtils.isEmpty(contact.getLastName()))
                name += contact.getLastName() + " ";
            name += contact.getFirstName();

            nameTV.setText(name);

            if (!TextUtils.isEmpty(contact.getRole()))
                roleTV.setText(contact.getRole());
            else
                roleTV.setVisibility(View.GONE);

            deptTV.setText(contact.getDepartment());
            if (!TextUtils.isEmpty(contact.getMobileNumber_1()))
                mobileNumber1TV.setText(contact.getMobileNumber_1());
            else
                mobileNumber1CV.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(contact.getMobileNumber_2()))
                mobileNumber2TV.setText(contact.getMobileNumber_2());
            else
                mobileNumber2CV.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(contact.getCUGNumber()))
                mobileNumber3TV.setText(contact.getCUGNumber());
            else
                mobileNumber3CV.setVisibility(View.GONE);

            mobileNumber1CV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getMobileNumber_1());
                }
            });

            mobileNumber2CV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getMobileNumber_2());
                }
            });

            mobileNumber3CV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getCUGNumber());
                }
            });

            call1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getMobileNumber_1());
                }
            });

            call2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getMobileNumber_2());
                }
            });

            call3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCall(contact.getCUGNumber());
                }
            });

            save1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveContact(name, contact.getMobileNumber_1());
                }
            });

            save2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveContact(name, contact.getMobileNumber_2());
                }
            });

            save3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveContact(name, contact.getCUGNumber());
                }
            });
        }
    }

    private void saveContact(String name, String number) {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);

        startActivityForResult(intent, 2);

    }

    private void makeCall(String number) {

        try {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void init() {

        nameTV = (TextView) findViewById(R.id.view_contact_name);
        roleTV = (TextView) findViewById(R.id.view_contact_role);
        deptTV = (TextView) findViewById(R.id.view_contact_department);
        mobileNumber1CV = (CardView) findViewById(R.id.mobile_number_1);
        mobileNumber2CV = (CardView) findViewById(R.id.mobile_number_2);
        mobileNumber3CV = (CardView) findViewById(R.id.mobile_number_3);
        mobileNumber1TV = (TextView) findViewById(R.id.mobile_number_1_text);
        mobileNumber2TV = (TextView) findViewById(R.id.mobile_number_2_text);
        mobileNumber3TV = (TextView) findViewById(R.id.mobile_number_3_text);
        save1 = (ImageView) findViewById(R.id.save1);
        save2 = (ImageView) findViewById(R.id.save2);
        save3 = (ImageView) findViewById(R.id.save3);
        call1 = (ImageView) findViewById(R.id.call1);
        call2 = (ImageView) findViewById(R.id.call2);
        call3 = (ImageView) findViewById(R.id.call3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
