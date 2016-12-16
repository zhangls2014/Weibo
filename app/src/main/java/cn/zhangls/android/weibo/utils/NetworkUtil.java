/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by zhangls on 2016/8/11.
 * 网络相关辅助类
 */

public class NetworkUtil {

    public NetworkUtil() {
        /** 不能被实例化 **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取NetworkInfo
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络连接是否可用
     *
     * @param context 上下文
     * @return 返回boolean值
     */
    public static boolean isNetworkConnected(Context context) {
        // 获取 NetworkInfo 对象
        NetworkInfo networkInfo = getNetworkInfo(context);
        // 判断 NetworkInfo 对象是否为空
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 判断wifi连接是否可用
     *
     * @param context 上下文
     * @return 返回boolean值
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return null != info && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否是移动网络连接是否可用
     *
     * @param context 上下文
     * @return 返回boolean值
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return null != info && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 获取当前网络连接的类型信息
     * 原生方法
     *
     * @param context 上下文
     * @return 返回int值
     */
    public static int getConnectedType(Context context) {
        //获取NetworkInfo对象
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null && networkInfo.isAvailable()) {
            //返回NetworkInfo的类型
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义方法
     *
     * @param context 上下文
     * @return 返回int值
     */
    public static int getNetworkType(Context context) {
        // 结果返回值
        int netType = 0;
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = getNetworkInfo(context);
        // NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) {
            return netType;
        }
        // 否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }
}
