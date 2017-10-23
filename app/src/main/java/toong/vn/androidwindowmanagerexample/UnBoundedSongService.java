package toong.vn.androidwindowmanagerexample;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class UnBoundedSongService extends Service {
    private String TAG = getClass().getSimpleName();

    public UnBoundedSongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This is unbounded service => this method will never called
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WindowManager.LayoutParams p =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        // Display it on top of other application windows, but only for the current user
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                                ? WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
                                : WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        // Make the underlying application window visible through any transparent parts
                        PixelFormat.TRANSLUCENT);

        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.window_manager_layout, null);

        p.gravity = Gravity.TOP;
        p.y = 10;

        windowManager.addView(popupView, p);
        // dismiss windowManager after 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                windowManager.removeView(popupView);
            }
        }, 4000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}