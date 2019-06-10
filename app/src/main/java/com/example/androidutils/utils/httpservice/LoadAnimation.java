package com.example.androidutils.utils.httpservice;

import android.content.Context;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * @功能:
 * @Creat 2019/06/10 16:05
 * @User Lmy
 * @By Android Studio
 */
public class LoadAnimation {
    private static LoadAnimation loadAnimation;

    public static LoadAnimation getInstance() {
        if (loadAnimation == null) {
            loadAnimation = new LoadAnimation();
        }
        return loadAnimation;
    }

    public OnLoadListener getLoadListener(Context context, final int loadingColor, final int textColor, final int bgColor, final String content, final Z_TYPE CHART_RECT) {
        final ZLoadingDialog dialog = new ZLoadingDialog(context);
        OnLoadListener loadListener = new OnLoadListener() {
            @Override
            public void onShow() {
                if (dialog != null) {
                    dialog.setLoadingBuilder(CHART_RECT)//设置类型
                            .setLoadingColor(loadingColor)//颜色
                            .setHintText(content)
                            .setHintTextSize(16) // 设置字体大小 dp
                            .setHintTextColor(textColor)  // 设置字体颜色
                            .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                            .setDialogBackgroundColor(bgColor) // 设置背景色，默认白色
                            .show();
                }
            }
            @Override
            public void onConceal() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        };
        return loadListener;
    }
}
