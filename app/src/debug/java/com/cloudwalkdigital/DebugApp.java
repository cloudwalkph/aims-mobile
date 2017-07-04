

import com.cloudwalkdigital.aims.App;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by alleoindong on 7/4/17.
 */

public class DebugApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();

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
