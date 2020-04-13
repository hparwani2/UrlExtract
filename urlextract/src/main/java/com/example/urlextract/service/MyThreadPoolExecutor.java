package com.example.urlextract.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {
    private int counter = 0;

    public MyThreadPoolExecutor(int var0) {
        super(var0, var0, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public synchronized void execute(Runnable command) {
        counter++;
        System.out.println("Counter in execute: " + counter);
        super.execute(command);
    }

    @Override
    protected synchronized void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        counter--;
        System.out.println("Counter after execute: " + counter);
        notifyAll();
    }

    public synchronized void waitForExecuted() throws InterruptedException {
        while (counter > 0)
            wait();
    }
}
