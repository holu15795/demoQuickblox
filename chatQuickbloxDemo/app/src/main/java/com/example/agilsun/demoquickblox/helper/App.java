package com.example.agilsun.demoquickblox.helper;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

/**
 * *******************************************
 * * Created by HoLu on 28/02/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class App extends Application{

    static final String APP_ID = "68986";
    static final String AUTH_KEY = "JS-YJ2MhGy6J5Mq";
    static final String AUTH_SECRET = "MrLuQEPXaLVWjRV";
    static final String ACCOUNT_KEY = "1v9oPHt9R2jc5eqMu-jz";

    @Override
    public void onCreate() {
        super.onCreate();
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.d("session", "onSuccess: ");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("session", "onError: ");
            }
        });
    }

}
