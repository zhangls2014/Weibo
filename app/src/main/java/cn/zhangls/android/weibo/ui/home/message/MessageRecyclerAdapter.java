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

package cn.zhangls.android.weibo.ui.home.message;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseRecyclerAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/6.
 */

class MessageRecyclerAdapter extends BaseRecyclerAdapter<MessageInfo, MessageRecyclerAdapter.MessageHolder> {

    private OnChildClickListener mOnChildClickListener;

    private RecyclerView mRecyclerView;

    MessageRecyclerAdapter(Context context, ArrayList<MessageInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_message_recycler, parent, false);
        return new MessageHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, int position) {
        if (position < 3) {
            holder.mBodyText.setVisibility(View.GONE);
        } else {
            holder.mBodyText.setText(mDataList.get(position).getBody());
        }
        holder.mTitleText.setText(mDataList.get(position).getTitle());
        //设置圆形图片
        if (position < 5) {
            Glide.with(mContext)
                    .load(Integer.parseInt(mDataList.get(position).getAvatarUrl()))
                    .centerCrop()
                    .placeholder(R.drawable.avator_default)
                    .dontAnimate()
                    .into(holder.mCircleImageView);
        } else {
            Glide.with(mContext)
                    .load(mDataList.get(position).getAvatarUrl())
                    .centerCrop()
                    .placeholder(R.drawable.avator_default)
                    .dontAnimate()
                    .into(holder.mCircleImageView);
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnChildClickListener.onChildClick(mRecyclerView, v, holder.getAdapterPosition());
            }
        });

    }

    class MessageHolder extends RecyclerView.ViewHolder {

        private View parent;
        private CircleImageView mCircleImageView;
        private AppCompatTextView mTitleText;
        private AppCompatTextView mBodyText;

        MessageHolder(View itemView) {
            super(itemView);
            parent = itemView;
            mCircleImageView = (CircleImageView) findViewById(R.id.item_message_recycler_avatar);
            mTitleText = (AppCompatTextView) findViewById(R.id.item_message_recycler_title);
            mBodyText = (AppCompatTextView) findViewById(R.id.item_message_recycler_body);
        }

        private View findViewById(int id) {
            return parent.findViewById(id);
        }
    }

    interface OnChildClickListener {
        void onChildClick(RecyclerView recyclerView, View view, int position);
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
