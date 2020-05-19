package mo.ed.aad.workmanagerandroidwave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.text.format.Time;

import java.util.concurrent.TimeUnit;

import mo.ed.aad.workmanagerandroidwave.periodic.MyPeriodicWork;

public class PeriodicActivityTask extends AppCompatActivity {

    public static final String TAG="PeriodicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_task);

        Constraints constraints=new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(
                MyPeriodicWork.class,
                20,
                TimeUnit.MINUTES
        )
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);

    }
}
