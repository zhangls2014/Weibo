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

package com.sina.weibo.sdk.openapi.legacy;

import android.content.Context;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

/**
 * 该类封装了微博的注册接口。
 * 详情请参考<a href="http://t.cn/8F1nSzB">注册接口</a>
 * 
 * @author SINA
 * @date 2014-03-03
 */
public class RegisterAPI extends AbsOpenAPI {
    public RegisterAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    private static final String SERVER_URL_PRIX = API_SERVER + "/register";

    /**
     * 验证昵称是否可用。
     * 
     * @param nickname  需要验证的昵称。4-20个字符，支持中英文、数字、"_"或减号
     * @param listener  异步请求回调接口
     */
    public void suggestions(String nickname, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("nickname", nickname);
        requestAsync(SERVER_URL_PRIX + "/verify_nickname.json", params, HTTPMETHOD_GET, listener);
    }

}
