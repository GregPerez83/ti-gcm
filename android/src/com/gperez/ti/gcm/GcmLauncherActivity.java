package com.gperez.ti.gcm;

import android.app.Activity;
import android.content.Intent;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.json.JSONObject;

public class GcmLauncherActivity extends Activity {

    Boolean appWasRunning = false;

    public GcmLauncherActivity() {
        super();
        appWasRunning = TiApplication.getAppCurrentActivity() != null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TiGcmModule.logd("GcmLauncherActivity: Starting activity");

        KrollDict data = extractIntentData();
        if (!appWasRunning) {
            TiApplication tiApp = TiApplication.getInstance();
            String tiPackageName = tiApp.getPackageName();
            String mainClassName = tiApp.getPackageManager().getLaunchIntentForPackage(tiPackageName).getComponent().getClassName();

            Intent mainActivityIntent = new Intent();
            mainActivityIntent.setClassName(tiPackageName, mainClassName);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mainActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(mainActivityIntent);

            TiGcmModule.getInstance().fireData(data, true);

        } else {
            TiGcmModule.getInstance().fireData(data, true);
            this.finish();
        }
    }

    private KrollDict extractIntentData() {
        KrollDict data;
        try {
            String dataStr = this.getIntent().getStringExtra(TiC.PROPERTY_DATA);
            JSONObject dataJson = new JSONObject(dataStr);
            data = new KrollDict(dataJson);
        }
        catch (Exception ex){
            TiGcmModule.logd("GcmLauncherActivity - Error parsing data " + ex.getMessage());
            data = new KrollDict();
        }
        return data;
    }
}
