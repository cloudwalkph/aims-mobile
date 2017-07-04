package com.cloudwalkdigital.aims;

import com.facebook.stetho.Stetho;
import com.testfairy.TestFairy;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by alleoindong on 7/4/17.
 */

public class DebugApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();

        // Test Fairy
        TestFairy.begin(this, "510674f0271ee46c915ca8920a0ef72a0bfc4406");

        // Initialize realm
        Realm.init(this);
        // Initialize stetho
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());


    }
}
