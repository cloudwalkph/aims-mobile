package com.cloudwalkdigital.aims.dagger;

import com.cloudwalkdigital.aims.LoginActivity;
import com.cloudwalkdigital.aims.dagger.modules.AppModule;
import com.cloudwalkdigital.aims.dagger.modules.NetModule;
import com.cloudwalkdigital.aims.joborder.JobOrderDiscussionsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alleoindong on 7/4/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(LoginActivity activity);
    void inject(JobOrderDiscussionsFragment fragment);
}
