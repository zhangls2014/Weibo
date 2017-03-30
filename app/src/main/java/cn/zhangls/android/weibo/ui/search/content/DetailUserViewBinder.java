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

package cn.zhangls.android.weibo.ui.search.content;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/28.
 */

public class DetailUserViewBinder extends ItemViewBinder<DetailUser, DetailUserViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_search_detail_user, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DetailUser detailUser) {
        holder.setCircleAvatar(detailUser.getProfile_image_url());
        holder.setUserName(detailUser.getScreen_name());
        holder.setUserDescription(detailUser.getDescription());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCircleImageView;
        private AppCompatTextView mNameText;
        private AppCompatTextView mDescriptionText;

        ViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.civ_item_search_detail_user_avatar);
            mNameText = (AppCompatTextView) itemView.findViewById(R.id.actv_item_search_detail_user_name);
            mDescriptionText = (AppCompatTextView) itemView.findViewById(R.id.actv_item_search_detail_description);
        }

        private void setCircleAvatar(String avatarUrl) {
            Glide.with(itemView.getContext())
                    .load(avatarUrl)
                    .centerCrop()
                    .crossFade()
                    .dontAnimate()
                    .error(R.drawable.avator_default)
                    .placeholder(R.drawable.avator_default)
                    .into(mCircleImageView);
        }

        private void setUserName(String userName) {
            mNameText.setText(userName);
        }

        private void setUserDescription(String userDescription) {
            mDescriptionText.setText(userDescription);
        }
    }
}
