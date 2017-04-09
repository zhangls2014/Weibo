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

package cn.zhangls.android.weibo.ui.message.comment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityMessageCommentBinding;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.ui.message.comment.content.ReplyComment;
import cn.zhangls.android.weibo.ui.message.comment.content.ReplyCommentViewProvider;
import cn.zhangls.android.weibo.ui.message.mention.content.MentionComment;
import cn.zhangls.android.weibo.ui.message.mention.content.MentionCommentViewProvider;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/25.
 * <p>
 * 评论消息
 */

public class CommentActivity extends BaseActivity implements CommentContract.View, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 微博评论类型， 0：对微博的评论、1：对评论的回复
     */
    private static final int COMMENT_TYPE_COMMENT = 0;
    private static final int COMMENT_TYPE_REPLY = 1;

    /**
     * WeiboRecyclerAdapter 适配器
     */
    private MultiTypeAdapter mMultiTypeAdapter;
    /**
     * 类型池
     */
    private Items mItems;
    /**
     * ActivityMessageCommentBinding
     */
    private ActivityMessageCommentBinding mBinding;

    private CommentContract.Presenter mCommentPresenter;

    private CommentListType mCommentListType = CommentListType.ALL_COMMENT;
    private LinearLayoutManager mLayout;

    private enum CommentListType {
        ALL_COMMENT,
        FOLLOWING_COMMENT,
        MINE
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message_comment);

        initialize();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        mLayout.scrollToPosition(0);
        switch (mCommentListType) {
            case ALL_COMMENT:
                mCommentPresenter.requestCommentToMe(CommentsAPI.AUTHOR_FILTER_ALL);
                break;
            case FOLLOWING_COMMENT:
                mCommentPresenter.requestCommentToMe(CommentsAPI.AUTHOR_FILTER_ATTENTIONS);
                break;
            case MINE:
                mCommentPresenter.requestCommentByMe();
                break;
        }
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mCommentPresenter = presenter;
    }

    private void initialize() {
        new CommentPresenter(this, this);
        mCommentPresenter.start();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置RecyclerView
        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(ReplyComment.class, new ReplyCommentViewProvider(false));
        // 评论类型 ViewHolder
        mMultiTypeAdapter.register(MentionComment.class, new MentionCommentViewProvider(false));

        mLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.acMsgCommentRecycler.setLayoutManager(mLayout);
        mBinding.acMsgCommentRecycler.setAdapter(mMultiTypeAdapter);
        // 设置 Item 的类型
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object o) {
                Class m;
                switch (getItemViewType((Comment) o)) {
                    case COMMENT_TYPE_REPLY:
                        m = ReplyComment.class;
                        break;
                    case COMMENT_TYPE_COMMENT:
                        m = MentionComment.class;
                        break;
                    default:
                        m = MentionComment.class;
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
        mBinding.acMsgCommentSwipeRefresh.setOnRefreshListener(this);
        mBinding.acMsgCommentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        // 第一次加载页面时，刷新数据
        onRefresh();
        showProgressDialog();
    }

    /**
     * 获取 Item View Type
     *
     * @param comment 评论信息
     * @return View Type
     */
    private int getItemViewType(Comment comment) {
        if (comment.getReply_comment() != null) {
            return COMMENT_TYPE_REPLY;
        } else {
            return COMMENT_TYPE_COMMENT;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_message_comment, menu);
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
            case R.id.menu_ac_msg_comment_all_comment:
                mCommentListType = CommentListType.ALL_COMMENT;
                break;
            case R.id.menu_ac_msg_comment_following_comment:
                mCommentListType = CommentListType.FOLLOWING_COMMENT;
                break;
            case R.id.menu_ac_msg_comment_mine:
                mCommentListType = CommentListType.MINE;
                break;
            default:
                mCommentListType = CommentListType.ALL_COMMENT;
                break;
        }
        item.setChecked(true);
        onRefresh();
        showProgressDialog();

        return super.onOptionsItemSelected(item);
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
        if (mBinding.acMsgCommentSwipeRefresh.isRefreshing()) {
            mBinding.acMsgCommentSwipeRefresh.setRefreshing(false);
        } else {
            closeProgressDialog();
        }
    }
}