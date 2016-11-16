package cn.zhangls.android.weibo.ui.home.profile;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.model.User;

/**
 * Created by zhangls on 2016/11/16.
 *
 * Profile 接口
 */

interface ProfileContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取用户信息
         */
        void getUser();
    }

    interface View extends BaseView<Presenter> {
        /**
         * 加载数据
         */
        void loadData(User user);
    }
}
