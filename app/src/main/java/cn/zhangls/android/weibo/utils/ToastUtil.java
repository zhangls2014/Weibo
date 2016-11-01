package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangls on 2016/10/29.
 *
 */

public class ToastUtil {
    /**
     * 显示短Toast
     * @param context 上下文对象
     * @param msg 内容
     */
    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长Toast
     * @param context 上下文对象
     * @param msg 内容
     */
    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
