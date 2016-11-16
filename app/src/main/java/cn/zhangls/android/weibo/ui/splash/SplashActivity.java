package cn.zhangls.android.weibo.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.ui.home.HomeActivity;
import cn.zhangls.android.weibo.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the presenter
        new SplashPresenter(this, this);

        mPresenter.start();
    }

    /**
     * 跳转主页
     */
    @Override
    public void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 跳转登录界面
     */
    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
