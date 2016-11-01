package cn.zhangls.android.weibo.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by zhangls on 2016/10/25.
 *
 * 所有的Activity必须继承的父类
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化日志设置
        initLogger();

        Logger.d(TAG, "onCreate");
    }

    /**
     * 初始化日志工具
     */
    private void initLogger() {
        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
//                .logAdapter(new AndroidAdapter()); //default AndroidLogAdapter
    }

    @Override
    protected void onStart() {
        Logger.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

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
