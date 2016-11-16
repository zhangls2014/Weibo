package cn.zhangls.android.weibo.ui.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;

/**
 * Created by zhangls on 2016/10/28.
 *
 */
class SplashPresenter implements SplashContract.Presenter {

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
        if (mAccessToken.isSessionValid()) {
            mSplashView.toHomeActivity();
        } else {
            mSplashView.toLoginActivity();
        }
    }
}
