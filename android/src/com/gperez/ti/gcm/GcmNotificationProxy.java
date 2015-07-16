package com.gperez.ti.gcm;

import java.util.Date;

import android.app.PendingIntent;
import android.content.Intent;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiUIHelper;

import ti.modules.titanium.android.AndroidModule;
import android.app.Notification;
import android.content.Context;
import android.net.Uri;

@Kroll.proxy(creatableInModule=TiGcmModule.class, propertyAccessors = {
        TiC.PROPERTY_CONTENT_TEXT,
        TiC.PROPERTY_CONTENT_TITLE
})
public class GcmNotificationProxy extends KrollProxy
{
    private static final String TAG = "TiGcmNotification";

    protected Notification notification;

    public GcmNotificationProxy()
    {
        super();
        notification = new Notification(
                android.R.drawable.stat_sys_warning, null, System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    public GcmNotificationProxy(TiContext tiContext)
    {
        this();
    }

    @Override
    public void handleCreationDict(KrollDict d)
    {
        super.handleCreationDict(d);
        if (d == null) {
            return;
        }

        if (d.containsKey(TiC.PROPERTY_ICON)) {
            setIcon(d.get(TiC.PROPERTY_ICON));
        }
        if (d.containsKey(TiC.PROPERTY_TICKER_TEXT)) {
            setTickerText(TiConvert.toString(d, TiC.PROPERTY_TICKER_TEXT));
        }
        if (d.containsKey(TiC.PROPERTY_WHEN)) {
            setWhen(d.get(TiC.PROPERTY_WHEN));
        }
        if (d.containsKey(TiC.PROPERTY_AUDIO_STREAM_TYPE)) {
            setAudioStreamType(TiConvert.toInt(d, TiC.PROPERTY_AUDIO_STREAM_TYPE));
        }
        /*
        if (d.containsKey(TiC.PROPERTY_CONTENT_VIEW)) {
            setContentView((RemoteViewsProxy) d.get(TiC.PROPERTY_CONTENT_VIEW));
        }
        if (d.containsKey(TiC.PROPERTY_CONTENT_INTENT)) {
            setContentIntent((PendingIntentProxy) d.get(TiC.PROPERTY_CONTENT_INTENT));
        }*/
        if (d.containsKey(TiC.PROPERTY_DEFAULTS)) {
            setDefaults(TiConvert.toInt(d, TiC.PROPERTY_DEFAULTS));
        }
        /*
        if (d.containsKey(TiC.PROPERTY_DELETE_INTENT)) {
            setDeleteIntent((PendingIntentProxy) d.get(TiC.PROPERTY_DELETE_INTENT));
        }*/
        if (d.containsKey(TiC.PROPERTY_FLAGS)) {
            setFlags(TiConvert.toInt(d, TiC.PROPERTY_FLAGS));
        }
        if (d.containsKey(TiC.PROPERTY_ICON_LEVEL)) {
            setIconLevel(TiConvert.toInt(d, TiC.PROPERTY_ICON_LEVEL));
        }
        if (d.containsKey(TiC.PROPERTY_LED_ARGB)) {
            setLedARGB(TiConvert.toInt(d, TiC.PROPERTY_LED_ARGB));
        }
        if (d.containsKey(TiC.PROPERTY_LED_OFF_MS)) {
            setLedOffMS(TiConvert.toInt(d, TiC.PROPERTY_LED_OFF_MS));
        }
        if (d.containsKey(TiC.PROPERTY_LED_ON_MS)) {
            setLedOnMS(TiConvert.toInt(d, TiC.PROPERTY_LED_ON_MS));
        }
        if (d.containsKey(TiC.PROPERTY_NUMBER)) {
            setNumber(TiConvert.toInt(d, TiC.PROPERTY_NUMBER));
        }
        if (d.containsKey(TiC.PROPERTY_SOUND)) {
            setSound(TiConvert.toString(d, TiC.PROPERTY_SOUND));
        }
        if (d.containsKey(TiC.PROPERTY_VIBRATE_PATTERN)) {
            setVibratePattern((Object[]) d.get(TiC.PROPERTY_VIBRATE_PATTERN));
        }
        checkLatestEventInfoProperties(d);
    }

    @Kroll.method @Kroll.setProperty
    public void setIcon(Object icon)
    {
        if (icon instanceof Number) {
            notification.icon = ((Number)icon).intValue();
        } else {
            String iconUrl = TiConvert.toString(icon);
            //TiContext context = invocation == null ? getTiContext() : invocation.getTiContext();
            String iconFullUrl = resolveUrl(null, iconUrl);
            notification.icon = TiUIHelper.getResourceId(iconFullUrl);
            if (notification.icon == 0) {
                Log.w(TAG, "No image found for " + iconUrl);
            }
        }
    }

    @Kroll.method @Kroll.setProperty
    public void setTickerText(String tickerText)
    {
        notification.tickerText = tickerText;
    }

    @Kroll.method @Kroll.setProperty
    public void setWhen(Object when)
    {
        if (when instanceof Date) {
            notification.when = ((Date)when).getTime();
        } else {
            notification.when = ((Double) TiConvert.toDouble(when)).longValue();
        }
    }

    @Kroll.method @Kroll.setProperty
    public void setAudioStreamType(int type)
    {
        notification.audioStreamType = type;
    }

    /*
    @Kroll.method @Kroll.setProperty
    public void setContentView(RemoteViewsProxy contentView)
    {
        notification.contentView = contentView.getRemoteViews();
    }

    @Kroll.method @Kroll.setProperty
    public void setContentIntent(PendingIntentProxy contentIntent)
    {
        notification.contentIntent = contentIntent.getPendingIntent();
    }
    */

    @Kroll.method @Kroll.setProperty
    public void setDefaults(int defaults)
    {
        notification.defaults = defaults;
    }

    /*
    @Kroll.method @Kroll.setProperty
    public void setDeleteIntent(PendingIntentProxy deleteIntent)
    {
        notification.deleteIntent = deleteIntent.getPendingIntent();
    }*/

    @Kroll.method @Kroll.setProperty
    public void setFlags(int flags)
    {
        notification.flags = flags;
    }

    @Kroll.method @Kroll.setProperty
    public void setIconLevel(int iconLevel)
    {
        notification.iconLevel = iconLevel;
    }

    @Kroll.method @Kroll.setProperty
    public void setLedARGB(int ledARGB)
    {
        notification.ledARGB = ledARGB;
    }

    @Kroll.method @Kroll.setProperty
    public void setLedOffMS(int ledOffMS)
    {
        notification.ledOffMS = ledOffMS;
    }

    @Kroll.method @Kroll.setProperty
    public void setLedOnMS(int ledOnMS)
    {
        notification.ledOnMS = ledOnMS;
    }

    @Kroll.method @Kroll.setProperty
    public void setNumber(int number)
    {
        notification.number = number;
    }

    @Kroll.method @Kroll.setProperty
    public void setSound(String url)
    {
        notification.sound = Uri.parse(resolveUrl(null, url));

    }

    @Kroll.method @Kroll.setProperty
    public void setVibratePattern(Object[] pattern)
    {
        if (pattern != null) {
            notification.vibrate = new long[pattern.length];
            for (int i = 0; i < pattern.length; i++) {
                notification.vibrate[i] = ((Double)TiConvert.toDouble(pattern[i])).longValue();
            }
        }
    }

    protected void checkLatestEventInfoProperties(KrollDict d)
    {
        if (d.containsKeyAndNotNull(TiC.PROPERTY_CONTENT_TITLE)
                || d.containsKeyAndNotNull(TiC.PROPERTY_CONTENT_TEXT))
        {
            String contentTitle = "";
            String contentText = "";
            PendingIntent contentIntent = null;
            if (d.containsKeyAndNotNull(TiC.PROPERTY_CONTENT_TITLE)) {
                contentTitle = TiConvert.toString(d, TiC.PROPERTY_CONTENT_TITLE);
            }
            if (d.containsKeyAndNotNull(TiC.PROPERTY_CONTENT_TEXT)) {
                contentText = TiConvert.toString(d, TiC.PROPERTY_CONTENT_TEXT);
            }

            Context c = getActivity();
            if (c == null) {
                c = TiApplication.getInstance().getApplicationContext();
            }
            contentIntent = createDefaultIntent(c, d.getKrollDict(TiC.PROPERTY_DATA));

            notification.setLatestEventInfo(c, contentTitle, contentText, contentIntent);
        }
    }

    private PendingIntent createDefaultIntent(Context context, Object data) {
        Intent intent = new Intent(context, GcmLauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        long actionId = (new Date().getTime());
        intent.setAction("action" + actionId);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        if (data != null) {
            intent.putExtra(TiC.PROPERTY_DATA, data.toString());
        }

        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    /*
    @Kroll.method
    public void setLatestEventInfo(String contentTitle, String contentText, PendingIntentProxy contentIntent)
    {
        Context c = getActivity();
        if (c == null) {
            c = TiApplication.getInstance().getApplicationContext();
        }
        notification.setLatestEventInfo(c, contentTitle, contentText, contentIntent.getPendingIntent());
    }*/

    public Notification getNotification()
    {
        return notification;
    }

    @Override
    public String getApiName()
    {
        return "Ti.Android.Notification";
    }
}