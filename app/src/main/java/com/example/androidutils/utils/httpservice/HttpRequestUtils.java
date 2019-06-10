package com.example.androidutils.utils.httpservice;

/**
 * @功能:
 * @Creat 2019/04/30 10:32
 * @User Lmy
 * @By Android Studio
 */


import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\9\17 0017 9:50
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class HttpRequestUtils {
    private static HttpRequestUtils httpRequestUtils;

    private static int overtime = 600;//超时时间
    //请求方式
    public static final int REQUEST_GET = 1;
    public static final int REQUEST_POST = 2;
    public static final int REQUEST_PUT = 3;
    public static final int REQUEST_DELETE = 4;
    //编码集
    public static final String CHARSET_NAME_UTF = "UTF-8";
    public static final String CHARSET_NAME_GPK = "gbk";
    //错误返回 500 找不到网络路径
    public static String error = "{\"code\":500,\"error\":\"服务器内部错误\"}";
    //文件下载保存的目录
    public static final String FILE_SAVE_CATALOGUE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Downloads";//文件存储路径  /storage/emulated/0
    public String savePath = null; // 储存下载文件的目录


    /**
     * 单例模式
     */
    public static HttpRequestUtils getInstance() {
        return httpRequestUtils == null ? new HttpRequestUtils() : httpRequestUtils;
    }

    /**
     * 错误信息
     *
     * @param error
     */
    public void setError(String error) {
        HttpRequestUtils.error = error;
    }

    /**
     * 设置超时-时间
     *
     * @param overtime
     */
    public void setOvertime(int overtime) {
        HttpRequestUtils.overtime = overtime;
    }

    public String getRequestRresults(String url, HashMap<String, String> headers, RequestBody requestBody, int mode) {
        String result = null;
        if (requestBody == null) {
            requestBody = new FormBody.Builder().build();
        }
        switch (mode) {
            case REQUEST_GET:
                result = doGet(url, headers);
                break;
            case REQUEST_POST:
                result = doPost(url, headers, requestBody);
                break;
            case REQUEST_PUT:
                result = doPut(url, headers, requestBody);
                break;
            case REQUEST_DELETE:
                result = doDelete(url, headers, requestBody);
                break;
        }
        return result;
    }

    /**
     * 本地位置返回文件 ，读取Json文件
     *
     * @param context
     * @param localFile
     * @return json
     */
    public String readJson(Context context, String localFile) throws IOException {
        InputStream is = context.getAssets().open(localFile);
        BufferedReader reader = null; //从给定位置获取文件流
        StringBuffer data = new StringBuffer();   //返回值,使用StringBuffer
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String temp = null;  //每次读取文件的缓存
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return data.toString();
    }

    /**
     * 本地位置返回文件 ，读取Json文件
     *
     * @param context
     * @param localFile   当地文件
     * @param charsetName 字符集
     */


    public String readJson(Context context, String localFile, String charsetName) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = context.getClass().getClassLoader().getResourceAsStream(localFile);
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1) {
            sb.append(new String(buf, 0, len, charsetName));
        }
        is.close();
        return sb.toString();
    }

    /**
     * get 方式请求
     *
     * @param url
     * @param headers
     * @return
     */
    public String doGet(String url, HashMap<String, String> headers) {
        Request request = new Request
                .Builder()
                .url(url)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .build();
        return execute(request);
    }

    /**
     * Put 方式请求
     *
     * @param url
     * @param headers
     * @param builder
     * @return
     */
    public String doPut(String url, HashMap<String, String> headers, RequestBody builder) {
        Request request = new Request
                .Builder()
                .url(url)
                .put(builder)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .build();
        return execute(request);
    }

    /**
     * Post 方式请求
     *
     * @param url
     * @param headers
     * @param builder
     * @return
     */
    public String doPost(String url, HashMap<String, String> headers, RequestBody builder) {
        Request request = new Request
                .Builder()
                .url(url)
                .post(builder)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .build();
        return execute(request);
    }

    /**
     * delete 方式请求
     *
     * @param url
     * @param headers
     * @param builder
     * @return
     */
    public String doDelete(String url, HashMap<String, String> headers, RequestBody builder) {
        Request request = new Request
                .Builder()
                .url(url)
                .delete(builder)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .build();
        return execute(request);
    }

    /**
     * 执行
     *
     * @param request 请求数据
     * @return
     */
    private String execute(Request request) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Response response = okHttpClient
                    .newBuilder()
                    .writeTimeout(overtime, TimeUnit.SECONDS)//超时时间
                    .readTimeout(overtime, TimeUnit.SECONDS)
                    .connectTimeout(overtime, TimeUnit.SECONDS)
                    .build()
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            return error;
        } catch (IOException e) {
            Log.e("HttpRequestUtils", e.getMessage());
            e.printStackTrace();
            return error;
        }
    }

    /**
     * 执行
     *
     * @param request
     * @param responseCallback
     */
    private void execute(Request request, Callback responseCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(responseCallback);
    }

/****************************************************下载文件************************************************/

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { // 下载失败
                listener.onDownloadFailed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                savePath = isExistDir(saveDir == null ? FILE_SAVE_CATALOGUE : saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        listener.onDownloading(progress);// 下载中
                    }
                    fos.flush();
                    listener.onDownloadSuccess(file); // 下载完成
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (is != null)
                        is.close();
                    if (fos != null)
                        fos.close();
                }
            }
        });
    }

    /**
     * @param saveDir 判断下载目录是否存在
     * @return
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        //················下载成功·················
        void onDownloadSuccess(File file);

        //················下载进度·················
        void onDownloading(int progress);

        //················下载失败·················
        void onDownloadFailed(Call call, IOException e);
    }
/****************************************************参数处理************************************************/
    /**
     * 参数（可选）
     *
     * @param key
     * @param value
     * @param is
     * @return
     */
    public String getChoosable(String key, String value, boolean is) {
        if (value.isEmpty()) {
            return "";
        }
        return is ? "&" + key + "=" + value : key + "=" + value;
    }

    /**
     * 参数（可选）Map
     *
     * @param map
     * @param key
     * @param value
     * @return
     */
    public Map getMapChoosable(Map map, String key, String value) {
        if (!value.isEmpty()) {
            map.put(key, value);
        }
        return map;
    }
}
