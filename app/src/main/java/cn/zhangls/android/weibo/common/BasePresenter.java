package cn.zhangls.android.weibo.common;

/**
 * Created by zhangls on 16/10/25.
 *
 * 基础Presenter类，连接View和Model
 */
public interface BasePresenter<T extends BaseView> {
    /**
     * Presenter的入口方法
     */
    void start();
}
