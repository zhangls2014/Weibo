package cn.zhangls.android.weibo.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.ui.home.HomeActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        //create the presenter
        new SplashPresenter(this);

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
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
