package cn.zhangls.android.weibo.ui.home.weibo;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.model.StatusList;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

interface WeiboContract {
    interface Presenter extends BasePresenter {
        /**
         * 刷新微博
         */
        void getTimeline();

        /**
         * fab 点击事件
         */
        void fabClick();
    }

    interface WeiboView extends BaseView<Presenter> {
        /**
         * 刷新微博
         */
        void onWeiboRefresh();

        /**
         * 回到顶部
         */
        void backToTop();

        /**
         * 完成数据加载
         */
        void refreshCompleted(StatusList publicTimelineStatusList);

        /**
         * 停止刷新动画
         */
        void stopRefresh();
    }
}
