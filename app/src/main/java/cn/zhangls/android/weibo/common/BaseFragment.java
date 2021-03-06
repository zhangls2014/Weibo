/*
 * MIT License
 *
 * Copyright (c) 2017 zhangls2014
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

package cn.zhangls.android.weibo.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.ui.login.LoginActivity;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/5.
 * <p>
 * Fragment 基类
 */

public abstract class BaseFragment extends Fragment {
    /**
     * Fragment 是否可见
     */
    protected boolean isVisible = true;
    /**
     * 是否加载过数据标识符
     */
    protected boolean isLoaded = true;

    /**
     * 实现缓加载策略
     *
     * @param isVisibleToUser 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = getUserVisibleHint();
        //可见且未创建视图时，加载数据
        if (isVisible && !isLoaded) {
            loadData();
            isLoaded = true;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //如果Fragment可见，则加载数据
        if (isVisible) {
            loadData();
            isLoaded = true;
        } else {
            isLoaded = false;
        }
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    protected abstract void loadData();

    /**
     * 显示登录 Snackbar
     *
     * @param view The view to find a parent from.
     */
    protected void showLoginSnackbar(View view) {
        Snackbar.make(view, R.string.login_snackbar_content, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.login_snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.actionStart(getContext());
                    }
                })
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .show();
    }
}
