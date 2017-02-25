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

package cn.zhangls.android.weibo.ui.message.content;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemCommentCardBinding;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.Status;
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
public class CommentCardViewProvider extends ItemViewProvider<Comment, CommentCardViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemCommentCardBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_comment_card,
                parent,
                false
        );
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final Comment comment) {
        holder.mBinding.setComment(comment);

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
                holder.mBinding.itemCommentCardUserAvatar.getContext().startActivity(new Intent(
                        holder.mBinding.itemCommentCardUserAvatar.getContext(), UserActivity.class
                ));
            }
        });
        // 设置评论
        holder.mBinding.itemCommentCardText.setText(
                TextUtil.convertText(
                        context,
                        comment.getText(),
                        ContextCompat.getColor(context, R.color.material_blue_700),
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

        setWeiboCard(comment.getStatus(), holder);

        holder.mBinding.itemCommentCardReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(
                        context,
                        comment.getStatus(),
                        EditActivity.TYPE_CONTENT_REPLY,
                        comment
                );
            }
        });
    }

    /**
     * 设置微博内容提要
     */
    private void setWeiboCard(final Status status, final ViewHolder holder) {
        if (status.getRetweeted_status() != null) {
            // 如果被转发微博已经被删除
            if (status.getRetweeted_status().getUser() == null) {
                holder.mBinding.itemCommentCardStatus.setContent(status.getRetweeted_status().getText());
                showPic("", (AppCompatImageView) holder.mBinding.itemCommentCardStatus.findViewById(R.id.item_summary_picture));
                return;
            }
            if (status.getRetweeted_status().getPic_urls() != null &&
                    !status.getRetweeted_status().getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(status.getRetweeted_status().getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) holder.mBinding.itemCommentCardStatus.findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = status.getRetweeted_status().getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) holder.mBinding.itemCommentCardStatus.findViewById(R.id.item_summary_picture));
            }
            holder.mBinding.itemCommentCardStatus.setTitle(status.getRetweeted_status().getUser().getScreen_name());
            holder.mBinding.itemCommentCardStatus.setContent(status.getRetweeted_status().getText());
            holder.mBinding.itemCommentCardStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentActivity.actionStart(holder.itemView.getContext(), status.getRetweeted_status());
                }
            });
        } else {
            // 将缩略图 url 转换成高清图 url
            String url = status.getUser().getProfile_image_url();
            showPic(url, (AppCompatImageView) holder.mBinding.itemCommentCardStatus.findViewById(R.id.item_summary_picture));
            holder.mBinding.itemCommentCardStatus.setTitle(status.getUser().getScreen_name());
            holder.mBinding.itemCommentCardStatus.setContent(status.getText());
            holder.mBinding.itemCommentCardStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentActivity.actionStart(holder.itemView.getContext(), status);
                }
            });
        }
    }

    /**
     * 显示图片
     *
     * @param picUrl    图片连接
     * @param imageView 显示的 ImageView
     */
    private void showPic(String picUrl, AppCompatImageView imageView) {
        Glide.with(imageView.getContext())
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.pic_bg)
                .placeholder(R.drawable.pic_bg)
                .into(imageView);
    }

    /**
     * 转换URL
     *
     * @param thumbnailUrl 缩略图　URL
     * @return 高清图 URL
     */
    private String replaceUrl(String thumbnailUrl) {
        return thumbnailUrl.replace("thumbnail", "bmiddle");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentCardBinding mBinding;

        ViewHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ItemCommentCardBinding binding) {
            mBinding = binding;
        }
    }
}