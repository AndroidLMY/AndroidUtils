package com.example.androidutils.utils.httpservice;

/**
 * @功能:
 * @Creat 2019/06/10 15:59
 * @User Lmy
 * @By Android Studio
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * 判断当前手机是否有网络连接
 */

public class OkHttp3NetWork {
    private static final String NO_NETWORK_TEXT = "   没有网络能干啥，\n去设置中开启网络吧!";

    /**
     * 访问本地xml信息，用来做测试
     *
     * @param context
     * @param localFile 文件 "city.json" assets 包下（与res同级）
     * @param netWork
     * @throws Exception
     */
    public static void submitDialog(Context context, String localFile, OnNetWorkInterface netWork) throws Exception {
        netWork.result(HttpRequestUtils.getInstance().readJson(context, localFile));     //返回数据  跳到本页面的handleOrderList方法里面 146line
    }
    /**
     * 提交数据,带提示信息
     *
     * @param context
     * @param requetWay  请求方式
     * @param netWork
     */
    public static void submitDialog(Context context, int requetWay,  OnNetWorkInterface netWork) {
        submitData(context, requetWay, null, 25000, netWork);
    }

    /**
     * 提交数据,带提示信息
     *
     * @param context
     * @param requetWay  请求方式
     * @param onLoadListener      加载弹框
     * @param netWork
     */
    public static void submitDialog(Context context, int requetWay, OnLoadListener onLoadListener, OnNetWorkInterface netWork) {

        submitData(context, requetWay, onLoadListener, 25000, netWork);
    }

    /**
     * 提交数据,OKhttp3 请求
     *
     * @param context
     * @param requetWay   请求方式
     * @param onLoadListener     加载弹框
     * @param netWork
     */
    public static void submitData(Context context, int requetWay,  OnLoadListener onLoadListener, int overtime, OnNetWorkInterface netWork) {
        if (!isNetworkAvailable(context)) {   // 如果手机中没有可用的连接，给出提示信息
            Toast.makeText(context, NO_NETWORK_TEXT, Toast.LENGTH_SHORT).show();
            return;
        }
        UploadDataAsyncTask up = new UploadDataAsyncTask(netWork, onLoadListener, overtime, requetWay);
        up.execute();
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url   "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk"
     * @param saveDir
     * @param listener
     */
    public static void submitDownloadFile(Context context, String url, String saveDir, HttpRequestUtils.OnDownloadListener listener) {
        if (!isNetworkAvailable(context)) {   // 如果手机中没有可用的连接，给出提示信息
            Toast.makeText(context, NO_NETWORK_TEXT, Toast.LENGTH_SHORT).show();
            return;
        }
        new HttpRequestUtils().download(url, saveDir, listener);
    }


    /**
     * 判断当前手机是否有网络连接, true, 有可用的网络连接；false,没有可用的网络连接
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (manager == null || info == null || !info.isAvailable()) {
            return false;
        }
        return true;
    }
}
