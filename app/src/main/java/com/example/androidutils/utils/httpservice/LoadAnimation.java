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
    public OnLoadListener getLoadListener(Context context, final int Color, final String content, final Z_TYPE CHART_RECT) {
        final ZLoadingDialog dialog = new ZLoadingDialog(context);
        OnLoadListener loadListener = new OnLoadListener() {
            @Override
            public void onShow() {
                if (dialog != null) {
                    dialog.setLoadingBuilder(CHART_RECT)//设置类型
                            .setLoadingColor(Color)//颜色
                            .setHintText(content)
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
