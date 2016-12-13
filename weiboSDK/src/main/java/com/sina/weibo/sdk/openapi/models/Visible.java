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

import org.json.JSONObject;

/**
 * 微博可见性结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class Visible {
    
    public static final int VISIBLE_NORMAL  = 0;
    public static final int VISIBLE_PRIVACY = 1;
    public static final int VISIBLE_GROUPED = 2;
    public static final int VISIBLE_FRIEND  = 3;
    
    /** type 取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博 */
    public int type;
    /** 分组的组号 */
    public int list_id;
    
    public static Visible parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        
        Visible visible = new Visible();
        visible.type    = jsonObject.optInt("type", 0);
        visible.list_id = jsonObject.optInt("list_id", 0);
        
        return visible;
    }
}