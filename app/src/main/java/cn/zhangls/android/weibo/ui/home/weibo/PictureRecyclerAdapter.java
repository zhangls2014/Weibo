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

package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseRecyclerAdapter;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls on 2016/11/8.
 * <p>
 * 图片显示RecyclerView 适配器
 */

public class PictureRecyclerAdapter extends BaseRecyclerAdapter<Status, PictureRecyclerAdapter.PicViewHolder> {

    public PictureRecyclerAdapter(Context context, ArrayList<Status> dataList) {
        super(context, dataList);
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_weibo_9_pic,
                parent,
                false
        );
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PicViewHolder holder, int position) {
        if (holder instanceof PicViewHolder) {
            // 将缩略图 url 转换成高清图 url
            String url = mDataList.get(0).getPic_urls().get(position).getThumbnail_pic().replace("thumbnail", "bmiddle");
            // 显示图片
            showPic(url, holder.imgView);
            // ImageView 点击事件
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showShortToast(holder.itemView.getContext(),
                            String.format(Locale.CHINA, "你点击了第%d张图片", holder.getAdapterPosition() + 1));
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
    private void showPic(String picUrl, ImageView imageView) {
        Glide.with(mContext)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.pic_bg)
                .placeholder(R.drawable.pic_bg)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mDataList.get(0).getPic_urls() != null ? mDataList.get(0).getPic_urls().size() : 0;
    }

    /**
     * ViewHolder
     */
    static class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;

        PicViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.iv_weibo_item_pic);
        }
    }
}