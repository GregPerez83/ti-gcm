package com.gperez.ti.gcm;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollNativeConverter;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.json.JSONObject;

import java.util.HashMap;

public class GcmIntentService extends IntentService
{
    private static final String TAG = "GcmIntentService";

    public GcmIntentService() {
        super(GcmIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            TiGcmModule.logd(TAG+": messageType is " + messageType);
            TiGcmModule.logd(TAG+": ----------------:"+ extras.toString());

            if (messageType != null && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //
                // Push Notification Received
                //

                // Generate Data
                HashMap<String, Object> jsonData = new HashMap<String, Object>();
                for (String key : extras.keySet()) {
                    if (extras.get(key) != null && !"".equals(extras.get(key))) {
                        jsonData.put(key, extras.get(key));
                    }
                }

                // Convert JSON format
                JSONObject json = new JSONObject(jsonData);

                if (!isInForeground()) {
                    // When in the background, launch developer's service implementation to handle the push notification
                    TiApplication tiApp = TiApplication.getInstance();
                    Intent launcherIntent = new Intent(tiApp, GcmScriptService.class);
                    launcherIntent.putExtra(TiC.PROPERTY_DATA, json.toString());

                    // set Service Mode "START_NOT_STICKY".
                    // @see http://developer.android.com/reference/android/app/Service.html#START_NOT_STICKY
                    // default is "START_REDELIVER_INTENT" defined in TiJSService
                    launcherIntent.putExtra(TiC.INTENT_PROPERTY_START_MODE, Service.START_NOT_STICKY);

                    // Start service
                    tiApp.startService(launcherIntent);
                }
                else {
                    // Foreground. Send message directly to app.
                    TiGcmModule module = TiGcmModule.getInstance();
                    if (module != null) {
                        module.fireData(new KrollDict(jsonData), false);
                    }
                    else {
                        TiGcmModule.logd(TAG+": fireMessage module instance not found.");
                    }
                }
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public static boolean isInForeground() {
        Context context = TiApplication.getInstance().getApplicationContext();
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        if (am.getRunningTasks(1).get(0).topActivity.getPackageName().equals(packageName)) {
            return true;
        }
        return false;
    }
}