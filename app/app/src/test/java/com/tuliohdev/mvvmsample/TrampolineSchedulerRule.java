package com.tuliohdev.mvvmsample;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by tulio on 25/01/18.
 */

public class TrampolineSchedulerRule implements TestRule {

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(
                    scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(
                    scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(
                    scheduler -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                    scheduler -> Schedulers.trampoline());

                try {
                    statement.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

}
