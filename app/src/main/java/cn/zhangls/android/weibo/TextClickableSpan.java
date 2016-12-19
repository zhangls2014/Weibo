/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.zhangls.android.weibo;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import cn.zhangls.android.weibo.ui.details.comment.CommentActivity;
import cn.zhangls.android.weibo.utils.TextUtil;
import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls on 2016/11/8.
 *
 * 文本内容点击事件实现
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
    /**
     * 点击文本内容的类型
     */
    private int clickType;

    public TextClickableSpan(Context context, String searchStr, int clickType) {
        this.context = context;
        this.searchStr = searchStr;
        this.clickType = clickType;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        // 去掉下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        ToastUtil.showLongToast(context, "您点击了 ：" + searchStr);
        switch (clickType) {
            case TextUtil.CLICK_TYPE_USER_NAME:

                break;
            case TextUtil.CLICK_TYPE_TOPIC:

                break;
            case TextUtil.CLICK_TYPE_LINK:
                CommentActivity.actionStart(context, searchStr);
                break;
        }
    }
}
