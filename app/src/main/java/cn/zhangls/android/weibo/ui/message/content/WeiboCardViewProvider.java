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

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.details.comment.CommentActivity;
import cn.zhangls.android.weibo.ui.home.weibo.WeiboFrameProvider;
import cn.zhangls.android.weibo.views.SummaryCard;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/20.
 * <p>
 * 转发微博提要
 */
public class WeiboCardViewProvider extends WeiboFrameProvider<WeiboCardViewProvider.WeiboCardHolder> {

    /**
     * 唯一的构造方法
     *
     * @param attitudesAPI   AttitudesAPI，用于调用点赞API
     * @param showControlBar 是否显示转发、评论、点赞栏
     */
    public WeiboCardViewProvider(AttitudesAPI attitudesAPI, boolean showControlBar) {
        super(attitudesAPI, showControlBar);
    }

    @Override
    protected WeiboCardHolder onCreateContentViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_repost_summary, parent, false);
        return new WeiboCardHolder(root);
    }

    @Override
    protected void onBindContentViewHolder(@NonNull WeiboCardHolder holder, @NonNull Status status) {
        setWeiboCard(status, holder);
    }

    /**
     * 设置微博内容提要
     */
    private void setWeiboCard(final Status status, final WeiboCardHolder holder) {
        if (status.getRetweeted_status() != null) {
            // 如果被转发微博已经被删除
            if (status.getRetweeted_status().getUser() == null) {
                holder.mSummaryCard.setContent(status.getRetweeted_status().getText());
                showPic("", (AppCompatImageView) holder.mSummaryCard.findViewById(R.id.item_summary_picture));
                return;
            }
            if (status.getRetweeted_status().getPic_urls() != null &&
                    !status.getRetweeted_status().getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(status.getRetweeted_status().getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) holder.mSummaryCard.findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = status.getRetweeted_status().getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) holder.mSummaryCard.findViewById(R.id.item_summary_picture));
            }
            holder.mSummaryCard.setTitle(status.getRetweeted_status().getUser().getScreen_name());
            holder.mSummaryCard.setContent(status.getRetweeted_status().getText());
            holder.mSummaryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentActivity.actionStart(holder.itemView.getContext(), status.getRetweeted_status());
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

    static class WeiboCardHolder extends RecyclerView.ViewHolder {

        private SummaryCard mSummaryCard;

        WeiboCardHolder(View itemView) {
            super(itemView);
            mSummaryCard = (SummaryCard) itemView.findViewById(R.id.item_repost_summary_summary_card);
        }
    }
}