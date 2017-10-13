package in.ac.skasc.skascfacultycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mTeachingStaffsCV;
    private RelativeLayout mNonTeachingStaffsCV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        init();
        GoogleAd.loadAd(this);
        startService(new Intent(this, VersionCheckService.class));
    }

    private void init() {
        mTeachingStaffsCV = (RelativeLayout) findViewById(R.id.main_teach_card_view);
        mNonTeachingStaffsCV = (RelativeLayout) findViewById(R.id.main_non_teach_card_view);

        mTeachingStaffsCV.setOnClickListener(this);
        mNonTeachingStaffsCV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, DepartmentActivity.class);
        if (v.getId() == mTeachingStaffsCV.getId())
            intent.putExtra("option", "TS");
        else if (v.getId() == mNonTeachingStaffsCV.getId())
            intent.putExtra("option", "NTS");
        startActivity(intent);
    }

}
