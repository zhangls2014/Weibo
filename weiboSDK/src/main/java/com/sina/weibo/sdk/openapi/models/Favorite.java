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

/**
 * 我喜欢的微博信息结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class Favorite {

    /** 我喜欢的微博信息 */
//    public Status status;
    /** 我喜欢的微博的 Tag 信息 */
//    public ArrayList<Tag> tags;
    /** 创建我喜欢的微博信息的时间 */
    public String favorited_time;
    
    public static Favorite parse(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            return Favorite.parse(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Favorite parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Favorite favorite = new Favorite();
//        favorite.status         = Status.parse(jsonObject.optJSONObject("status"));
        favorite.favorited_time = jsonObject.optString("favorited_time");
            
        JSONArray jsonArray    = jsonObject.optJSONArray("tags");
        if (jsonArray != null && jsonArray.length() > 0) {
            int length = jsonArray.length();
//            favorite.tags = new ArrayList<Tag>(length);
            for (int ix = 0; ix < length; ix++) {
//                favorite.tags.add(Tag.parse(jsonArray.optJSONObject(ix)));
            }
        }

        return favorite;
    }
}
