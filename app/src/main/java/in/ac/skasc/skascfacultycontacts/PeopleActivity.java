package in.ac.skasc.skascfacultycontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeopleActivity extends AppCompatActivity {

    private EditText mSearchET;
    private RecyclerView mRecyclerView;
    private List<Contact> contactList;
    private String deptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        init();
        GoogleAd.loadAd(this);

        mSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {

                String s = e.toString().toLowerCase().trim();
                if (!TextUtils.isEmpty(s)) {

                    List<Contact> tmp = new ArrayList<>();
                    for (Contact con : contactList) {

                        String name = con.getTitle() + " ";
                        if (!TextUtils.isEmpty(con.getLastName()))
                            name += con.getLastName() + " ";
                        name += con.getFirstName();

                        if (name.toLowerCase().contains(s))
                            tmp.add(con);
                        else if (con.getFirstName().toLowerCase().contains(s))
                            tmp.add(con);
                        else if (con.getLastName().toLowerCase().contains(s))
                            tmp.add(con);
                        else if (con.getTitle().toLowerCase().contains(s))
                            tmp.add(con);
                        else if (con.getRole().toLowerCase().contains(s))
                            tmp.add(con);
                        else if (con.getMobileNumber_1().contains(s))
                            tmp.add(con);
                        else if (con.getMobileNumber_2().contains(s))
                            tmp.add(con);
                        else if (con.getCUGNumber().contains(s))
                            tmp.add(con);
                        else if (con.getDepartment().toLowerCase().contains(s))
                            tmp.add(con);
                    }
                    Collections.sort(tmp);
                    mRecyclerView.setAdapter(new RecyclerAdapter(PeopleActivity.this, tmp));
                } else

                    mRecyclerView.setAdapter(new RecyclerAdapter(PeopleActivity.this, contactList));

            }
        });
    }

    private void init() {
        mSearchET = (EditText) findViewById(R.id.people_searchET);
        mRecyclerView = (RecyclerView) findViewById(R.id.people_recycler_view);
        contactList = new ArrayList<>();

        String option = getIntent().getStringExtra("option");

        if (option.equals("TS"))
            option = DBConstants.TSCONTACTS;
        else if (option.equals("NTS"))
            option = DBConstants.NTSCONTACTS;

        deptID = getIntent().getStringExtra("deptID");

        MyJSONParser jsonParser = new MyJSONParser(this);
        contactList = jsonParser.getContacts(option, deptID);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RecyclerAdapter(this, contactList));
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        View view;

        ContactViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setName(String name) {

            TextView nameTv = (TextView) view.findViewById(R.id.contact_name);
            nameTv.setText(name);

        }

        void setRole(String role) {
            final TextView roleTv = (TextView) view.findViewById(R.id.contact_role);
            if (!TextUtils.isEmpty(role))
                roleTv.setText(role);
            else
                roleTv.setVisibility(View.GONE);
        }

        void setInitial(String fname) {

            TextView initialTV = (TextView) view.findViewById(R.id.contact_initial);
            if (!TextUtils.isEmpty(fname))
                initialTV.setText(fname.substring(0, 1));
        }

        void setDepartment(String department) {
            TextView text = (TextView) view.findViewById(R.id.contact_department);
            text.setVisibility(View.VISIBLE);
            text.setText(department);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        Activity activity;
        List<Contact> contactList;

        RecyclerAdapter(Activity activity, List<Contact> contactList) {
            this.activity = activity;
            this.contactList = contactList;

        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
            return new ContactViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {

            final Contact model = contactList.get(position);

            String name = model.getTitle() + " ";
            if (!TextUtils.isEmpty(model.getLastName()))
                name += model.getLastName() + " ";
            name += model.getFirstName();

            holder.setName(name);
            holder.setRole(model.getRole());
            holder.setInitial(model.getFirstName());
            if (deptID.equals("0"))
                holder.setDepartment(model.getDepartment());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, ViewContactActivity.class);
                    intent.putExtra("contact", model);
                    activity.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }
    }
}
