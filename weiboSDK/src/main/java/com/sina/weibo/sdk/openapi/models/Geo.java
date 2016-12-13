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

package com.sina.weibo.sdk.openapi.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 地理信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class Geo {
    
    /** 经度坐标 */
    public String longitude;
    /** 维度坐标 */
    public String latitude;
    /** 所在城市的城市代码 */
    public String city;
    /** 所在省份的省份代码 */
    public String province;
    /** 所在城市的城市名称 */
    public String city_name;
    /** 所在省份的省份名称 */
    public String province_name;
    /** 所在的实际地址，可以为空 */
    public String address;
    /** 地址的汉语拼音，不是所有情况都会返回该字段 */
    public String pinyin;
    /** 更多信息，不是所有情况都会返回该字段 */
    public String more;
    
    public static Geo parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        Geo geo = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            geo = parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return geo;
    }

    public static Geo parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        
        Geo geo = new Geo();
        geo.longitude       = jsonObject.optString("longitude");
        geo.latitude        = jsonObject.optString("latitude");
        geo.city            = jsonObject.optString("city");
        geo.province        = jsonObject.optString("province");
        geo.city_name       = jsonObject.optString("city_name");
        geo.province_name   = jsonObject.optString("province_name");
        geo.address         = jsonObject.optString("address");
        geo.pinyin          = jsonObject.optString("pinyin");
        geo.more            = jsonObject.optString("more");
        
        return geo;
    }
}
