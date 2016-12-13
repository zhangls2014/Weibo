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
 * 评论结构体。
 * 
 * @author SINA
 * @since 2013-11-24
 */
public class Comment {

    /** 评论创建时间 */
    public String created_at;
    /** 评论的 ID */
    public String id;
    /** 评论的内容 */
    public String text;
    /** 评论的来源 */
    public String source;
    /** 评论作者的用户信息字段 */
//    public User user;
    /** 评论的 MID */
    public String mid;
    /** 字符串型的评论 ID */
    public String idstr;
    /** 评论的微博信息字段 */
//    public Status status;
    /** 评论来源评论，当本评论属于对另一评论的回复时返回此字段 */
    public Comment reply_comment;
    
    public static Comment parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Comment comment = new Comment();
        comment.created_at    = jsonObject.optString("created_at");
        comment.id            = jsonObject.optString("id");
        comment.text          = jsonObject.optString("text");
        comment.source        = jsonObject.optString("source");
//        comment.user          = User.parse(jsonObject.optJSONObject("user"));
        comment.mid           = jsonObject.optString("mid");
        comment.idstr         = jsonObject.optString("idstr");
//        comment.status        = Status.parse(jsonObject.optJSONObject("status"));
        comment.reply_comment = Comment.parse(jsonObject.optJSONObject("reply_comment"));
        
        return comment;
    }
}
