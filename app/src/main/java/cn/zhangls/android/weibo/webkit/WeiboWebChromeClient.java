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

package cn.zhangls.android.weibo.webkit;

import android.view.View;
import android.webkit.WebChromeClient;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/11.
 * <p>
 * 自定义 WebChromeClient
 */

public class WeiboWebChromeClient extends WebChromeClient {

    /**
     * Notify the host application that the current page has entered full
     * screen mode. The host application must show the custom View which
     * contains the web contents &mdash; video or other HTML content &mdash;
     * in full screen mode. Also see "Full screen support" documentation on
     * {@link WebView}.
     *
     * @param view     is the View object to be shown.
     * @param callback invoke this callback to request the page to exit
     */
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {

    }

    @Override
    public void onHideCustomView() {

    }
}
