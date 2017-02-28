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

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseRecyclerAdapter;
import cn.zhangls.android.weibo.databinding.ItemCommentListBinding;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.utils.TextUtil;

import java.util.ArrayList;

class CommentRecyclerAdapter extends BaseRecyclerAdapter<Comment, CommentRecyclerAdapter.ViewHolder> {

    private OnChildClickListener mOnChildClickListener;

    private RecyclerView mRecyclerView;

    CommentRecyclerAdapter(Context context, ArrayList<Comment> dataList) {
        super(context, dataList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentListBinding commentListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_comment_list,
                parent,
                false
        );
        ViewHolder holder = new ViewHolder(commentListBinding.getRoot());
        holder.setBinding(commentListBinding);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBinding.setComment(mDataList.get(position));
        // 设置微博头像
        Glide.with(holder.mBinding.itemCommentListAvatar.getContext())
                .load(holder.mBinding.getComment().getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.mBinding.itemCommentListAvatar);
        // 设置头像的点击事件，跳转个人页面
        holder.mBinding.itemCommentListAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.actonStart(holder.mBinding.itemCommentListAvatar.getContext());
            }
        });
        // RecyclerView 的 Item 点击事件
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerView != null && mOnChildClickListener != null) {
                    mOnChildClickListener.onChildClick(
                            mRecyclerView,
                            v,
                            holder.getAdapterPosition(),
                            mDataList.get(holder.getAdapterPosition())
                    );
                }
            }
        });

        holder.mBinding.itemCommentListText.setText(
                TextUtil.convertText(
                        holder.mBinding.getRoot().getContext(),
                        holder.mBinding.getComment().getText(),
                        ContextCompat.getColor(holder.mBinding.getRoot().getContext(), R.color.material_blue_700),
                        (int) holder.mBinding.itemCommentListText.getTextSize()
                )
        );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentListBinding mBinding;

        public ViewHolder(View view) {
            super(view);
        }

        public void setBinding(ItemCommentListBinding binding) {
            mBinding = binding;
        }
    }

    interface OnChildClickListener {
        void onChildClick(RecyclerView recyclerView, View view, int position, Comment comment);
    }

    void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        mOnChildClickListener = onChildClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }
}
