package com.zzkong.example.utils;

import android.os.Looper;
import android.support.annotation.VisibleForTesting;

/**
 * Created by Kilnn on 2018/1/6.
 */

public class MainThreadPlugin {

    private volatile Thread mMainThread;

    MainThreadPlugin() {
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public MainThreadPlugin(Thread mainThread) {
        mMainThread = mainThread;
    }

    public void assertMainThread() {
        if (!isMainThread()) {
            throw new IllegalStateException("不允许在非主线程访问");
        }
    }

    public void assertNotMainThread() {
        if (isMainThread()) {
            throw new IllegalStateException("不允许在主线程访问");
        }
    }

    public boolean isMainThread() {
        if (mMainThread == null) {
            return Looper.getMainLooper().getThread() == Thread.currentThread();
        } else {
            return mMainThread == Thread.currentThread();
        }
    }

}
