package com.example.androidutils.utils;

import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {
    private static WebViewUtils webViewUtils;

    /**
     * 单例模式
     */
    public static WebViewUtils getInstance() {
        if (webViewUtils == null) {
            webViewUtils = new WebViewUtils();
        }
        return webViewUtils;
    }
    /**
     * webview 显示HTML代码
     *
     * @param webview
     * @param baseUrl 网址
     * @param data    HTML代码
     */
    public void setWebViewHTML(final WebView webview, String baseUrl, String data) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        //设置自适应屏幕，两者合用
        webview.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        webview.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setBlockNetworkImage(false);
        webview.getSettings().setDefaultFontSize(44);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                webview.measure(w, h); //重新测量
            }
        });
        webview.loadDataWithBaseURL(baseUrl, data, "text/html", "UTF-8", null);
    }
}
