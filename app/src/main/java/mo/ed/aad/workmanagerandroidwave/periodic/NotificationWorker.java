package mo.ed.aad.workmanagerandroidwave.periodic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mo.ed.aad.workmanagerandroidwave.MainActivity;
import mo.ed.aad.workmanagerandroidwave.R;

import static android.content.ContentValues.TAG;

public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Data taskData= getInputData();
            String taskDataString= taskData.getString(MainActivity.MESSAGE_STATUS);

            showNotification("Work Manager",taskDataString!=null?taskDataString:"Message has been sent");

            Data outputData=new Data.Builder().putString(WORK_RESULT,"Job Finished").build();

            return Result.success(outputData);
        }catch (Throwable throwable){
            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }
    }

    private void showNotification(String task, String description) {
        NotificationManager manager=(NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId="task_channel";
        String channelName="task_name";

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder=new
                NotificationCompat.Builder(getApplicationContext(),channelId)
                .setContentTitle(task)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());
    }
}