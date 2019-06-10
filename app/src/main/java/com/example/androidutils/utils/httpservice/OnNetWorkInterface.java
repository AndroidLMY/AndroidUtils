package com.example.androidutils.utils.httpservice;

/**
 * @功能:
 * @Creat 2019/04/30 10:31
 * @User Lmy
 * @By Android Studio
 */
/**
 * 数据的提交接口
 *
 * @author redkid
 */
public interface OnNetWorkInterface {
    /**
     * 校验UI数据
     *
     * @return true往下进行，false停止 （注：返回false时自行处理提示信息）
     */
    boolean validate();

    /**
     * 需要提交服务器的数据
     */
    SubmitData getSubmitData();

    /**
     * 处理服务器返回数据
     *
     * @param result 返回数据结果
     */
    void result(final String result) throws Exception;

}
