package cn.zhangls.android.weibo.ui.login;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by zhangls on 2016/10/29.
 *
 */

class LoginPresenter implements LoginContract.Presenter {

    /**
     * 登录View
     */
    private LoginContract.View mLoginView;

    LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = loginView;

        mLoginView.setPresenter(this);
    }

    /**
     * 开始
     */
    @Override
    public void start() {
        mLoginView.login();
    }
}