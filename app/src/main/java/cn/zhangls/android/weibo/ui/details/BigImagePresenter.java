package cn.zhangls.android.weibo.ui.details;

import android.content.Context;
import android.util.Log;

/**
 * Created by zhangls on 2016/12/6.
 *
 */

class BigImagePresenter implements BigImageContract.Presenter {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 视图对象
     */
    private BigImageContract.BigImageView mBigImageView;

    public BigImagePresenter(Context context, BigImageContract.BigImageView bigImageView) {
        mContext = context;
        mBigImageView = bigImageView;
        mBigImageView.setPresenter(this);
    }

    /**
     * Image 点击事件
     */
    @Override
    public void onClick() {

    }

    /**
     * Image 长按事件
     */
    @Override
    public void onLongClick() {

    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {

    }
}
