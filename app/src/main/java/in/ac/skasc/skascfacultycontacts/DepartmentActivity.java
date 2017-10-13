package in.ac.skasc.skascfacultycontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {


    private EditText searchET;
    private RecyclerView departmentsRecycler;
    private List<Department> departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        GoogleAd.loadAd(this);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable e) {

                String s = e.toString().toLowerCase().trim();
                List<Department> tmp = new ArrayList<>();
                for (Department dept : departments) {

                    String deptName = dept.getDeptName().toLowerCase();
                    if (deptName.contains(s))
                        tmp.add(dept);
                }
                departmentsRecycler.setAdapter(new DepartmentRecyclerAdapter(tmp, DepartmentActivity.this));
            }
        });
    }

    private void init() {

        searchET = (EditText) findViewById(R.id.departments_searchET);
        departmentsRecycler = (RecyclerView) findViewById(R.id.departments_list);
        MyJSONParser jsonParser = new MyJSONParser(getApplicationContext());

        String option = getIntent().getStringExtra("option");
        if (option.equals("TS"))
            option = DBConstants.TSDEPTS;
        else if (option.equals("NTS"))
            option = DBConstants.NTSDEPTS;
        departments = jsonParser.getDepartments(option);
        departments.add(0, new Department("0", "ALL"));
        findViewById(R.id.loading).setVisibility(View.GONE);

        departmentsRecycler.setLayoutManager(new LinearLayoutManager(this));
        departmentsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        departmentsRecycler.setItemAnimator(new DefaultItemAnimator());
        departmentsRecycler.setHasFixedSize(true);
        departmentsRecycler.setAdapter(new DepartmentRecyclerAdapter(departments, this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private static class DepartmentRecyclerAdapter extends RecyclerView.Adapter<DepartmentViewHolder> {

        List<Department> departments;
        Activity activity;

        DepartmentRecyclerAdapter(List<Department> departments, Activity activity) {
            this.departments = departments;
            this.activity = activity;
        }

        @Override
        public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_card, parent, false);
            return new DepartmentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DepartmentViewHolder holder, int position) {

            final Department model = departments.get(position);
            holder.setDeptName(model.getDeptName());
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, PeopleActivity.class);
                    intent.putExtra("option", activity.getIntent().getStringExtra("option"));
                    intent.putExtra("deptID", model.getId());
                    activity.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return departments.size();
        }
    }

    static class DepartmentViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView text;

        DepartmentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setDeptName(String deptName) {

            text = (TextView) view.findViewById(R.id.text1);
            text.setText(deptName);
        }
    }
}
