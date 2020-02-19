package com.etang.nt_eink_launcher.util;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lenovo on 2018/5/24.
 */

public class GetUtils {

    public static String LoginByGet() {
        String msg = "";
        try {
            //get请求的url
            URL url = new URL("https://v1.hitokoto.cn/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式,请求超时信息
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
//            //设置运行输入,输出:
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            //Post方式不能缓存,需手动设置为false
//            conn.setUseCaches(false);
//
//            //设置请求的头信息
//            conn.setRequestProperty("staffid",StaffId );  //当前请求用户StaffId
//            conn.setRequestProperty("timestamp", ApiHelper.GetTimeStamp()); //发起请求时的时间戳（单位：毫秒）
//            conn.setRequestProperty("nonce", ApiHelper.GetRandom()); //发起请求时的随机数

            //开启连接
            conn.connect();
            InputStream inputStream = null;
            BufferedReader reader = null;
            //如果应答码为200的时候，表示成功的请求带了，这里的HttpURLConnection.HTTP_OK就是200
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //获得连接的输入流
                inputStream = conn.getInputStream();
                //转换成一个加强型的buffered流
                reader = new BufferedReader(new InputStreamReader(inputStream));
                //把读到的内容赋值给result
                String result = reader.readLine();
                JSONObject json_test = new JSONObject(result);
                //打印json 数据
//                Log.e("json", json_test.get("Data").toString());
//                Log.e("json", json_test.get("Status").toString());
//                Log.e("json", json_test.get("Info").toString());


                Log.e("Json", json_test.toString());

            }
            //关闭流和连接
            reader.close();
            inputStream.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

}