package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * <p>
 * 文件名:KeyBoardUtil
 * </p>
 * <p>
 * 描述:软键盘相关辅助类
 * </p>
 * <p>
 * 内容摘要:打开或关闭软键盘
 * </p>
 * <p>
 * 完成日期:2015年4月16日
 * </p>
 *
 * @author SamWu
 * @version 1.0
 */
public class KeyBoardUtil {
    /**
     * 打开软键盘
     *
     * @param view     可编辑组件
     *                 输入框
     * @param mContext 上下文
     */
    public static <T extends View> void openKeyboard(T view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param view     可编辑组件
     *                 输入框
     * @param mContext 上下文
     */
    public static <T extends View> void closeKeyboard(T view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
