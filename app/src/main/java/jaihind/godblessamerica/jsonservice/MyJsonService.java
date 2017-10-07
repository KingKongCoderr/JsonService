package jaihind.godblessamerica.jsonservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.TimeUtils;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import jaihind.godblessamerica.jsonservice.Network.DogApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJsonService extends Service {
    
    private static final java.lang.String JSON_THREAD = "json_thread_for_network_request";
    private Looper jsonLooper;
    private JsonHandler jsonHandler;
    
    public MyJsonService() {
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        HandlerThread handlerThread = new HandlerThread(JSON_THREAD);
        handlerThread.start();
        jsonHandler = new JsonHandler(handlerThread.getLooper());
        long delay = TimeUnit.SECONDS.toMillis(15L);
        jsonHandler.postDelayed(new MyJsonRunnable(intent),delay);
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    private class MyJsonRunnable implements Runnable, Callback<DogImagePojo> {
        private Intent onStartIntent;
        
        public MyJsonRunnable(Intent onStartIntent) {
            this.onStartIntent = onStartIntent;
        }
        
        @Override
        public void run() {
            DogApiManager dogApiManager = new DogApiManager();
            Call<DogImagePojo> dogapi = dogApiManager.getDogApi().getDogPicture();
            dogapi.enqueue(this);
        }
        
        @Override
        public void onResponse(Call<DogImagePojo> call, Response<DogImagePojo> response) {
            if (response.isSuccessful()) {
                DogImagePojo result = response.body();
                String imgUrl = result.getMessage();
                Log.d("image", imgUrl + "");
                Intent innerNotifyIntent = new Intent(MyJsonService.this, MainActivity.class);
                innerNotifyIntent.putExtra("notificationresult", imgUrl);
                PendingIntent notificationIntent = PendingIntent
                        .getActivity(MyJsonService.this, 300, innerNotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyJsonService.this);
                notificationBuilder
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.image_notification_title))
                        .setContentText(getString(R.string.image_notification_text))
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(notificationIntent);
                notificationManager.notify(1,notificationBuilder.build());
                if(onStartIntent!=null) {
                    PendingIntent resultIntent = onStartIntent.getParcelableExtra("pendingintent");
                    Intent innerResultIntent = new Intent();
                    innerResultIntent.putExtra("imageurl", imgUrl);
                    try {
                        resultIntent.send(MyJsonService.this, 200, innerResultIntent);
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.e("response_failure", response.code() + "");
            }
            stopSelf();
        }
        
        @Override
        public void onFailure(Call<DogImagePojo> call, Throwable t) {
            
        }
    }
}
