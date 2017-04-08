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
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemCommentContainerBinding;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.ui.details.comment.CommentActivity;
import cn.zhangls.android.weibo.ui.edit.EditActivity;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.utils.TextUtil;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/22.
 *
 * 评论信息页面
 */
public abstract class CommentFrameProvider<SubViewHolder extends RecyclerView.ViewHolder>
        extends ItemViewProvider<Comment, CommentFrameProvider.FrameHolder> {

    /**
     * 是否显示回复按钮
     */
    private boolean canReply;

    private ItemCommentContainerBinding mBinding;

    /**
     * 唯一构造方法
     *
     * @param canReply 是否显示回复按钮
     */
    public CommentFrameProvider(boolean canReply) {
        this.canReply = canReply;
    }

    protected abstract SubViewHolder onCreateContentViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindContentViewHolder(
            @NonNull SubViewHolder holder, @NonNull Comment comment);

    @NonNull
    @Override
    protected FrameHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_comment_container,
                parent,
                false
        );
        // 设置是否显示回复按钮
        setCanReply(canReply);

        SubViewHolder subViewHolder = onCreateContentViewHolder(inflater, parent);
        FrameHolder frameHolder;
        if (subViewHolder != null) {
            frameHolder = new FrameHolder(mBinding.getRoot(), subViewHolder);
        } else {
            frameHolder = new FrameHolder(mBinding.getRoot());
        }
        frameHolder.setBinding(mBinding);
        return frameHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FrameHolder holder, @NonNull final Comment comment) {
        holder.mBinding.setComment(comment);

        if (canReply) {
            setReplyBtnListener(holder, comment);
        }

        final Context context = holder.mBinding.getRoot().getContext();
        // 设置微博头像
        Glide.with(context)
                .load(comment.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.mBinding.itemCommentCardUserAvatar);
        holder.mBinding.itemCommentCardUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.actonStart(holder.mBinding.itemCommentCardUserAvatar.getContext(), comment.getUser());
            }
        });
        // 设置评论
        holder.mBinding.itemCommentCardText.setText(
                TextUtil.convertText(
                        context,
                        comment.getText(),
                        ContextCompat.getColor(context, R.color.colorAccent),
                        (int) holder.mBinding.itemCommentCardText.getTextSize()
                )
        );
        holder.mBinding.itemCommentCardText.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mBinding.itemCommentCardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(
                        context,
                        holder.mBinding.getComment().getStatus()
                );
            }
        });

        onBindContentViewHolder((SubViewHolder) holder.subViewHolder, comment);
    }

    /**
     * 设置回复按钮监听
     */
    private void setReplyBtnListener(final FrameHolder holder, final Comment comment) {
        holder.mBinding.itemCommentCardReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(
                        holder.mBinding.itemCommentCardReplyBtn.getContext(),
                        comment.getStatus(),
                        EditActivity.TYPE_CONTENT_REPLY,
                        comment
                );
            }
        });
    }

    /**
     * 返回是否显示回复按钮
     *
     * @return canReply
     */
    public boolean isCanReply() {
        return canReply;
    }

    /**
     * 设置是否需要显示回复按钮
     *
     * @param canReply 是否需要显示回复按钮
     */
    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
        if (canReply) {
            mBinding.itemCommentCardReplyBtn.setVisibility(View.VISIBLE);
        } else {
            mBinding.itemCommentCardReplyBtn.setVisibility(View.GONE);
        }
    }

    static class FrameHolder extends RecyclerView.ViewHolder {

        private ItemCommentContainerBinding mBinding;

        private RecyclerView.ViewHolder subViewHolder;

        FrameHolder(View itemView) {
            super(itemView);
        }

        FrameHolder(View itemView, RecyclerView.ViewHolder subViewHolder) {
            super(itemView);
            if (subViewHolder != null && subViewHolder.itemView != null) {
                ((FrameLayout) itemView.findViewById(R.id.item_comment_card_container))
                        .addView(subViewHolder.itemView);
            }
            this.subViewHolder = subViewHolder;
        }

        public void setBinding(ItemCommentContainerBinding binding) {
            mBinding = binding;
        }
    }
}