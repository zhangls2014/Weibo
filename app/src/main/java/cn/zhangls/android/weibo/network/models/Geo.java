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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/16.
 * <p>
 * 地理信息结构体
 */

public class Geo implements Parcelable {
    /**
     * 经度坐标
     */
    @SerializedName("longitude")
    private String longitude;
    /**
     * 维度坐标
     */
    @SerializedName("latitude")
    private String latitude;
    /**
     * 所在城市的城市代码
     */
    @SerializedName("city")
    private String city;
    /**
     * 所在省份的省份代码
     */
    @SerializedName("province")
    private String province;
    /**
     * 所在城市的城市名称
     */
    @SerializedName("city_name")
    private String city_name;
    /**
     * 所在省份的省份名称
     */
    @SerializedName("province_name")
    private String province_name;
    /**
     * 所在的实际地址，可以为空
     */
    @SerializedName("address")
    private String address;
    /**
     * 地址的汉语拼音，不是所有情况都会返回该字段
     */
    @SerializedName("pinyin")
    private String pinyin;
    /**
     * 更多信息，不是所有情况都会返回该字段
     */
    @SerializedName("more")
    private String more;

    protected Geo(Parcel in) {
        longitude = in.readString();
        latitude = in.readString();
        city = in.readString();
        province = in.readString();
        city_name = in.readString();
        province_name = in.readString();
        address = in.readString();
        pinyin = in.readString();
        more = in.readString();
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getMore() {
        return more;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getProvince() {
        return province;
    }

    public String getProvince_name() {
        return province_name;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(city_name);
        dest.writeString(province_name);
        dest.writeString(address);
        dest.writeString(pinyin);
        dest.writeString(more);
    }
}
