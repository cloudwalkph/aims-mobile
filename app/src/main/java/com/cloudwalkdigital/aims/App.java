package com.cloudwalkdigital.aims;

import android.app.Application;

import com.cloudwalkdigital.aims.dagger.DaggerNetComponent;
import com.cloudwalkdigital.aims.dagger.NetComponent;
import com.cloudwalkdigital.aims.dagger.modules.AppModule;
import com.cloudwalkdigital.aims.dagger.modules.NetModule;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by alleoindong on 7/4/17.
 */

public class App extends Application {
    private NetComponent mNetComponent;
    private String mAPIUrl = "http://192.168.1.87:8000/api/";

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(mAPIUrl))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
