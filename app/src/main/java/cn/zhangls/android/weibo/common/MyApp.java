package cn.zhangls.android.weibo.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by zhangls on 2016/10/28.
 *
 */

public class MyApp extends Application {

    private String TAG = this.getClass().getName();

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco
        Fresco.initialize(this);
        //初始化日志打印工具
        initLogger();
    }

    /**
     * 初始化日志工具
     */
    private void initLogger() {
        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL,Use LogLevel.NONE for the release versions.
                .methodOffset(2);                // default 0
//                .logAdapter(new AndroidAdapter()); //default AndroidLogAdapter
    }
}
