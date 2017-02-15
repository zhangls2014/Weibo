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

package cn.zhangls.android.weibo.ui.details.comment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.CommentList;


/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/7.
 *
 * 评论列表 Fragment
 */
public class CommentFragment extends BaseFragment {
    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * CommentRecyclerAdapter
     */
    private CommentRecyclerAdapter mCommentAdapter;

    /**
     * OnItemClickListener
     */
    private OnItemClickListener mItemClickListener;

    public CommentFragment() {
    }

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fg_item_list_recycler);

        return view;
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    @Override
    protected void loadData() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mCommentAdapter = new CommentRecyclerAdapter(getContext(), new ArrayList<Comment>());
        // 评论列表点击事件监听
        mCommentAdapter.setOnChildClickListener(new CommentRecyclerAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View view, int position, Comment comment) {
                Log.d("CommentFragment", "onChildClick: ============0.0===========");
                if (mItemClickListener != null) {
                    Log.d("CommentFragment", "onChildClick: =======================");
                    mItemClickListener.onItemClick(recyclerView, view, position, comment);
                }
            }
        });
        mRecyclerView.setAdapter(mCommentAdapter);
        ((CommentActivity) getContext()).setOnLoadCommentListener(new CommentActivity.OnLoadCommentListener() {
            @Override
            public void loadCommentList(CommentList commentList) {
                mCommentAdapter.setData(commentList.getComments());
            }
        });
    }

    interface OnItemClickListener {
        /**
         * Item Click 事件
         *
         * @param recyclerView RecyclerView
         * @param view         RecyclerView Item
         * @param position     RecyclerView position
         * @param comment      RecyclerView item data
         */
        void onItemClick(RecyclerView recyclerView, View view, int position, Comment comment);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
