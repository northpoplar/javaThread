package com.java;

/**
 * Created by zhoucl on 2018/2/5.
 */
public class BlockedOrWaiting {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread tDecrease = new Thread(() -> counter.decrease(20));
        tDecrease.setName("decrease thread");
        tDecrease.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主要线程显示--"+tDecrease.getName()+"的状态"+tDecrease.getState());

        Thread tIncrease = new Thread(() -> counter.increase(10));
        tIncrease.setName("increase thread");
        tIncrease.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主要线程显示--"+tDecrease.getName()+"的状态"+tDecrease.getState());

    }
}

class Counter {

    private int count = 10;

    synchronized void increase(int num) {
        count += num;
        this.notifyAll();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void decrease(int num) {
        if (num > count) {
            try {
                System.out.println("等候开始"+Thread.currentThread().getName()+"的状态"+Thread.currentThread().getState());
                this.wait();
                System.out.println("等候结束---"+Thread.currentThread().getName()+"的状态"+Thread.currentThread().getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count -= num;
    }
}
