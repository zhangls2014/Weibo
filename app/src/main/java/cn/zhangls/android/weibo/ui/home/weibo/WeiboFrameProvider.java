/*
 * MIT License
 *
 * Copyright (c) 2016 zhangls2014
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
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemFgHomeWeiboContainerBinding;
import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.utils.TextUtil;
import me.drakeet.multitype.ItemViewProvider;

public abstract class WeiboFrameProvider<SubViewHolder extends RecyclerView.ViewHolder>
        extends ItemViewProvider<Status, WeiboFrameProvider.FrameHolder> {

    private static final String TAG = "WeiboFrameProvider";

    protected abstract SubViewHolder onCreateContentViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindContentViewHolder(
            @NonNull SubViewHolder holder, @NonNull Status status);

    @NonNull
    @Override
    protected FrameHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemFgHomeWeiboContainerBinding containerBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_fg_home_weibo_container,
                parent,
                false
        );
        SubViewHolder subViewHolder = onCreateContentViewHolder(inflater, parent);
        FrameHolder frameHolder;
        if (subViewHolder != null) {
            frameHolder = new FrameHolder(containerBinding.getRoot(), subViewHolder);
        } else {
            frameHolder = new FrameHolder(containerBinding.getRoot());
        }
        frameHolder.setBinding(containerBinding);
        return frameHolder;
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onBindViewHolder(@NonNull FrameHolder holder, @NonNull Status status) {
        holder.binding.setStatus(status);
        holder.binding.setUser(status.getUser());
        final Context context = holder.binding.getRoot().getContext();
        // 设置微博头像
        Glide.with(context)
                .load(holder.binding.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.binding.fgHomeRecyclerItemAvatar);
        // 设置微博正文 getBinding()
        holder.binding.tvWeiboText.setText(
                TextUtil.convertText(
                        context,
                        status.getText(),
                        ContextCompat.getColor(context, R.color.material_blue_800),
                        (int) holder.binding.tvWeiboText.getTextSize()
                )
        );
        holder.binding.tvWeiboText.setMovementMethod(LinkMovementMethod.getInstance());

        onBindContentViewHolder((SubViewHolder) holder.subViewHolder, status);
    }

    /**
     * ViewHolder
     */
    static class FrameHolder extends RecyclerView.ViewHolder {

        private ItemFgHomeWeiboContainerBinding binding;

        private RecyclerView.ViewHolder subViewHolder;

        FrameHolder(View itemView) {
            super(itemView);
        }

        FrameHolder(View itemView, RecyclerView.ViewHolder subViewHolder) {
            super(itemView);
            if (subViewHolder != null && subViewHolder.itemView != null) {
                ((FrameLayout) itemView.findViewById(R.id.fl_weibo_container))
                        .addView(subViewHolder.itemView);
            }
            this.subViewHolder = subViewHolder;
        }

        void setBinding(ItemFgHomeWeiboContainerBinding binding) {
            this.binding = binding;
        }
    }
}