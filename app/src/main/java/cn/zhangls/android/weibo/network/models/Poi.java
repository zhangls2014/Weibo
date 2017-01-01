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

package cn.zhangls.android.weibo.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 * <p>
 * 位置信息结构体
 */

public class Poi {

    /**
     * Poi id
     */
    @SerializedName("poiid")
    private String poiid;
    /**
     * 名称
     */
    @SerializedName("title")
    private String title;
    /**
     * 地址
     **/
    @SerializedName("address")
    private String address;
    /**
     * 经度
     **/
    @SerializedName("lon")
    private String lon;
    /**
     * 纬度
     **/
    @SerializedName("lat")
    private String lat;
    /**
     * 分类
     **/
    @SerializedName("category")
    private String category;
    /**
     * 城市
     **/
    @SerializedName("city")
    private String city;
    /**
     * 省
     **/
    @SerializedName("province")
    private String province;
    /**
     * 国家
     **/
    @SerializedName("country")
    private String country;
    /**
     * 链接
     **/
    @SerializedName("url")
    private String url;
    /**
     * 电话
     **/
    @SerializedName("phone")
    private String phone;
    /**
     * 邮政编码
     **/
    @SerializedName("postcode")
    private String postcode;
    /**
     * 微博ID
     **/
    @SerializedName("weibo_id")
    private String weibo_id;
    /**
     * 分类码
     **/
    @SerializedName("categorys")
    private String categorys;
    /**
     * 分类名称
     **/
    @SerializedName("category_name")
    private String category_name;
    /**
     * 图标
     **/
    @SerializedName("icon")
    private String icon;
    /**
     * 签到数
     **/
    @SerializedName("checkin_num")
    private String checkin_num;
    /**
     * 签到用户数
     **/
    @SerializedName("checkin_user_num")
    private String checkin_user_num;
    /**
     * tip数
     **/
    @SerializedName("tip_num")
    private String tip_num;
    /**
     * 照片数
     **/
    @SerializedName("photo_num")
    private String photo_num;
    /**
     * todo数量
     **/
    @SerializedName("todo_num")
    private String todo_num;
    /**
     * 距离
     **/
    @SerializedName("distance")
    private String distance;

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategorys() {
        return categorys;
    }

    public String getCheckin_num() {
        return checkin_num;
    }

    public String getCheckin_user_num() {
        return checkin_user_num;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDistance() {
        return distance;
    }

    public String getIcon() {
        return icon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto_num() {
        return photo_num;
    }

    public String getPoiid() {
        return poiid;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getProvince() {
        return province;
    }

    public String getTip_num() {
        return tip_num;
    }

    public String getTitle() {
        return title;
    }

    public String getTodo_num() {
        return todo_num;
    }

    public String getUrl() {
        return url;
    }

    public String getWeibo_id() {
        return weibo_id;
    }
}
