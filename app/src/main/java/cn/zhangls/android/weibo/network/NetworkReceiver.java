/*
 * MIT License
 *
 * Copyright (c) 2017 zhangls2014
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

package cn.zhangls.android.weibo.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by NickZhang on 2016/12/13.
 * <p>
 * 网络监听类
 * <p>
 * 监听网络的状态变化，只有用户在操作网络连接开关（WIFI，Mobile）时接收广播
 * 然后对相应的界面进行相应的操作，并将网路状态保存在 APP 中
 */
public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";

    public NetworkReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 选择监听类型
//        wifiSwitchListener(intent);
//        wifiConnectListener(intent);
        networkConnectListener(context, intent);
    }

    /**
     * 监听 WIFI 的打开与关闭，与 WIFI 的连接无关
     *
     * @param intent intent
     */
    private void wifiSwitchListener(Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            // wifi state
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.i(TAG, "onReceive: wifiState === " + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    // TODO: 保存 WIFI 状态
                    break;
                case WifiManager.WIFI_STATE_DISABLING:

                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    // TODO: 保存 WIFI 状态
                    break;
                case WifiManager.WIFI_STATE_ENABLING:

                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:

                    break;
                default:

                    break;
            }
        }
    }

    /**
     * 监听 WIFI 的连接状态（是否连接上一个有效的 WIFI，无法监听 WIFI 的打开与关闭）
     *
     * @param intent intent
     */
    private void wifiConnectListener(Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            if (parcelableExtra != null) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                // 网络是否连接
                boolean isConnected = state == NetworkInfo.State.CONNECTED;
                if (isConnected) {
                    // TODO: 保存 WIFI 连接状态
                } else {
                    // TODO: 保存 WIFI 连接状态
                }
            }
        }
    }

    /**
     * 监听网络连接设置，包括 WIFI 和移动数据的打开和关闭
     * WIFI 打开、关闭或连接上可用的连接都会收到通知
     * <p>
     * 该广播反应较慢
     *
     * @param intent intent
     */
    private void networkConnectListener(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        // TODO: 保存网络状态
                        Log.i(TAG, "networkConnectListener: 当前 wifi 连接可用");
                    } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // TODO: 保存网络状态
                        Log.i(TAG, "networkConnectListener: 当前数据流量可用");
                    }
                } else {
                    Log.i(TAG, "networkConnectListener: 当前没有网络连接，请确保你已经打开网络连接");
                }
            } else {
                Log.i(TAG, "networkConnectListener: 当前没有网络连接，请确保你已经打开网络连接");
                // TODO: 保存网络状态
            }
        }
    }
}
