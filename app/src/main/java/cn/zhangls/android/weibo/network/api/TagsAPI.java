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
 * 该类封装了标签接口
 */
public class TagsAPI extends BaseAPI {

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public TagsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 返回指定用户的标签列表。
     * 
     * @param uid       要获取的标签列表所属的用户ID
     * @param count     单页返回的记录条数，默认为20
     * @param page      返回结果的页码，默认为1
     */
    public void tags(long uid, int count, int page) {
        // TODO  ".json"
    }

    /**
     * 批量获取用户的标签列表。
     * 
     * @param uids      要获取标签的用户ID。最大20
     */
    public void tagsBatch(String[] uids) {
        // TODO "/tags_batch.json"
    }

    /**
     * 获取系统推荐的标签列表。
     * 
     * @param count     返回记录数，默认10，最大10
     */
    public void suggestions(int count) {
        // TODO "/suggestions.json"
    }

    /**
     * 为当前登录用户添加新的用户标签(无论调用该接口次数多少，每个用户最多可以创建10个标签)。
     * 
     * @param tags      要创建的一组标签，每个标签的长度不可超过7个汉字，14个半角字符
     */
    public void create(String[] tags) {
        // TODO "/create.json"
    }

    /**
     * 删除一个用户标签。
     * 
     * @param tag_id    要删除的标签ID
     */
    public void destroy(long tag_id) {
        // TODO  "/destroy.json"
    }

    /**
     * 批量删除一组标签。
     * 
     * @param ids       要删除的一组标签ID，一次最多提交10个ID
     */
    public void destroyBatch(String[] ids) {
        // TODO "/destroy_batch.json"
    }
}
