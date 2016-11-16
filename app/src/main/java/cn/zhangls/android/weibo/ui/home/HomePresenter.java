package cn.zhangls.android.weibo.ui.home;

import android.support.annotation.NonNull;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    HomePresenter(@NonNull HomeContract.View homeView) {
        mHomeView = homeView;

        mHomeView.setPresenter(this);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
    }
}
