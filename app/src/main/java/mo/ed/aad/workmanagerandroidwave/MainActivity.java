package mo.ed.aad.workmanagerandroidwave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.aad.workmanagerandroidwave.periodic.NotificationWorker;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_STATUS = "message_status";
    private WorkManager mWorkManager;
    private OneTimeWorkRequest mRequest;

    @BindView(R.id.button)
    Button btnLaunch;

    @BindView(R.id.textView)
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mWorkManager= WorkManager.getInstance(getApplicationContext());

        mRequest=new
                OneTimeWorkRequest.Builder(NotificationWorker.class).build();


        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkManager.enqueue(mRequest);
            }
        });

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo!=null){
                    WorkInfo.State state=workInfo.getState();
                    tvStatus.setText(state.toString()+"\n");
                }
            }
        });
    }
}
