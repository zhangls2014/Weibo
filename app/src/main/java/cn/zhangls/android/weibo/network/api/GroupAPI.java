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
 * 此类封装了分组的接口。
 */
public class GroupAPI extends BaseAPI {

    /** 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐 */
    private static final int FEATURE_ALL = 0;
    private static final int FEATURE_ORIGINAL = 1;
    private static final int FEATURE_PICTURE = 2;
    private static final int FEATURE_VIDEO = 3;
    private static final int FEATURE_MUSICE = 4;

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public GroupAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 获取当前登录用户某一好友分组的微博列表。
     * 
     * @param list_id       需要查询的好友分组ID，建议使用返回值里的idstr，当查询的为私有分组时，则当前登录用户必须为其所有者
     * @param since_id      若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id        若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count         单页返回的记录条数，默认为50
     * @param page          返回结果的页码，默认为1
     * @param base_app      是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @param featureType   过滤类型ID，0：全部，1：原创， 2：图片，3：视频，4：音乐
     *                      {@link #FEATURE_ALL}
     *                      {@link #FEATURE_ORIGINAL}
     *                      {@link #FEATURE_PICTURE}
     *                      {@link #FEATURE_VIDEO}
     *                      {@link #FEATURE_MUSICE}
     */
    public void timeline(long list_id, long since_id, long max_id, int count, int page, boolean base_app,
                         int featureType) {
        // TODO "/timeline.json"
    }

    // TODO 获取当前登陆用户某一好友分组的微博ID列表
    public void timelineIds() {
    }

    /**
     * 获取某一好友分组下的成员列表。
     * 
     * @param list_id   获取某一好友分组下的成员列表
     * @param count     单页返回的记录条数，默认为50，最大不超过200
     * @param cursor    分页返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     */
    public void members(long list_id, int count, int cursor) {
        // TODO "/members.json"
    }

    // TODO 获取某一好友分组下的成员列表的ID
    public void membersIds() {
    }

    // TODO 批量取好友分组成员的分组说明
    public void memberDescriptionPatch() {
    }

    /**
     * 判断某个用户是否是当前登录用户指定好友分组内的成员。
     * 
     * @param uid       需要判断的用户的UID
     * @param list_id   指定的当前用户的好友分组ID，建议使用返回值里的idstr
     */
    public void isMember(long uid, long list_id) {
        // TODO "/is_member.json"
    }
    // TODO 批量获取某些用户在指定用户的好友分组中的收录信息
    public void listed() {
    }

    /**
     * 获取当前登陆用户某个分组的详细信息。
     * 
     * @param list_id   需要查询的好友分组ID，建议使用返回值里的idstr
     */
    public void showGroup(long list_id) {
        // TODO "/show.json"
    }

    /**
     * 批量获取好友分组的详细信息。
     * 
     * @param list_ids  需要查询的好友分组ID，建议使用返回值里的idstr，多个之间用逗号分隔，每次不超过20个
     * @param uids      参数list_ids所指的好友分组的所有者UID，多个之间用逗号分隔，每次不超过20个，需与list_ids一一对应
     */
    public void showGroupBatch(String list_ids, long uids) {
        // TODO "/show_batch.json"
    }

    /**
     * 创建好友分组。
     * 
     * @param name          要创建的好友分组的名称，不超过10个汉字，20个半角字符
     * @param description   要创建的好友分组的描述，不超过70个汉字，140个半角字符
     * @param tags          要创建的好友分组的标签，多个之间用逗号分隔，最多不超过10个，每个不超过7个汉字，14个半角字符
     */
    public void create(String name, String description, String tags) {
        // TODO "/create.json"
    }

    /**
     * 更新好友分组。
     * 
     * @param list_id       需要更新的好友分组ID，建议使用返回值里的idstr，只能更新当前登录用户自己创建的分组
     * @param name          要创建的好友分组的名称，不超过10个汉字，20个半角字符
     * @param description   要创建的好友分组的描述，不超过70个汉字，140个半角字符
     * @param tags          要创建的好友分组的标签，多个之间用逗号分隔，最多不超过10个，每个不超过7个汉字，14个半角字符
     */
    public void update(long list_id, String name, String description, String tags) {
        // TODO "/update.json"
    }

    /**
     * 删除好友分组。
     * 
     * @param list_id   要删除的好友分组ID，建议使用返回值里的idstr
     */
    public void deleteGroup(long list_id) {
        // TODO  "/destroy.json"
    }

    /**
     * 添加关注用户到好友分组。
     * 
     * @param list_id   好友分组ID，建议使用返回值里的idstr
     * @param uid       好友分组ID，建议使用返回值里的idstr
     */
    public void addMember(long list_id, long uid) {
        // TODO "/members/add.json"
    }

    /**
     * 批量添加用户到好友分组。
     * 
     * @param list_id       好友分组ID，建议使用返回值里的idstr
     * @param uids          需要添加的用户的UID，多个之间用逗号分隔，最多不超过30个
     * @param group_descriptions 添加成员的分组说明，每个说明最多8个汉字，16个半角字符，多个需先URLencode，然后再用半角逗号分隔，最多不超过30个，且需与uids参数一一对应
     */
    public void addMemberBatch(long list_id, String uids, String group_descriptions) {
        // TODO "/members/add_batch.json"
    }

    /**
     * 更新好友分组中成员的分组说明。
     * 
     * @param list_id       好友分组ID，建议使用返回值里的idstr
     * @param uid           需要更新分组成员说明的用户的UID
     * @param group_description 需要更新的分组成员说明，每个说明最多8个汉字，16个半角字符，需要URLencode
     */
    public void updateMembers(long list_id, long uid, String group_description) {
        // TODO  "/members/update.json"
    }

    /**
     * 删除好友分组内的关注用户。
     * 
     * @param list_id   好友分组ID，建议使用返回值里的idstr
     * @param uid       需要删除的用户的UID
     */
    public void deleteMembers(long list_id, long uid) {
        // TODO  "/members/destroy.json"
    }

    /**
     * 调整当前登录用户的好友分组顺序。
     * 
     * @param list_ids  调整好顺序后的分组ID列表，以逗号分隔，例：57,38，表示57排第一、38排第二，以此类推
     * @param count     好友分组数量，必须与用户所有的分组数一致、与分组ID的list_id个数一致
     */
    public void order(String list_ids, int count) {
        // TODO  "/order.json"
    }
}
