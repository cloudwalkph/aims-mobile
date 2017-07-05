package com.cloudwalkdigital.aims.dagger;

import com.cloudwalkdigital.aims.LoginActivity;
import com.cloudwalkdigital.aims.dagger.modules.AppModule;
import com.cloudwalkdigital.aims.dagger.modules.NetModule;
import com.cloudwalkdigital.aims.joborder.JobOrderActivity;
import com.cloudwalkdigital.aims.joborder.JobOrderDiscussionsFragment;
import com.cloudwalkdigital.aims.joborder.JobOrderValidateFragment;
import com.cloudwalkdigital.aims.projectselection.ProjectSelectionActivity;
import com.cloudwalkdigital.aims.questions.QuestionActivity;
import com.cloudwalkdigital.aims.userselection.UserSelectionActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alleoindong on 7/4/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(LoginActivity activity);
    void inject(ProjectSelectionActivity activity);
    void inject(JobOrderActivity activity);
    void inject(UserSelectionActivity activity);
    void inject(QuestionActivity activity);
    void inject(JobOrderDiscussionsFragment fragment);
    void inject(JobOrderValidateFragment fragment);
}
