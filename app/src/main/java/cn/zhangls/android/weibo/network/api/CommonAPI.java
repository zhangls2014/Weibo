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

package cn.zhangls.android.weibo.network.api;

import android.content.Context;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 此类封装了公共服务的接口。
 */
public class CommonAPI extends BaseAPI {

    /** 国家的首字母，默认为空，代表全部。 */
    public enum CAPITAL {
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
    }

    /** 语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn。 */
    public static final String LANGUAGE_ZH_CN   = "zh-cn";
    public static final String LANGUAGE_ZH_TW   = "zh-tw";
    public static final String LANGUAGE_EN      = "english";

    public CommonAPI(Context context, Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 获取城市列表。
     * 
     * @param province  省份代码
     * @param capital   国家的首字母，a-z，可为空代表返回全部，默认为全部
     * @param language  返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     *                  <li> {@link #LANGUAGE_ZH_CN}
     *                  <li> {@link #LANGUAGE_ZH_TW}
     *                  <li> {@link #LANGUAGE_EN}
     */
    public void getCity(String province, String capital, String language) {
        // TODO "/get_city.json"
    }

    /**
     * 获取国家列表
     *
     * @param capital   国家的首字母，a-z，可为空代表返回全部，默认为全部。
     * @param language  返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     *                  <li> {@link #LANGUAGE_ZH_CN}
     *                  <li> {@link #LANGUAGE_ZH_TW}
     *                  <li> {@link #LANGUAGE_EN}
     */
    public void getCountry(CAPITAL capital, String language) {
        // TODO"/get_country.json"
    }

    /**
     * 获取时区配置表。
     * 
     * @param language  返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     *                  <li> {@link #LANGUAGE_ZH_CN}
     *                  <li> {@link #LANGUAGE_ZH_TW}
     *                  <li> {@link #LANGUAGE_EN}
     */
    public void getTimezone(String language) {
        // TODO "/get_timezone.json"
    }
}
