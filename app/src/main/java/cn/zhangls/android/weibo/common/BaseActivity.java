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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;

import cn.zhangls.android.weibo.R;

/**
 * Created by zhangls on 2016/10/25.
 *
 * 所有的Activity必须继承的父类
 */

public abstract class BaseActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {

    protected ProgressDialog mProgressDialog;

    protected SlidingPaneLayout mSlidingPaneLayout;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initSwipeBack();
    }

    /**
     * 是否支持滑动返回
     *
     * @return 是否支持滑动返回
     */
    protected abstract boolean isSupportSwipeBack();

    /**
     * 初始化滑动返回
     */
    private void initSwipeBack() {
        if (isSupportSwipeBack()) {
            mSlidingPaneLayout = new SlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //属性
                Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(mSlidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSlidingPaneLayout.setPanelSlideListener(this);
            mSlidingPaneLayout.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));

            View leftView = new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mSlidingPaneLayout.addView(leftView, 0);

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
            decor.removeView(decorChild);
            decor.addView(mSlidingPaneLayout);
            mSlidingPaneLayout.addView(decorChild, 1);
        }
    }

    /**
     * Called when a sliding pane's position changes.
     *
     * @param panel       The child view that was moved
     * @param slideOffset The new offset of this sliding pane within its range, from 0-1
     */
    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    /**
     * Called when a sliding pane becomes slid completely open. The pane may or may not
     * be interactive at this point depending on how much of the pane is visible.
     *
     * @param panel The child view that was slid to an open position, revealing other panes
     */
    @Override
    public void onPanelOpened(View panel) {
        finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    /**
     * Called when a sliding pane becomes slid completely closed. The pane is now guaranteed
     * to be interactive. It may now obscure other views in the layout.
     *
     * @param panel The child view that was slid to a closed position
     */
    @Override
    public void onPanelClosed(View panel) {

    }

    /**
     * 显示短的Toast
     * @param msg msg
     */
    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长的Toast
     * @param msg msg
     */
    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示进度对话框
     */
    protected void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("loading...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 关闭进度对话框
     */
    protected void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {

        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
