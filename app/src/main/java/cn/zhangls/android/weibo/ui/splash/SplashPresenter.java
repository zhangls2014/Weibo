package cn.zhangls.android.weibo.ui.splash;

import android.support.annotation.NonNull;

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

    SplashPresenter(@NonNull SplashContract.View splashView) {
//        mSplashView = checkNotNull(splashView);
        mSplashView = splashView;
        mSplashView.setPresenter(this);
    }

    /**
     * 在Splash页面开始加载数据
     */
    @Override
    public void initData() {
        mSplashView.toHomeActivity();
    }

    /**
     * 开始
     */
    @Override
    public void start() {
        initData();
    }
}
