package cn.zhangls.android.weibo.ui.login;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;

/**
 * Created by zhangls on 2016/10/29.
 *
 */

interface LoginContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        /**
         * 登录操作
         */
        void login();
    }
}
