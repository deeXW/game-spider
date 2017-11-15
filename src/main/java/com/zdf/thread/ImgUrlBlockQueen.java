package com.zdf.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhongdifeng on 2017/11/15.
 */
public class ImgUrlBlockQueen {
    private ArrayBlockingQueue<Object> eggs = new ArrayBlockingQueue<Object>(99999);

    /**
     * 获取蛋
     *
     * @return
     */
    public Object getImgUrl() {
        try {
            Object egg = eggs.take();
            System.out.println("消费者取蛋,当前剩余：" + eggs.size());
            return egg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加入队列
     */
    public void addImgUrl(Object egg) {
        try {
            eggs.put(new Object());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
