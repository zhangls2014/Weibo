package cn.zhangls.android.weibo.ui.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;

/**
 * Created by zhangls on 2016/10/28.
 *
 */
class SplashPresenter implements SplashContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * View对象
     */
    @NonNull
    private SplashContract.View mSplashView;
    /**
     * 封装授权信息
     */
    private Oauth2AccessToken mAccessToken;

    SplashPresenter(Context context, @NonNull SplashContract.View splashView) {
        mSplashView = splashView;
        mSplashView.setPresenter(this);
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
    }

    /**
     * 开始
     */
    @Override
    public void start() {
        //初始化日志设置
        initLogger();

        if (mAccessToken.isSessionValid()) {
            mSplashView.toHomeActivity();
        } else {
            mSplashView.toLoginActivity();
        }
    }

    /**
     * 初始化日志工具
     */
    private void initLogger() {
        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
//                .logAdapter(new AndroidAdapter()); //default AndroidLogAdapter
    }
}
