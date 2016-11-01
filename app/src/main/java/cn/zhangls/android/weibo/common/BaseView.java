package cn.zhangls.android.weibo.common;

/**
 * Created by zhangls on 16/10/25.
 *
 * MVP模式所有的view的父类
 */
public interface  BaseView<T> {
    /**
     * 设置Presenter
     */
    void setPresenter(T presenter);
}
