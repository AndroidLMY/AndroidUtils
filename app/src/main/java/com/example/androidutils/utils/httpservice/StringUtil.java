package com.example.androidutils.utils.httpservice;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static StringUtil stringUtil;

    /**
     * 单例模式
     */
    public static StringUtil getInstance() {
        if (stringUtil == null) {
            stringUtil = new StringUtil();
        }
        return stringUtil;
    }

    //********************************************************************判断***************************************************************************************


    /**
     * 判断字符串是否为空
     */
    public boolean isEmpty(String str) {
        return null == str || "".equals(str) || str.length() == 0 ? true : false;
    }

    /**
     * 判断对象是否为空
     */
    public boolean isEmpty(Object obj) {
        return null == obj || "" == obj ? true : false;
    }




    /**
     * 判断字符串是否为数字
     */
    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串去掉空格是否相等
     */
    public boolean isEquals(String str1, String str2, boolean trim) {
        str1 = str1.trim();
        str2 = str2.trim();
        if (str1 == null)
            return (str2 == null) || ((str2.length() == 0) && (trim));
        if (str1.length() == 0) {
            if (trim) {
                return isEmpty(str2);
            }
            return (str2 != null) && (str2.length() == 0);
        }
        if (trim) {
            return str1.equals(str2);
        }
        return str1.equals(str2);
    }

    /**
     * 判断是否包含非法字符
     *
     * @return true 包含非法字符
     */
    public boolean isContainsIllegalCharacter(String content) {
        char[] charArray = content.toCharArray();
        for (char c : charArray) {
            if (c == '_' || (c >= 48 && c <= 57) || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean containsIllegalCharacter(String content) {
        return !content.matches("[a-zA-Z0-9_\u4e00-\u9fa5]*");
    }
    //********************************************************************类型转换***************************************************************************************

    /**
     * 对象转字符串
     */
    public String valueOf(Object obj) {
        if (obj == null) {
            return "";
        }
        if ((obj instanceof String))
            return (String) obj;
        if ((obj instanceof Object[]))
            return join((Object[]) obj, ",");
        if ((obj instanceof String[]))
            return join((String[]) obj, ",");
        if ((obj instanceof double[]))
            return join((double[]) obj, ",");
        if ((obj instanceof long[]))
            return join((long[]) obj, ",");
        if ((obj instanceof float[]))
            return join((float[]) obj, ",");
        if ((obj instanceof int[]))
            return join((int[]) obj, ",");
        if ((obj instanceof short[]))
            return join((short[]) obj, ",");
        if ((obj instanceof byte[])) {
            return join((byte[]) obj, ",");
        }
        return String.valueOf(obj);
    }



    /**
     * 字符串转整形
     */
    public Integer getInt(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str.trim()));
        } catch (Exception ex) {
            return null;
        }

    }


    /**
     * 字符串转整形数组
     */
    public Integer[] getIntArray(String str, String spliter) {
        if (isEmpty(str)) {
            return new Integer[0];
        }
        String[] strs = str.split(spliter);
        List<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < strs.length; i++) {
            Integer val = getInt(strs[i]);
            if (val != null)
                tmp.add(val);
        }
        return tmp.toArray(new Integer[tmp.size()]);
    }

    /**
     * 字符串转long型
     */
    public Long getLong(String str) {
        try {
            return Long.valueOf(Long.parseLong(str.trim()));
        } catch (Exception ex) {
        }
        return null;
    }

    public Long[] getLongArray(String str, String spliter) {
        if (isEmpty(str)) {
            return new Long[0];
        }
        String[] strs = str.split(spliter);
        List<Long> tmp = new ArrayList<Long>();
        for (int i = 0; i < strs.length; i++) {
            Long val = getLong(strs[i]);
            if (val != null)
                tmp.add(val);
        }
        return tmp.toArray(new Long[tmp.size()]);
    }

    /**
     * 字符串转布尔类型
     */
    public Boolean getBoolean(String str) {
        try {
            return Boolean.valueOf(Boolean.parseBoolean(str.trim()));
        } catch (Exception ex) {
        }
        return null;
    }

    public Boolean[] getBooleanArray(String str, String spliter) {
        if (isEmpty(str)) {
            return new Boolean[0];
        }
        String[] strs = str.split(spliter);
        List<Boolean> tmp = new ArrayList<Boolean>();
        for (int i = 0; i < strs.length; i++) {
            Boolean val = getBoolean(strs[i]);
            if (val != null)
                tmp.add(val);
        }
        return tmp.toArray(new Boolean[tmp.size()]);
    }

    /**
     * 字符串转Floa类型
     */
    public Float getFloat(String str) {
        try {
            return Float.valueOf(Float.parseFloat(str.trim()));
        } catch (Exception ex) {
            return null;
        }
    }

    public Float[] getFloatArray(String str, String spliter) {
        if (isEmpty(str)) {
            return new Float[0];
        }
        String[] strs = str.split(spliter);
        List<Float> tmp = new ArrayList<Float>();
        for (int i = 0; i < strs.length; i++) {
            Float val = getFloat(strs[i]);
            if (val != null)
                tmp.add(val);
        }
        return tmp.toArray(new Float[tmp.size()]);
    }

    /**
     * 字符串转Double类型
     */
    public Double getDouble(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str.trim()));
        } catch (Exception ex) {
        }
        return null;
    }

    public Double[] getDouble(String str, String spliter) {
        if (isEmpty(str)) {
            return new Double[0];
        }
        String[] strs = str.split(spliter);
        List<Double> tmp = new ArrayList<Double>();
        for (int i = 0; i < strs.length; i++) {
            Double val = getDouble(strs[i]);
            if (val != null)
                tmp.add(val);
        }
        return tmp.toArray(new Double[tmp.size()]);
    }

    /**
     * 字符串转整形Int
     */
    public int getInt(String str, int defaultValue) {
        try {
            return Integer.valueOf(Integer.parseInt(str.trim()));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public double getDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * double转字符串 0.00
     */
    public String getZeroDouble(double doub) {
        return new DecimalFormat("0.00").format(doub);
    }

    /**
     * double为0.00 显示 --
     */
    public String getZeroToStr(double doub, String defaults) {
        if (doub == 0) {
            return defaults;
        } else {
            return new DecimalFormat("0.00").format(doub);
        }
    }


    //********************************************************************匹配模式 模型匹配 模式匹配***************************************************************************************

    /**
     * 转 UTF-8
     *
     * @param s
     * @return
     */
    @SuppressLint("DefaultLocale")
    public String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 0) && (c <= 'ÿ')) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    Log.e("StringUtil", ex.getMessage());
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 转 Unicode
     *
     * @param strText
     * @return
     */
    public String toUnicode(String strText) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strText.length(); i++) {
            char c = strText.charAt(i);
            int intAsc = c;
            if (intAsc > 128) {
                sb.append("\\u" + Integer.toHexString(intAsc));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 更改编码
     *
     * @param oldString  旧字符
     * @param oldCharset 旧编码
     * @param newCharset 新编码
     * @return
     */
    @SuppressLint("LongLogTag")
    public String changeEncoding(String oldString, String oldCharset, String newCharset) {
        if (isEmpty(oldString)) {
            return oldString;
        }
        if (isEmpty(newCharset)) {
            return oldString;
        }
        if (oldCharset == null) {
            oldCharset = "UTF-8";
        }
        if (newCharset.trim().equalsIgnoreCase(oldCharset.trim())) {
            return oldString;
        }
        try {
            if (isEmpty(oldCharset)) {
                return new String(oldString.getBytes(), newCharset);
            }
            return new String(oldString.getBytes(oldCharset), newCharset);
        } catch (UnsupportedEncodingException uee) {
            Log.e("由于系统不支持编码[" + oldCharset + "]或者[" + newCharset + "]，因此未能进行转换，直接返回原字符串", uee.getMessage());
        }
        return oldString;
    }


    /**
     * 获取[0,n)之间的一个随机整数
     *
     * @param maximum 最大数 1-10的int随机数
     * @return
     */
    public int getRandom(int maximum) {
//        return (int) (Math.random() * maximum);
        return new Random().nextInt(maximum) + 1;
    }

    /**
     * /获取[m,n]之间的随机数（0<=m<=n）
     *
     * @param m
     * @param n
     * @return
     */
    public int getRandomBetweenNumbers(int m, int n) {
        return (int) (m + Math.random() * (n - m + 1));
    }

    /**
     * 返回一个&位随机数
     *
     * @param digits 位数
     * @return
     */
    public String getDigitsRandom(int digits) {
        Random jjj = new Random();
        if (digits == 0)
            return "";
        String jj = "";
        for (int k = 0; k < digits; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * 获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
     *
     * @param KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 随机字符串的随机字符库
     * @param length  10
     * @return  TH10RMmk5K
     */
    //
    public String getRandomString(String KeyString, int length) {
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    /**
     * 通用唯一标识符(每一次都不同)
     *
     * @return  E90B150AC6E14C948F1F7D2B7D9E28B3
     */
    @SuppressLint("DefaultLocale")
    public String getGUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 把数组 转化为 字符串
     *
     * @param arr
     * @param spliter
     * @return
     */
    public String join(byte[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(short[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(double[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(float[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(int[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(long[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(String[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public String join(Object[] arr, String spliter) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(spliter);
                sb.append(valueOf(arr[i]));
            }
        }
        return sb.toString();
    }

    /**
     * 替换的字符串
     *
     * @param start   开始的位置  3
     * @param end     结束的位置  7
     * @param oldChar 原字符串   "13674928326"
     * @param newChar 替换的字符  "****"
     * @return 136****8326
     */
    public String setReplace(int start, int end, String oldChar, String newChar) {
        return new StringBuilder(oldChar).replace(start, end, newChar).toString();
    }

    /**
     * 替换的字符串
     *
     * @param sentence 句子
     * @param oldChar  原
     * @param newChar  新
     * @return
     */
    public String setReplace(String sentence, String oldChar, String newChar) {
        return sentence.replace(oldChar, newChar);
    }

    /**
     * 去掉
     *
     * @param src
     * @return
     */
    public String escape(String src) {
        return escape(src, "%");
    }

    /**
     * 去掉 某个字段
     *
     * @param src 1000%
     * @param pre "%"
     * @return
     */
    public String escape(String src, String pre) {
        if (src == null) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); i++) {
            char j = src.charAt(i);
            if ((Character.isDigit(j)) || (Character.isLowerCase(j))
                    || (Character.isUpperCase(j))) {
                tmp.append(j);
            } else if (j < 'Ā') {
                tmp.append(pre);
                if (j < '\020')
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append(pre + "u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 转义字符串
     *
     * @param src
     * @return
     */
    public String unescape(String src) {
        return unescape(src, "%");
    }

    public String unescape(String src, String pre) {
        if (src == null)
            // return src;
            return null;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        int pos = 0;
        while (lastPos < src.length()) {
            pos = src.indexOf(pre, lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    char ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    char ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else if (pos == -1) {
                tmp.append(src.substring(lastPos));
                lastPos = src.length();
            } else {
                tmp.append(src.substring(lastPos, pos));
                lastPos = pos;
            }
        }
        return tmp.toString();
    }


    //********************************************************************字符串的格式和校验***************************************************************************************


    /**
     * 将字符串数组转化为用逗号连接的字符串
     */
    public String arrayToString(String[] values) {
        String result = "";
        if (values != null) {
            if (values.length > 0) {
                for (String value : values) {
                    result += value + ",";
                }
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * 将字符串List转换为用逗号连接的字符串
     */
    public String listToString(List<String> list) {
        String result = "";
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                result += list.get(i) + ",";
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    /**
     * 获取字符第几次出现的位置
     */
    public int getCharacterPosition(String string, String divider, int poi) {
        Matcher slashMatcher = Pattern.compile(divider).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            if (mIdx == poi) {
                break;
            }
        }
        return slashMatcher.start();
    }
    //********************************************************************Url地址处理***************************************************************************************

    /**
     * 传入Url地址及需要匹配的参数名
     *
     * @param url       eg：http://www.autocarinn.com/Wap/index.aspx?province=guangdong&mobile=13812345678
     * @param paramName eg：province
     * @return 参数名对应的参数值 eg：guangdong
     */
    public String findParamValue(String url, String paramName) {
        Pattern pattern = Pattern.compile("(^|&|\\?)" + paramName + "=([^&]*)(&|$)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            /**
             * matcher.group(0)返回?province=guangdong&
             * matcher.group(1)返回?
             * matcher.group(2)返回guangdong
             */
            return matcher.group(2);
        }
        return null;
    }


}
