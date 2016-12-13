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
 * 位置信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class Poi {

    /** Poi id */
    public String poiid;
    /** 名称 */
    public String title;
    /** 地址 **/
    public String address;
    /** 经度 **/
    public String lon;
    /** 纬度 **/
    public String lat;
    /** 分类 **/
    public String category;
    /** 城市 **/
    public String city;
    /** 省 **/
    public String province;
    /** 国家 **/
    public String country;
    /** 链接 **/
    public String url;
    /** 电话**/
    public String phone;
    /** 邮政编码 **/
    public String postcode;
    /** 微博ID **/
    public String weibo_id;
    /** 分类码 **/
    public String categorys;
    /** 分类名称 **/
    public String category_name;
    /** 图标 **/
    public String icon;
    /** 签到数 **/
    public String checkin_num;
    /** 签到用户数 **/
    public String checkin_user_num;
    /** tip数 **/
    public String tip_num;
    /** 照片数 **/
    public String photo_num;
    /** todo数量 **/
    public String todo_num;
    /** 距离 **/
    public String distance;

    public static Poi parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        Poi poi = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            poi = parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return poi;
    }

    public static Poi parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        
        Poi poi = new Poi();
        poi.poiid               = jsonObject.optString("poiid");
        poi.title               = jsonObject.optString("title");
        poi.address             = jsonObject.optString("address");
        poi.lon                 = jsonObject.optString("lon");
        poi.lat                 = jsonObject.optString("lat");
        poi.category            = jsonObject.optString("category");
        poi.city                = jsonObject.optString("city");
        poi.province            = jsonObject.optString("province");
        poi.country             = jsonObject.optString("country");
        poi.url                 = jsonObject.optString("url");
        poi.phone               = jsonObject.optString("phone");
        poi.postcode            = jsonObject.optString("postcode");
        poi.weibo_id            = jsonObject.optString("weibo_id");
        poi.categorys           = jsonObject.optString("categorys");
        poi.category_name       = jsonObject.optString("category_name");
        poi.icon                = jsonObject.optString("icon");
        poi.checkin_num         = jsonObject.optString("checkin_num");
        poi.checkin_user_num    = jsonObject.optString("checkin_user_num");
        poi.tip_num             = jsonObject.optString("tip_num");
        poi.photo_num           = jsonObject.optString("photo_num");
        poi.todo_num            = jsonObject.optString("todo_num");
        poi.distance            = jsonObject.optString("distance");
        
        return poi;
    }
}
