package cn.zhangls.android.weibo.ui.splash;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;

/**
 * Created by zhangls on 2016/10/28.
 *
 * contract class for splash view presenter
 */

interface SplashContract {

    interface Presenter extends BasePresenter {
        /**
         * 在Splash页面开始加载数据
         */
        void initData();
    }

    interface View extends BaseView<Presenter> {
        /**
         * 跳转主页
         */
        void toHomeActivity();
    }
}
