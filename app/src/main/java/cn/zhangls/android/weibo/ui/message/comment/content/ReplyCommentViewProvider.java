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

package cn.zhangls.android.weibo.ui.message.comment.content;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.details.comment.CommentActivity;
import cn.zhangls.android.weibo.ui.message.mention.CommentFrameProvider;
import cn.zhangls.android.weibo.utils.TextUtil;
import cn.zhangls.android.weibo.views.SummaryCard;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/25.
 * <p>
 * 回复评论
 */
public class ReplyCommentViewProvider extends CommentFrameProvider<ReplyCommentViewProvider.ReplyHolder> {

    /**
     * 唯一构造方法
     *
     * @param canReply 是否显示回复按钮
     */
    public ReplyCommentViewProvider(boolean canReply) {
        super(canReply);
    }

    @Override
    protected ReplyHolder onCreateContentViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_reply_comment, parent, false);
        return new ReplyHolder(root);
    }

    @Override
    protected void onBindContentViewHolder(@NonNull final ReplyHolder holder, @NonNull final Comment comment) {
        // 设置被恢复评论内容
        String text = "@"
                + comment.getReply_comment().getUser().getScreen_name()
                + ":"
                + comment.getReply_comment().getText();
        holder.mTextView.setText(
                TextUtil.convertText(
                        holder.mTextView.getContext(),
                        text,
                        ContextCompat.getColor(holder.mTextView.getContext(), R.color.material_blue_700),
                        (int) holder.mTextView.getTextSize())
        );
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(holder.itemView.getContext(), comment.getStatus());
            }
        });
        // 设置被评论微博内容
        setWeiboCard(comment.getStatus(), holder);
    }

    /**
     * 设置微博内容提要
     */
    private void setWeiboCard(final Status status, final ReplyHolder holder) {
        String url;
        if (status.getRetweeted_status() != null) {
            if (status.getRetweeted_status().getUser() == null) {// 如果被转发微博已经被删除
                url = "";
                holder.mSummaryCard.setContent(status.getRetweeted_status().getText());
            } else if (status.getRetweeted_status().getPic_urls() != null &&
                    status.getRetweeted_status().getPic_urls().size() > 0) {// 转发微博包含图片，显示第一张图片
                // 将缩略图 url 转换成高清图 url
                url = replaceUrl(status.getRetweeted_status().getPic_urls().get(0).getThumbnail_pic());
            } else {// 转发微博不包含图片，显示被转发微博作者头像
                url = status.getRetweeted_status().getUser().getAvatar_large();
            }
        } else {// 非转发微博
            if (status.getPic_urls() != null && status.getPic_urls().size() > 0) {
                // 将缩略图 url 转换成高清图 url
                url = replaceUrl(status.getPic_urls().get(0).getThumbnail_pic());
            } else {
                url = status.getUser().getAvatar_large();
            }
        }
        showPic(url, (AppCompatImageView) holder.mSummaryCard.findViewById(R.id.item_summary_picture));
        holder.mSummaryCard.setTitle(status.getUser().getScreen_name());
        holder.mSummaryCard.setContent(status.getText());
        holder.mSummaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(holder.itemView.getContext(), status);
            }
        });
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

    static class ReplyHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView mTextView;
        private SummaryCard mSummaryCard;

        ReplyHolder(View itemView) {
            super(itemView);
            mTextView = (AppCompatTextView) itemView.findViewById(R.id.item_reply_comment_text);
            mSummaryCard = (SummaryCard) itemView.findViewById(R.id.item_reply_comment_summary_card);
        }
    }
}