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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 地理信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class GeoList {
    public ArrayList<Geo> Geos;

    public static GeoList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        GeoList geoList = new GeoList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.optJSONArray("geos");
            if (jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                geoList.Geos = new ArrayList<Geo>(length);
                for (int ix = 0; ix < length; ix++) {
                    geoList.Geos.add(Geo.parse(jsonArray.optJSONObject(ix)));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return geoList;
    }
}
