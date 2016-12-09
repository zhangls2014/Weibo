package cn.zhangls.android.weibo.ui.details;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;

/**
 * Created by zhangls on 2016/12/6.
 *
 */

interface BigImageContract {
    interface Presenter extends BasePresenter {
        /**
         * Image 点击事件
         */
        void onClick();

        /**
         * Image 长按事件
         */
        void onLongClick();
    }

    interface BigImageView extends BaseView<Presenter> {
        /**
         * 保存图片到相册
         */
        void saveImage();
    }
}
