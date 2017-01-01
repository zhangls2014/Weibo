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
import android.support.annotation.NonNull;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 地理信息相关接口
 */
public class LocationAPI extends BaseAPI {

    public LocationAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 根据GPS坐标获取偏移后的坐标
     * 
     * @param longtitude 纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     * @param latitude   纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     */
    public void gps2Offset(Double longtitude, Double latitude) {
        // TODO /geo/gps_to_offset.json
    }

    /**
     * 根据关键词按坐标点范围获取POI点的信息。
     * 
     * @param latitude   纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     * @param longtitude 经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0。
     * @param keyWord    查询的关键词，必须进行URLencode。
     */
    public void searchPoisByGeo(Double longtitude, Double latitude, String keyWord) {
        // TODO /pois/search/by_geo.json
    }

    /**
     * 根据地理信息坐标返回实际地址。
     * 
     * @param longtitude 经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0。
     * @param latitude   纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     */
    public void geo2Address(Double longtitude, Double latitude) {
        // TODO /geo/geo_to_address.json
    }

    /**
     * @see #gps2Offset(Double, Double)
     */
    public String gps2OffsetSync(Double longtitude, Double latitude) {
        // TODO /geo/gps_to_offset.json
        return null;
    }

    /**
     * @see #searchPoisByGeo(Double, Double, String)
     */
    public String searchPoisByGeoSync(Double longtitude, Double latitude, String keyWord) {
        // TODO /geo/geo_to_address.json
        return null;
    }

    /**
     * @see #geo2Address(Double, Double)
     */
    public String geo2AddressSync(Double longtitude, Double latitude) {
        // TODO /geo/geo_to_address.json
        return null;
    }
}
