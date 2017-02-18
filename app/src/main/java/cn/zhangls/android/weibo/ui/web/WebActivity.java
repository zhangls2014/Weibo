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

package cn.zhangls.android.weibo.ui.web;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityWebBinding;

public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";

    /**
     * Weibo base url
     */
    private final static String WEIBO_URL = "weibo_url";
    /**
     * web url
     */
    private String mWeiboUrl;
    private ActivityWebBinding mBinding;

    public static void actionStart(Context context, String webUrl) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEIBO_URL, webUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        initialize();
    }

    /**
     * 是否支持滑动返回
     *
     * @return 是否支持滑动返回
     */
    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 初始化方法
     */
    private void initialize() {
        // Appbar 返回按钮
        setSupportActionBar(mBinding.acWebToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWeiboUrl = getIntent().getStringExtra(WEIBO_URL);

        Log.d("WebActivity", "initialize: ==========" + mWeiboUrl);

        //设置SwipeRefreshLayout
        mBinding.acWebSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.acWebWebView.reload();
            }
        });
        mBinding.acWebSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));

        mBinding.acWebWebView.loadUrl(mWeiboUrl);
        mBinding.acWebWebView.setWebChromeClient(new WebChromeClient() {
            /**
             * Tell the host application the current progress of loading a page.
             *
             * @param view        The WebView that initiated the callback.
             * @param newProgress Current page loading progress, represented by
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: ============ " + newProgress);
                if (newProgress == 100) {
                    mBinding.acWebSwipeRefresh.setRefreshing(false);
                }
            }
        });
        mBinding.acWebWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mBinding.acWebWebView.loadUrl(mWeiboUrl);
                return true;
            }
        });
        // 启用支持javascript
        mBinding.acWebWebView.getSettings().setJavaScriptEnabled(true);
        // 优先使用缓存
        mBinding.acWebWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.acWebWebView.canGoBack()) {
            mBinding.acWebWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}