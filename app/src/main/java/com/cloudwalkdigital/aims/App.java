package com.cloudwalkdigital.aims;

import android.app.Application;

import com.cloudwalkdigital.aims.dagger.DaggerNetComponent;
import com.cloudwalkdigital.aims.dagger.NetComponent;
import com.cloudwalkdigital.aims.dagger.modules.AppModule;
import com.cloudwalkdigital.aims.dagger.modules.NetModule;
import com.facebook.stetho.Stetho;
import com.testfairy.TestFairy;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by alleoindong on 7/4/17.
 */

public class App extends Application {
    private NetComponent mNetComponent;
    private String mAPIUrl = "http://project-aims.medix.ph/api/";

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize realm
        Realm.init(this);

        // Test Fairy
        TestFairy.begin(this, "510674f0271ee46c915ca8920a0ef72a0bfc4406");

        // Initialize stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(mAPIUrl))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
