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
 * 地理位置纠正信息.
 * 
 * @author SINA
 * @since 2013-12-4
 */
public class OffsetGeo {

    public ArrayList<Coordinate> Geos;

    public static OffsetGeo parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        OffsetGeo offsetGeo = new OffsetGeo();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.optJSONArray("geos");
            if (jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                offsetGeo.Geos = new ArrayList<Coordinate>(length);
                for (int ix = 0; ix < length; ix++) {
                    offsetGeo.Geos.add(Coordinate.parse(jsonArray.optJSONObject(ix)));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return offsetGeo;
    }
}

