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

package cn.zhangls.android.weibo.ui.details.comment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.zhangls.android.weibo.R;

public class CommentActivity extends AppCompatActivity {

    /**
     * weibo url
     */
    public static String WEIBO_URL = "weibo_url";
    /**
     * WebView
     */
    private WebView mWebView;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(WEIBO_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        // Appbar 返回按钮
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mWebView = (WebView) findViewById(R.id.ac_comment_web_view);

        mWebView.loadUrl(getIntent().getStringExtra(WEIBO_URL));
        mWebView.setWebViewClient(new WebViewClient() {
            /**
             * Give the host application a chance to take over the control when a new
             * url is about to be loaded in the current WebView. If WebViewClient is not
             * provided, by default WebView will ask Activity Manager to choose the
             * proper handler for the url. If WebViewClient is provided, return true
             * means the host application handles the url, while return false means the
             * current WebView handles the url.
             * <p>
             * <p>Notes:
             * <ul>
             * <li>This method is not called for requests using the POST &quot;method&quot;.</li>
             * <li>This method is also called for subframes with non-http schemes, thus it is
             * strongly disadvised to unconditionally call {@link WebView#loadUrl(String)}
             * with the request's url from inside the method and then return true,
             * as this will make WebView to attempt loading a non-http url, and thus fail.</li>
             * </ul>
             * </p>
             *
             * @param view    The WebView that is initiating the callback.
             * @param request Object containing the details of the request.
             * @return True if the host application wants to leave the current WebView
             * and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mWebView.loadUrl(getIntent().getStringExtra(WEIBO_URL));
                return true;
            }
        });
        // 启用支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 优先使用缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
