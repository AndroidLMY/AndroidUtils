package com.example.androidutils.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 调用系统组件工具和获取本地属性的类
 */
public class SystemUtils {
    private static final String TAG = SystemUtils.class.getSimpleName();
    private static SystemUtils systemUtils;
    public static final int RESULT_OK = -1;
    public static final int RESULT_NO = 0;

    /**
     * 单例模式
     */
    public static SystemUtils getInstance() {
        if (systemUtils == null) {
            systemUtils = new SystemUtils();
        }
        return systemUtils;
    }
    //---------------------------------------------------------- 视频·音频·应用----------------------------------------------------------------------

    /**
     * 调用系统视频播放器
     *
     * @param activity
     * @param path
     */
    public void openVideo(Activity activity, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "video/mp4");
        activity.startActivity(intent);
    }

    /**
     * 调用系统音频播放器
     *
     * @param activity
     * @param path
     */
    public void openAudio(Activity activity, String path) {
        Intent it = new Intent(Intent.ACTION_VIEW);//调用系统自带的播放器
        it.setDataAndType(Uri.parse(path), "audio/mp3");
        activity.startActivity(it);
    }

    /**
     * 调用系统音乐播放器
     *
     * @param activity
     * @param path
     */
    public void openYinyue(Activity activity, String path) {
        Intent internt = new Intent(Intent.ACTION_VIEW);
        internt.setDataAndType(Uri.parse(path), "audio/mp3");
        internt.setComponent(new ComponentName("com.android.music", "com.android.music.MediaPlaybackActivity"));
        activity.startActivity(internt);
    }

    //---------------------------------------------------------- 打电话，短信----------------------------------------------------------------------

    /**
     * 调用拨号界面 ，要使用这个必须在配置文件中加入<uses-permission id="android.permission.open_PHONE" />
     *
     * @param activity
     * @param phone    手机号
     */
    public void openPhone(final Activity activity, final String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 直接拨打电话， 要使用这个必须在配置文件中加入<uses-permission id="android.permission.open_PHONE" />
     *
     * @param activity
     * @param uri      tel:13674928321  <a href="tel:4008119568">
     */
    public void openPhone(final Activity activity, Uri  uri) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        activity.startActivity(intent);
//        activity.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }



    /**
     * 发送短信
     *
     * @param activity
     * @param mobile   手机号
     * @param body     内容
     */
    public void sendSMS(Activity activity, String mobile, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobile));
        intent.putExtra("sms_body", body);
        activity.startActivity(intent);
    }

    /**
     * 其二是使用SmsManager： 直接发送短信
     *
     * @param mobile 手机号
     * @param body   内容
     * @return
     */
    public boolean sendSmsManager(String mobile, String body) {
        SmsManager sms = SmsManager.getDefault(); //获取SmsManager
        List<String> texts = sms.divideMessage(body); //如果内容大于70字，则拆分为多条
        for (String text : texts) {  //逐条发送短信
            sms.sendTextMessage(mobile, null, text, null, null);
        }
        return true;
    }

    //---------------------------------------------------------- QQ，微信，网页----------------------------------------------------------------------

    /**
     * 打开QQ的app
     *
     * @param activity
     */
    public void openQQ(Activity activity) {
        try {
            Intent intent12 = new Intent();
            intent12.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.SplashActivity");
            intent12.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent12);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " 启动'QQ'异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开QQ的会话界面
     *
     * @param activity
     * @param QQ       501863587
     */
    public void openQQNumber(Activity activity, String QQ) {
        openQQ(activity, "mqqwpa://im/chat?chat_type=wpa&uin=" + QQ);
    }

    /**
     * 打开QQ的会话界面
     *
     * @param activity
     * @param url      mqqwpa://im/chat?chat_type=wpa&uin=501863587
     */
    public void openQQ(Activity activity, String url) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(activity, " 启动'QQ'异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show(); //打开QQ的方法
        }
    }


    /**
     * 打开微信
     *
     * @param activity
     */
    public void openWeixin(Activity activity) {
        try {
            Toast.makeText(activity, "正在启动微信客户端,请稍后...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " 启动'微信'异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开手机上的  web浏览器
     *
     * @param activity
     * @param url
     */
    public void openWeb(Activity activity, String url) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
//---------------------------------------------------------- 通讯录，联系人列表----------------------------------------------------------------------


    /**
     * 打开通讯录
     */
    public void openAddressList(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Contacts.People.CONTENT_URI);
        activity.startActivity(intent);
    }

    /**
     * 打开联系人列表
     *
     * @param activity
     * @param requestCode 请求码
     */
    public void openLinkman(Activity activity, int requestCode) {
        Intent it = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
        activity.startActivityForResult(it, requestCode);
    }


    /**
     * 打开设置页面
     */
    public void openSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }
    //----------------------------------------------------------安装·卸载APK----------------------------------------------------------------------

    /**
     * 安装软件
     *
     * @param activity
     * @param apkUri
     */
    public void installApk(Context activity,   Uri apkUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }


    /**
     * Uninstall 卸载程序
     */
    public void deleteApk(Activity activity, String packageName) {
        Intent it = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
        activity.startActivity(it);
    }

    //----------------------------------------------------------相机·相册----------------------------------------------------------------------
    private Uri cameraUri;//相机返回的绝对路径
    private File file;    //相机返回的文件

    /**
     * 调用相册
     */
    @Deprecated
    public void openPhotoAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }
    public File getCameraFile() {
        return file;
    }

    public Uri getmCameraUri() {
        return cameraUri;
    }


    /**
     * 裁剪图片方法实现
     *
     * @param activity
     * @param uri
     * @param requestCode
     */
    public void startPhotoZoom(Activity activity, Uri uri, int requestCode) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");   //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param activity
     * @param uri
     * @param width
     * @param height
     * @param requestCode
     */
    public void startPhotoZoom(Activity activity, Uri uri, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);

        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 调用文件
     */
    public static final String TYPE_NO = "*/*";
    public static final String TYPE_IMG = "image/*";
    public static final String TYPE_AUDIO = "audio/*";
    public static final String TYPE_VIDEO = "video/*";
    public static final String TYPE_IMG_VIDEO = "video/*;image/*";

    public void openFile(Activity activity, String type, int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(type);//相同的类型的数据
            activity.startActivityForResult(Intent.createChooser(intent, "选择"), requestCode);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "启动'文件管理器'异常！\n请检查是否安装了该应用");
        }
    }


    /**
     * 检查手机上是否安装了指定的软件
     */
    public boolean isAvilible(Activity activity, String packageName) {
        final PackageManager packageManager = activity.getPackageManager(); //获取packagemanager
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0); //获取所有已安装程序的包信息
        List<String> packageNames = new ArrayList<String>(); //用于存储所有已安装程序的包名
        if (packageInfos != null) { //从pinfo中将包名字逐一取出，压入pName list中
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);//判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
    }


}



