package com.zdf.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
/**
* 使用URLConnection下载文件或图片并保存到本地。
*
*/
public class URLConnectionDownloader {
    protected static Logger logger           = Logger.getLogger("img_download");
    private static String downloadPath = "E:/img/";

    public static void main(String[] args) throws Exception {
        String imgUrl = "http://mpic.tiankong.com/ef2/c6e/ef2c6e8ac1a39a6166fb838eaa936751/640.jpg";
        download(imgUrl);
    }

    public static String getSuffix(String url){
        String suffixes="avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.]("+suffixes+")");//正则判断
        Matcher mc=pat.matcher(url);//条件匹配
        String substring = "";
        while(mc.find()){
            substring = mc.group();//截取文件名后缀名
        }
        return substring;
    }
    /**
       * 下载文件到本地
       * @param urlString 被下载的文件地址
       * @throws Exception
       */
    public static void download(String urlString) {
        InputStream is = null;
        OutputStream os = null;
        String originalFilename = getSuffix(urlString);
        try {
            // 构造URL
            URL url = new URL(urlString);

            // 打开连接
            URLConnection con = url.openConnection();
            //con.addRequestProperty("Referer", "http://video.car-me.com");

            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(downloadPath + originalFilename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            logger.error("下载失败，链接地址: " + urlString);
        } finally {
            // 完毕，关闭所有链接
            try {
                if(null != os){
                    os.close();
                }
                if(null != os){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}