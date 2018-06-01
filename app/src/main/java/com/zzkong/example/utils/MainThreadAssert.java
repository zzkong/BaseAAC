package com.zzkong.example.utils;

import android.support.annotation.VisibleForTesting;

/**
 * Created by Kilnn on 2018/1/6.
 */

public class MainThreadAssert {

    @VisibleForTesting
    public static MainThreadPlugin plugin = new MainThreadPlugin();

    public static void assertMainThread() {
        plugin.assertMainThread();
    }

    public static void assertNotMainThread() {
        plugin.assertNotMainThread();
    }

    public static boolean isMainThread() {
        return plugin.isMainThread();
    }

}
