package com.zdf.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhongdifeng on 2017/11/15.
 */
public class ImgUrlBlockQueen {
    private ArrayBlockingQueue<String> imgUrls = new ArrayBlockingQueue<String>(99999);

    /**
     * 获取蛋
     *
     * @return
     */
    public String getImgUrl() {
        try {
            String imgUrl = imgUrls.take();
            System.out.println("当前剩余：" + imgUrls.size());
            return imgUrl;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加入队列
     */
    public void addImgUrl(String imgUrl) {
        try {
            imgUrls.put(imgUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
