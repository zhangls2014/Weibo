package cn.zhangls.android.weibo.common;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zhangls on 2016/10/25.
 *
 * 所有的Activity必须继承的父类
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 显示短的Toast
     * @param msg msg
     */
    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长的Toast
     * @param msg msg
     */
    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
