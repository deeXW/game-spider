package com.zdf.util;
/**
 * 实现js中的escap和unescape
 * @author dee
 *
 */
public class EscapeUnescape {  
    public static String escape(String src) {  
        int i;  
        char j;  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length() * 6);  
        for (i = 0; i < src.length(); i++) {  
            j = src.charAt(i);  
            if (Character.isDigit(j) || Character.isLowerCase(j)  
                    || Character.isUpperCase(j))  
                tmp.append(j);  
            else if (j < 256) {  
                tmp.append("%");  
                if (j < 16)  
                    tmp.append("0");  
                tmp.append(Integer.toString(j, 16));  
            } else {  
                tmp.append("%u");  
                tmp.append(Integer.toString(j, 16));  
            }  
        }  
        return tmp.toString();  
    }  
  
    public static String unescape(String src) {  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length());  
        int lastPos = 0, pos = 0;  
        char ch;  
        while (lastPos < src.length()) {  
            pos = src.indexOf("%", lastPos);  
            if (pos == lastPos) {  
                if (src.charAt(pos + 1) == 'u') {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }  
            } else {  
                if (pos == -1) {  
                    tmp.append(src.substring(lastPos));  
                    lastPos = src.length();  
                } else {  
                    tmp.append(src.substring(lastPos, pos));  
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }  
  
    /**   
     * @disc 对字符串重新编码   
     * @param src   
     * @return    
     */  
    public static String isoToGB(String src) {  
        String strRet = null;  
        try {  
            strRet = new String(src.getBytes("ISO_8859_1"), "GB2312");  
        } catch (Exception e) {  
  
        }  
        return strRet;  
    }  
  
    /**   
     * @disc 对字符串重新编码   
     * @param src   
     * @return    
     */  
    public static String isoToUTF(String src) {  
        String strRet = null;  
        try {  
            strRet = new String(src.getBytes("ISO_8859_1"), "UTF-8");  
        } catch (Exception e) {  
  
        }  
        return strRet;  
    }  
  
    public static void main(String[] args) {  
        System.out.println(unescape("%u7b2c1%u96c6%24CNjY5OTAzNg%3D%3D%23%u7b2c2%u96c6%24CNjY5OTA0MA%3D%3D%23%u7b2c3%u96c6%24CNjY5OTA0OA%3D%3D%23%u7b2c4%u96c6%24CNjg2NjAxMg%3D%3D%23%u7b2c5%u96c6%24CNzA1NDg1Mg%3D%3D%23%u7b2c6%u96c6%24CNzI2NjEyMA%3D%3D%23%u7b2c7%u96c6%24CNzQ3Mjg5Mg%3D%3D"));  
    }  
}  