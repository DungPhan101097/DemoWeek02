package com.dungit.utility.demoweek2.demo_thread_pool_executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class MainThreadExecutor implements Executor {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }
}
