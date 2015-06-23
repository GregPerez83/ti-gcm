package com.gperez.ti.gcm;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class GcmBroadcastReceiver extends android.support.v4.content.WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());

        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

}