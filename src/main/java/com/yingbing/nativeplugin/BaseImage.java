package com.yingbing.nativeplugin;

import com.taobao.weex.annotation.JSMethod;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.dcloud.feature.uniapp.common.UniModule;

/**
 * @Title: TestTranslate
 * @author: 康雷  e-mail: 1014295211@qq.com
 * @date: 2020/8/26 16:45
 * @ClassName: BaseImage
 * @Description: 处理防盗链图片并返回base64
 */

public class BaseImage extends UniModule {
    //判断编码格式方法
    @JSMethod(uiThread = false)
    public static String getImgStr(String imgFile){
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        String baseStr = null;
        try {
            // 创建URL
            URL url = new URL(imgFile);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("referer", url.getHost());
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容放到内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            baseStr = Base64.encodeToString(data.toByteArray(), Base64.DEFAULT);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String suffix = getSuffix(imgFile);
        // 对字节数组Base64编码
        // 数据为空
        return baseStr != null && baseStr != "" ? "data:image/" + suffix +";base64," + baseStr : baseStr;
    }

    @JSMethod(uiThread = false)
    private static String getSuffix (String url) {
        String str = "jpg";
        if ( url.indexOf(".") > -1 && url != null ) {
            int lastIndex = url.lastIndexOf(".");
            str = url.substring(lastIndex + 1);
        }
        return str;
    }
}
