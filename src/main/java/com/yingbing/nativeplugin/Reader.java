package com.yingbing.nativeplugin;

import com.taobao.weex.annotation.JSMethod;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.MalformedInputException;

import io.dcloud.feature.uniapp.common.UniModule;


/**
 * @Title: TestTranslate
 * @author: 康雷  e-mail: 1014295211@qq.com
 * @date: 2020/8/26 16:45
 * @ClassName: Reader
 * @Description: 读取txt文本内容
 */
public class Reader extends UniModule {


    //判断编码格式方法
    @JSMethod(uiThread = false)
    private static String getFileCharset(String filePath) {
        String[] charsets = {"UTF-8", "GB2312", "US-ASCII", "BIG5", "GBK", "GB18030", "UTF-16BE", "UTF-16LE", "UTF-16", "UNICODE"};
        String charset = Charset.defaultCharset().displayName();
        CharsetDecoder decoder;
        BufferedReader br = null;
        String s = null;
        for (int i = 0; i < charsets.length; i++) {
            decoder = Charset.forName(charsets[i]).newDecoder();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), decoder));
                do {
                    s = br.readLine();
                } while (s != null);
                charset = charsets[i];
                break;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                break;
            } catch (MalformedInputException e) { //如果编码不能解码此文本就会抛出这个异常
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        return charset;
    }

    @JSMethod(uiThread = false)
    public String readAllLines(String filePath) {
        try {
            // 获取输入流
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), getFileCharset(filePath));
            BufferedReader br = new BufferedReader(isr);

            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp+"\r\n");
            }
            br.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
