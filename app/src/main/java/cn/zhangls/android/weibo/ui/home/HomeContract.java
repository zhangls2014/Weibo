package cn.zhangls.android.weibo.ui.home;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

public interface HomeContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        /**
         * NavigationView Header 的点击事件
         */
        void onHeaderClick();
    }
}
