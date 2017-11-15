package com.zdf.thread;

import com.zdf.util.URLConnectionDownloader;

/**
 * Created by zhongdifeng on 2017/11/15.
 */
public class DownloadImgConsumer implements Runnable{

    private ImgUrlBlockQueen imgUrlBlockQueen;

    public DownloadImgConsumer(ImgUrlBlockQueen imgUrlBlockQueen) {
        this.imgUrlBlockQueen = imgUrlBlockQueen;
    }

    @Override
    public void run() {
        String imgUrl = (String)imgUrlBlockQueen.getImgUrl();
        URLConnectionDownloader.download(imgUrl);
    }
}
