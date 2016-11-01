package cn.zhangls.android.weibo.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by zhangls on 2016/10/28.
 *
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco
        Fresco.initialize(this);
    }
}
