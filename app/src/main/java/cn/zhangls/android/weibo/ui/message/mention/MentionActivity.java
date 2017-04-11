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

package cn.zhangls.android.weibo.ui.message.mention;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityMentionBinding;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.ui.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleTextViewProvider;
import cn.zhangls.android.weibo.ui.message.mention.content.MentionCommentViewProvider;
import cn.zhangls.android.weibo.ui.message.mention.content.MentionComment;
import cn.zhangls.android.weibo.ui.message.mention.content.WeiboCard;
import cn.zhangls.android.weibo.ui.message.mention.content.WeiboCardViewProvider;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MentionActivity extends BaseActivity implements MentionContract.View, SwipeRefreshLayout.OnRefreshListener {

    /**
     * ItemViewType 微博不包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_NO_PIC = 0;
    /**
     * ItemViewType 微博包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_HAVE_PIC = 1;
    /**
     * ItemViewType 转发微博
     */
    private static final int ITEM_VIEW_TYPE_RETWEETED_STATUS = 2;
    /**
     * WeiboRecyclerAdapter 适配器
     */
    private MultiTypeAdapter mMultiTypeAdapter;
    /**
     * 类型池
     */
    private Items mItems;
    /**
     * ActivityMessageBinding
     */
    private ActivityMentionBinding mBinding;
    /**
     * MentionContract.Presenter
     */
    private MentionContract.Presenter mMessagePresenter;

    private WeiboListType mWeiboListType = WeiboListType.ALL_WEIBO;
    /**
     * LinearLayoutManager
     */
    private LinearLayoutManager mLayout;

    private enum WeiboListType {
        ALL_WEIBO,
        FOLLOWING_WEIBO,
        ORIGINAL_WEIBO,
        ALL_COMMENT,
        FOLLOWING_COMMENT
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MentionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mention);

        initialize();
    }

    /**
     * 初始化方法
     */
    private void initialize() {
        new MentionPresenter(this, this);
        mMessagePresenter.start();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AttitudesAPI attitudesAPI = new AttitudesAPI(this,
                AccessTokenKeeper.readAccessToken(this));

        //设置RecyclerView
        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 注册文字类型 ViewHolder
        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider(attitudesAPI, true));
        // 注册图片类型 ViewHolder
        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider(attitudesAPI, true));
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(WeiboCard.class, new WeiboCardViewProvider(attitudesAPI, true));
        // 注册评论类型 ViewHolder
        mMultiTypeAdapter.register(MentionComment.class, new MentionCommentViewProvider(true));

        mLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.acMsgRecycler.setLayoutManager(mLayout);
        mBinding.acMsgRecycler.setAdapter(mMultiTypeAdapter);
        // 设置 Item 的类型
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object o) {
                Class m;
                if (o instanceof Comment) {
                    m = MentionComment.class;
                    return m;
                }
                switch (getItemViewType((Status) o)) {
                    case ITEM_VIEW_TYPE_STATUS_NO_PIC:
                        m = SimpleText.class;
                        break;
                    case ITEM_VIEW_TYPE_STATUS_HAVE_PIC:
                        m = Picture.class;
                        break;
                    case ITEM_VIEW_TYPE_RETWEETED_STATUS:
                        m = WeiboCard.class;
                        break;
                    default:
                        m = SimpleText.class;
                        break;
                }
                return m;
            }

            @NonNull
            @Override
            public Object onFlattenItem(@NonNull Object o) {
                return o;
            }
        });

        //设置SwipeRefreshLayout
        mBinding.acMsgSwipeRefresh.setOnRefreshListener(this);
        mBinding.acMsgSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        // 第一次加载页面时，刷新数据
        onRefresh();
        showProgressDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_message_mention, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.isChecked()) {
            return super.onOptionsItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.menu_ac_msg_all_weibo:
                mWeiboListType = WeiboListType.ALL_WEIBO;
                break;
            case R.id.menu_ac_msg_following_weibo:
                mWeiboListType = WeiboListType.FOLLOWING_WEIBO;
                break;
            case R.id.menu_ac_msg_original_weibo:
                mWeiboListType = WeiboListType.ORIGINAL_WEIBO;
                break;
            case R.id.menu_ac_msg_all_comment:
                mWeiboListType = WeiboListType.ALL_COMMENT;
                break;
            case R.id.menu_ac_msg_following_comment:
                mWeiboListType = WeiboListType.FOLLOWING_COMMENT;
                break;
        }
        item.setChecked(true);
        onRefresh();
        showProgressDialog();

        return true;
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(MentionContract.Presenter presenter) {
        mMessagePresenter = presenter;
    }

    /**
     * 显示登录 Snackbar
     */
    @Override
    public void showLoginSnackbar() {
        showLoginSnackbar(mBinding.acMsgRecycler);
    }

    /**
     * 获取 Item View Type
     *
     * @param status 数据
     * @return View Type
     */
    private int getItemViewType(Status status) {
        if (status.getRetweeted_status() != null) {
            return ITEM_VIEW_TYPE_RETWEETED_STATUS;
        } else {
            if (status.getPic_urls() != null && !status.getPic_urls().isEmpty()) {// 微博包含图片
                return ITEM_VIEW_TYPE_STATUS_HAVE_PIC;
            } else {// 微博不包含图片
                return ITEM_VIEW_TYPE_STATUS_NO_PIC;
            }
        }
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        mLayout.scrollToPosition(0);
        switch (mWeiboListType) {
            case ALL_WEIBO:
                mMessagePresenter.requestWeiboTimeline(StatusesAPI.AUTHOR_FILTER_ALL,
                        StatusesAPI.SRC_FILTER_ALL, StatusesAPI.TYPE_FILTER_ALL);
                break;
            case FOLLOWING_WEIBO:
                mMessagePresenter.requestWeiboTimeline(StatusesAPI.AUTHOR_FILTER_ATTENTIONS,
                        StatusesAPI.SRC_FILTER_ALL, StatusesAPI.TYPE_FILTER_ALL);
                break;
            case ORIGINAL_WEIBO:
                mMessagePresenter.requestWeiboTimeline(StatusesAPI.AUTHOR_FILTER_ALL,
                        StatusesAPI.SRC_FILTER_ALL, StatusesAPI.TYPE_FILTER_ORIGAL);
                break;
            case ALL_COMMENT:
                mMessagePresenter.requestCommentTimeline(CommentsAPI.AUTHOR_FILTER_ALL,
                        CommentsAPI.SRC_FILTER_ALL);
                break;
            case FOLLOWING_COMMENT:
                mMessagePresenter.requestCommentTimeline(CommentsAPI.AUTHOR_FILTER_ATTENTIONS,
                        CommentsAPI.SRC_FILTER_ALL);
                break;
        }
    }

    /**
     * 显示@我微博信息
     *
     * @param statusList 微博列表
     */
    @Override
    public void showWeiboMention(StatusList statusList) {
        if (statusList != null) {
            mItems.clear();
            mItems.addAll(statusList.getStatuses());
            mMultiTypeAdapter.notifyDataSetChanged();
        }
        if (mBinding.acMsgSwipeRefresh.isRefreshing()) {
            mBinding.acMsgSwipeRefresh.setRefreshing(false);
        } else {
            closeProgressDialog();
        }
    }

    /**
     * 显示@我的评论
     *
     * @param commentList 评论列表
     */
    @Override
    public void showCommentMention(CommentList commentList) {
        if (commentList != null) {
            mItems.clear();
            mItems.addAll(commentList.getComments());
            mMultiTypeAdapter.notifyDataSetChanged();
        }
        if (mBinding.acMsgSwipeRefresh.isRefreshing()) {
            mBinding.acMsgSwipeRefresh.setRefreshing(false);
        } else {
            closeProgressDialog();
        }
    }
}
