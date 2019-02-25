package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PhoneInfo
 * @Package com.xuechuan.myapplication.uitls
 * @Description: 获取手机信息
 * @author: L-BackPacker
 * @date: 2018/4/2 16:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/2
 */
public class Utils {
    /**
     * tag
     */
    private static final String TAG = "Utils";

    /**
     * 安装某个应用
     *
     * @param context
     * @param apkFile
     * @return
     */
    public static boolean installApp(Context context, File apkFile) {
        try {
            context.startActivity(getInstallAppIntent(apkFile));
            return true;
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return false;
    }

    /**
     * 获取安装应用的Intent
     *
     * @param apkFile
     * @return
     */
    public static Intent getInstallAppIntent(File apkFile) {
        if (apkFile == null || !apkFile.exists()) {
            return null;
        }

        Utils.chmod("777", apkFile.getAbsolutePath());
        Uri uri = Uri.fromFile(apkFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * ti
     * 查某个包名的App是否已经安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean hasAppInstalled(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据包名启动第三方App
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean launchAppByPackageName(Context context,
                                                 String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }

        try {
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            if (intent != null) {
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return false;
    }

    public static String getAssetsFie(Context context, String name)
            throws IOException {

        InputStream is = context.getAssets().open(name);
        int size = is.available();

        // Read the entire asset into a local byte buffer.
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String content = new String(buffer, "UTF-8");

        return content;

    }

    /**
     * 是否为wifi连接状态?
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        if (networkinfo != null) {
            if ("wifi".equals(networkinfo.getTypeName().toLowerCase(Locale.US))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有网络连?
     *
     * @param context
     * @return
     */
    public static boolean isNetConnect(Context context) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        return networkinfo != null;
    }

    /**
     * 获取权限
     *
     * @param permission 权限
     * @param path       文件路径
     */
    public static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            Log.e(TAG, "chmod", e);
        }
    }

    /**
     * 是否安装了sdcard?
     *
     * @return true表示有，false表示没有
     */
    public static boolean haveSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取系统内部可用空间大小
     *
     * @return available size
     */
    public static long getSystemAvailableSize() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return availCount * blockSize;
    }

    /**
     * 获取sd卡可用空间大小
     *
     * @return available size
     */
    public static long getSDCardAvailableSize() {
        long available = 0;
        if (haveSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }


    /**
     * 获取application层级的metadata
     *
     * @param context
     * @param key
     * @return
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            Object metaObj = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA).metaData
                    .get(key);
            if (metaObj instanceof String) {
                return metaObj.toString();
            } else if (metaObj instanceof Integer) {
                return ((Integer) metaObj).intValue() + "";
            } else if (metaObj instanceof Boolean) {
                return ((Boolean) metaObj).booleanValue() + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, e);
        }
        return "";
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, e);
        }

        return "";
    }

    /**
     * 获取版本�?
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, e);
        }
        return 0;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属�?�density�?
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属�?�density�?
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属�?�scaledDensity�?
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属�?�scaledDensity�?
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 隐藏键盘
     *
     * @param activity activity
     */
    public static void hideInputMethod(Activity activity) {
        hideInputMethod(activity, activity.getCurrentFocus());
    }

    /**
     * 隐藏键盘
     *
     * @param context context
     * @param view    The currently focused view
     */
    public static void hideInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入键盘
     *
     * @param context context
     * @param view    The currently focused view, which would like to receive soft
     *                keyboard input
     */
    public static void showInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * Bitmap缩放，注意源Bitmap在缩放后将会被回收的
     *
     * @param origin
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap origin, int width, int height) {
        float originWidth = origin.getWidth();
        float originHeight = origin.getHeight();

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width) / originWidth;
        float scaleHeight = ((float) height) / originHeight;

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap scale = Bitmap.createBitmap(origin, 0, 0, (int) originWidth,
                (int) originHeight, matrix, true);
        //回收方法
        origin.recycle();
        return scale;
    }

    /**
     * 计算某一时间与现在时间间隔的文字提示
     */
    public static String countTimeIntervalText(long time) {
        long dTime = System.currentTimeMillis() - time;
        // 15分钟
        if (dTime < 15 * 60 * 1000) {
            return "刚刚";
        } else if (dTime < 60 * 60 * 1000) {
            // 几小时前
            return "小时前";
        } else if (dTime < 24 * 60 * 60 * 1000) {
            return (int) (dTime / (60 * 60 * 1000)) + "小时前";
        } else {
            return DateFormat.format("MM-dd kk:mm", System.currentTimeMillis())
                    .toString();
        }
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int x = 0, statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param context
     * @return
     */
    public static int getTitleBarHeight(Activity context) {
        int contentTop = context.getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(context);
    }

    /**
     * 获取屏幕宽度，px
     *
     * @param context
     * @return
     */
    public static float getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度，px
     *
     * @param context
     * @return
     */
    public static float getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕像素密度
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取scaledDensity
     *
     * @param context
     * @return
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * 获取当前小时分钟24小时
     *
     * @return
     */
    public static String getTime24Hours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取电池电量,0~1
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unused")
    public static float getBattery(Context context) {
        Intent batteryInfoIntent = context.getApplicationContext()
                .registerReceiver(null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryInfoIntent.getIntExtra("status", 0);
        int health = batteryInfoIntent.getIntExtra("health", 1);
        boolean present = batteryInfoIntent.getBooleanExtra("present", false);
        int level = batteryInfoIntent.getIntExtra("level", 0);
        int scale = batteryInfoIntent.getIntExtra("scale", 0);
        int plugged = batteryInfoIntent.getIntExtra("plugged", 0);
        int voltage = batteryInfoIntent.getIntExtra("voltage", 0);
        int temperature = batteryInfoIntent.getIntExtra("temperature", 0); // 温度的单位是10�?
        String technology = batteryInfoIntent.getStringExtra("technology");
        return ((float) level) / scale;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    public static String getMobileName() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 是否安装了sdcard
     *
     * @return true表示有，false表示没有
     */
    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取sd卡可用空度
     *
     * @return available size
     */
    public static long getAvailableExternalSize() {
        long available = 0;
        if (hasSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }

    /**
     * 获取内存可用空间
     *
     * @return available size
     */
    public static long getAvailableInternalSize() {
        long available = 0;
        if (hasSDCard()) {
            File path = Environment.getRootDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();

            available = availaBlock * blocSize;
        } else {
            available = -1;
        }
        return available;
    }

    /*
     * 版本控制部分
     */

    /**
     * 是否大于2.2版本及以上
     *
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 是否为2.3版本及以上
     *
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 是否为3.0版本及以上
     *
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否为3.1版本及以上
     *
     * @return
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 是否4.1版本及以上
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static String getPhoneType() {

        String phoneType = Build.MODEL;

        Log.d(TAG, "phoneType is : " + phoneType);

        return phoneType;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getOsVersion() {
        String osversion;
        int osversion_int = getOsVersionInt();
        osversion = osversion_int + "";
        return osversion;

    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static int getOsVersionInt() {
        return Build.VERSION.SDK_INT;

    }

    /**
     * 获取ip地址
     *
     * @return
     */
    @SuppressLint("LongLogTag")
    public static String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        if (!inetAddress.getHostAddress().toString()
                                .equals("null")
                                && inetAddress.getHostAddress() != null) {
                            return inetAddress.getHostAddress().toString()
                                    .trim();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return "";
    }

    /**
     * 获取手机号，几乎获取不到
     *
     * @param context
     * @return
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String phoneNum = mTelephonyMgr.getLine1Number();
        return TextUtils.isEmpty(phoneNum) ? "" : phoneNum;
    }

    /**
     * 获取imei值
     *
     * @param context
     * @return
     */
    public static String getPhoneImei(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String phoneImei = mTelephonyMgr.getDeviceId();
        Log.d(TAG, "IMEI is : " + phoneImei);
        return TextUtils.isEmpty(phoneImei) ? "" : phoneImei;
    }

    /**
     * 获取imsi值
     *
     * @param context
     * @return
     */
    public static String getPhoneImsi(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getApplicationContext().getSystemService(
                        Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String phoneImsi = mTelephonyMgr.getSubscriberId();
        Log.d(TAG, "IMSI is : " + phoneImsi);

        return TextUtils.isEmpty(phoneImsi) ? "" : phoneImsi;
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getLocalMacAddress() {
        String Mac = null;
        try {
            String path = "sys/class/net/wlan0/address";
            if ((new File(path)).exists()) {
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer = new byte[8192];
                int byteCount = fis.read(buffer);
                if (byteCount > 0) {
                    Mac = new String(buffer, 0, byteCount, "utf-8");
                }
                fis.close();
            }

            if (Mac == null || Mac.length() == 0) {
                path = "sys/class/net/eth0/address";
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis.read(buffer_name);
                if (byteCount_name > 0) {
                    Mac = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
                fis.close();
            }

            if (Mac == null || Mac.length() == 0) {
                return "";
            } else if (Mac.endsWith("\n")) {
                Mac = Mac.substring(0, Mac.length() - 1);
            }
        } catch (Exception io) {
            Log.w(TAG, "Exception", io);
        }

        return TextUtils.isEmpty(Mac) ? "" : Mac;
    }

    /**
     * 获取重复字段多的个数
     *
     * @param s
     * @return
     */
    public static int getRepeatTimes(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }

        int mCount = 0;
        char[] mChars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < mChars.length; i++) {
            char key = mChars[i];
            Integer value = map.get(key);
            int count = value == null ? 0 : value.intValue();
            map.put(key, ++count);
            if (mCount < count) {
                mCount = count;
            }
        }

        return mCount;
    }

    /**
     * 判断是否为手机号码
     *
     * @return
     */
    public static boolean isPhoneNum(String phone) {
/*        // 确保每一位都是数�?
//        return !TextUtils.isEmpty(num) && num.matches("1[0-9]{10}")
//                && !isRepeatedStr(num) && !isContinuousNum(num);
        if(StringUtil.isEmpty(phone)||phone.length()!=11)
            return false;
        String reg="^(1[3-5,8])\\d{9}$";
        return phone.matches(reg);*/
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否400服务代码
     *
     * @param num
     * @return
     */
    public static boolean is400or800(String num) {
        return !TextUtils.isEmpty(num)
                && (num.startsWith("400") || num.startsWith("800"))
                && num.length() == 10;
    }

    /**
     * 判断是否区域号码
     *
     * @param num
     * @return
     */
    public static boolean isAdCode(String num) {
        return !TextUtils.isEmpty(num) && num.matches("[0]{1}[0-9]{2,3}")
                && !isRepeatedStr(num);
    }

    /**
     * 判断是否座机号码
     *
     * @param num
     * @return
     */
    public static boolean isPhoneHome(String num) {
        return !TextUtils.isEmpty(num) && num.matches("[0-9]{7,8}")
                && !isRepeatedStr(num);
    }

    /**
     * 判断是否为重复字符串
     *
     * @param str ，需要检查的字符串
     */
    public static boolean isRepeatedStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int len = str.length();
        if (len <= 1) {
            return false;
        } else {
            char firstChar = str.charAt(0);// 第一个字长度
            for (int i = 1; i < len; i++) {
                char nextChar = str.charAt(i);// 第i个字长度
                if (firstChar != nextChar) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断字符串是否为连续数字 45678901
     */
    public static boolean isContinuousNum(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        if (!isNumbericString(str))
            return true;
        int len = str.length();
        for (int i = 0; i < len - 1; i++) {
            char curChar = str.charAt(i);
            char verifyChar = (char) (curChar + 1);
            if (curChar == '9') {
                verifyChar = '0';
            }
            char nextChar = str.charAt(i + 1);
            if (nextChar != verifyChar) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为连续字母 xyZaBcde
     */
    public static boolean isContinuousWord(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (!isAlphaBetaString(str)) {
            return true;
        }
        int len = str.length();
        String local = str.toLowerCase();
        for (int i = 0; i < len - 1; i++) {
            char curChar = local.charAt(i);
            char verifyChar = (char) (curChar + 1);
            if (curChar == 'z') {
                verifyChar = 'a';
            }
            char nextChar = local.charAt(i + 1);
            if (nextChar != verifyChar) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为纯数字
     *
     * @param str ，需要检查的字符串
     */
    public static boolean isNumbericString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]+$");// 从开头到结尾必须全部为数�?
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 判断是否为纯字母
     *
     * @param str
     * @return
     */
    public static boolean isAlphaBetaString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile("^[a-zA-Z]+$");// 从开头到结尾必须全部为字母或者数�?
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 判断是否为纯字母或数字
     *
     * @param str
     * @return
     */
    public static boolean isAlphaBetaNumbericString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");// 从开头到结尾必须全部为字母或者数�?
        Matcher m = p.matcher(str);

        return m.find();
    }

    private static String regEx = "[\u4e00-\u9fa5]";
    private static Pattern pat = Pattern.compile(regEx);

    /**
     * 判断是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        return pat.matcher(str).find();
    }

    /**
     * 模式匹配
     *
     * @param pattern
     * @param input
     * @return
     */
    public static boolean patternMatcher(String pattern, String input) {
        if (TextUtils.isEmpty(pattern) || TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(input);

        return matcher.find();
    }

    /****************************************************************************/
    // import PPutils
    private static int id = 1;

    public static int getNextId() {
        return id++;
    }

    /**
     * 将输入流转为字节数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] read2Byte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        return outSteam.toByteArray();
    }

    /**
     * 判断是否符合月和年的过期时间规则
     *
     * @param date
     * @return
     */
    public static boolean isMMYY(String date) {
        try {
            if (!TextUtils.isEmpty(date) && date.length() == 4) {
                int mm = Integer.parseInt(date.substring(0, 2));
                return mm > 0 && mm < 13;
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }

        return false;
    }

    /**
     * 20120506 共八位，前四位-年，中间两位-月，后两位-日
     *
     * @param date
     * @return
     */
    public static boolean isRealDate(String date, int yearlen) {
        // if(yearlen != 2 && yearlen != 4)
        // return false;
        int len = 4 + yearlen;
        if (date == null || date.length() != len) {
            return false;
        }

        if (!date.matches("[0-9]+")) {
            return false;
        }

        int year = Integer.parseInt(date.substring(0, yearlen));
        int month = Integer.parseInt(date.substring(yearlen, yearlen + 2));
        int day = Integer.parseInt(date.substring(yearlen + 2, yearlen + 4));

        if (year <= 0) {
            return false;
        }
        if (month <= 0 || month > 12) {
            return false;
        }
        if (day <= 0 || day > 31) {
            return false;
        }

        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return day > 30 ? false : true;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    return day > 29 ? false : true;
                }
                return day > 28 ? false : true;
            default:
                return true;
        }
    }

    /**
     * 判断字符串是否为连续字符 abcdef 456789
     */
    public static boolean isContinuousStr(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char curChar = str.charAt(i);
            char nextChar = (char) (curChar + 1);
            if (i + 1 < len) {
                nextChar = str.charAt(i + 1);
            }
            if (nextChar != (curChar + 1)) {
                return false;
            }
        }
        return true;
    }

    public static final String REGULAR_NUMBER = "(-?[0-9]+)(,[0-9]+)*(\\.[0-9]+)?";

    /**
     * 为字符串中的数字染颜色
     *
     * @param str   ，待处理的字符串
     * @param color ，需要染的颜色
     * @return
     */
    public static SpannableString setDigitalColor(String str, int color) {
        if (str == null)
            return null;
        SpannableString span = new SpannableString(str);

        Pattern p = Pattern.compile(REGULAR_NUMBER);
        Matcher m = p.matcher(str);
        while (m.find()) {
            int start = m.start();
            int end = start + m.group().length();
            span.setSpan(new ForegroundColorSpan(color), start, end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return span;
    }

    /**
     * 汉语
     *
     * @param str
     * @return
     */
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 带分数
     *
     * @param str
     * @param length
     * @return
     */
    public static String getFixedNumber(String str, int length) {
        if (str == null || length <= 0 || str.length() < length) {
            return null;
        }
        Log.d(TAG, "getFixedNumber, str is : " + str);
        Pattern p = Pattern.compile("\\d{" + length + "}");
        Matcher m = p.matcher(str);
        String result = null;
        if (m.find()) {
            int start = m.start();
            int end = start + m.group().length();
            result = str.substring(start, end);
        }

        return result;
    }

    /**
     * 长度有外空间
     *
     * @param s
     * @return
     */
    public static int getLengthWithoutSpace(CharSequence s) {
        int len = s.length();

        int rlen = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != ' ')
                rlen++;
        }

        return rlen;
    }

    /**
     * 获取控件的宽度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(TextView view) {
        // int height = view.getMeasuredHeight();
        // if(0 < height){
        // return height;
        // }
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(TextView view) {
        // int height = view.getMeasuredHeight();
        // if(0 < height){
        // return height;
        // }
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 测量控件的尺度
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {
        // int width =
        // View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // int height =
        // View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // view.measure(width,height);

        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }

    /**
     * 获取位置
     *
     * @param dis
     * @return
     */
    public static String getDisDsrc(float dis) {
        if (dis <= 0) {
            return "";
        }
        String disStr = null;
        if (dis > 1000) {
            disStr = (float) Math.round(dis / 1000 * 10) / 10 + "km";
        } else {
            disStr = dis + "m";
        }
        return disStr;
    }

    /**
     * 有效时间
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比�?2007/02/29会被接受，并转换�?2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或�?�NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判读为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return false;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return true;
    }

    /**
     * 获取随机数据
     *
     * @return
     */
    public static long getRandom8() {
        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
//随机生成数字，并添加到字符串
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
//将字符串转换为数字并输出
        return Integer.parseInt(str.toString());
    }

    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime = 0;

    /***
     * 处理多次点击问题
     * @return
     */
    public static boolean handleOnDoubleClick() {
        long l = System.currentTimeMillis();
        if (l - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = l;
            return false;
        }
        return true;
    }

    /**
     * @param sfm    fragment管理器
     * @param list   fragment集合
     * @param layout 显示fragment 布局
     * @param id     要显示的集合的fragment 的几个
     * @return
     */
    public static void showSelectFragment(FragmentManager sfm, ArrayList<Fragment> list, int layout, int id) {
        if ((id + 1) > list.size()) {
            throw new IndexOutOfBoundsException("超出集合长度");
        }
        if (sfm == null) {
            throw new NullPointerException("FragmentManager不能为空");
        }
        FragmentTransaction transaction = sfm.beginTransaction();
        Fragment fragment = list.get(id);
        if (!fragment.isVisible()) {
            Log.e("ylf", "showSelectFragment: ");
            if (!fragment.isAdded()) {
                transaction.add(layout, fragment, fragment.getClass().getName());
            } else {
                for (int i = 0; i < list.size(); i++) {
                    sfm.beginTransaction().hide(list.get(i)).commit();
                }
                transaction.show(fragment);
            }
        }
        transaction.commit();
    }

/*    public static int getNetWorkStatus(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }*/

    public static int getNetWorkStatus(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo != null && dataNetworkInfo != null && wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {//WIFI已连接,移动数据已连接
                netWorkType = Constants.NETWORK_WIFI;
            } else if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {//WIFI已连接,移动数据已断开
                netWorkType = Constants.NETWORK_WIFI;
            } else if (dataNetworkInfo != null && dataNetworkInfo.isConnected()) {//WIFI已断开,移动数据已连接
                netWorkType = Constants.NETWORK_CLASS_4_G;
            } else {//WIFI已断开,移动数据已断开
                netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
            }
        } else {
            //这里的就不写了，前面有写，大同小异
            System.out.println("API level 大于21");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //通过循环将网络信息逐个取出来.
            boolean isNetWork = false;
            boolean isWift = false;
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo != null && networkInfo.isConnected()) {
                    isNetWork = true;
                }
                if (networkInfo != null && networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                    isWift = true;
                }
            }
            if (isNetWork) {
                if (isWift) {
                    netWorkType = Constants.NETWORK_WIFI;
                } else
                    netWorkType = Constants.NETWORK_CLASS_4_G;
            } else {
                netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
            }
        }
    /*    ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }*/

        return netWorkType;
    }

    public static String getNewType(Context context) {
        int status = getNetWorkStatus(context);
        String netType = "wift";
        switch (status) {
            case Constants.NETWORK_CLASS_UNKNOWN:
                netType = null;
                break;
            case Constants.NETWORK_CLASS_2_G:
                netType = "2G";
                break;
            case Constants.NETWORK_CLASS_3_G:
                netType = "3G";
                break;
            case Constants.NETWORK_CLASS_4_G:
                netType = "4G";
                break;
            case Constants.NETWORK_WIFI:
                netType = "wifi";
                break;
            default:

        }
        return netType;
    }

   /* public static String getNewType(Context context) {
        String work = MyAppliction.getInstance().getUserNerWork();
        if (StringUtil.isEmpty(work) || work.equalsIgnoreCase(DataMessageVo.NONETWORK)) {
            return "";
        }
        return work;
    }*/


    private static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_CLASS_4_G;

            default:
                return Constants.NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 获取分辨率
     *
     * @return
     */
    public static String getdp(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String with = String.valueOf(width);
        String heights = String.valueOf(height);
        return with + "*" + heights;
    }

    /**
     * 获取dpi
     *
     * @return
     */
    public static String getdpi(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        return String.valueOf(metrics.densityDpi);
    }

    public static void showPassWord(CheckBox mChbLoginEyable, final EditText editText) {
        mChbLoginEyable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    //mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    /**
                     * 第二种
                     */
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    //mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    /**
                     * 第二种
                     */
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(final Activity activity, final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 300);
    }

    public static String convertFileSizeB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 150*****7856
     *
     * @param phone
     * @return
     */
    public static String phoneData(String phone) {
        String str = "****";
        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3, 7, str);
        return sb.toString();
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isNet(Context context) {
        String newType = Utils.getNewType(context);
        if (StringUtil.isEmpty(newType)) {
            return false;
        }
        return true;
    }

    public static ArrayList<String> getPronvers(String msg) {
        ArrayList<String> titleMsg = new ArrayList<>();
        String[] split = msg.split("-");
        for (int i = 0; i < split.length; i++) {
            titleMsg.add(split[i]);
        }
        return titleMsg;
    }

    /**
     * 获取键盘是否弹出
     *
     * @param root
     * @return
     */
    public static boolean getKeybordStatus(final View root) {
        final int[] screenHeight = new int[1];
        final int[] myHeight = new int[1];
        final int[] heightDiff = new int[1];
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenHeight[0] = root.getRootView().getHeight();
                myHeight[0] = root.getHeight();
                heightDiff[0] = screenHeight[0] - myHeight[0];
            }
        });
        if (heightDiff[0] > 100) {
            return true;
        } else {
            return false;
        }
    }

    public static String getYouZanUrl(Context context, String orderNumber) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(context.getResources().getString(R.string.http_youzan_hear));
        buffer.append(context.getResources().getString(R.string.http_youzan_key));
        buffer.append("=");
        buffer.append(orderNumber);
        buffer.append("&");
        buffer.append(context.getResources().getString(R.string.http_youzan_tipu));
        return buffer.toString();
    }

    /**
     * 返回int类型集合
     *
     * @param ids
     * @return
     */
    public static ArrayList<Integer> getArrayToArrayList(Integer[] ids) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        return list;
    }

    public static <T> T getGosnT(String success, Class<T> c) {
        Gson gson = new Gson();
        T t = gson.fromJson(success, c);
        return t;
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean delectFile(File path) {
        if (path.exists()) {
            if (path.isFile()) {
                path.delete();//如果是文件，直接删除
            } else if (path.isDirectory()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            path.delete();
            return true;
        }
        return false;
    }
    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
