package cn.zhangls.android.weibo;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls on 2016/11/8.
 */

public class TextClickableSpan extends ClickableSpan {
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 需要搜索的字符串
     */
    private String searchStr;

    public TextClickableSpan(Context context, String searchStr) {
        this.context = context;
        this.searchStr = searchStr;
    }

    @Override
    public void onClick(View widget) {
        ToastUtil.showLongToast(context, "您点击了 ：" + searchStr);
    }
}
