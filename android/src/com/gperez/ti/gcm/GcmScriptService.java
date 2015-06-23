package com.gperez.ti.gcm;

import ti.modules.titanium.android.TiJSService;

// Application-defined service that is invoked when a message is received while the app is not in the foreground
public final class GcmScriptService extends TiJSService {
    public GcmScriptService() {
        super("gcm.js");
    }
}

