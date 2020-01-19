package com.tw.dubbo.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author clay
 * @date 2018/12/3 20:24
 */
public class NamedThreadFactory implements ThreadFactory {


    protected final String mPrefix;

    protected final boolean mDaemon;

    protected final ThreadGroup mGroup;
    protected final AtomicInteger mThreadNum = new AtomicInteger(1);


    public NamedThreadFactory(String prefix, boolean daemon) {
        mPrefix = prefix + "-thread-";
        mDaemon = daemon;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }


    @Override
    public Thread newThread(Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemon);
        return ret;
    }
}
