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

package cn.zhangls.android.weibo.ui.home.weibo.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.home.weibo.WeiboFrameProvider;
import cn.zhangls.android.weibo.utils.TextUtil;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/22.
 *
 */
public class RepostViewProvider extends WeiboFrameProvider<RepostViewProvider.RepostHolder> {

    public RepostViewProvider(AttitudesAPI attitudesAPI) {
        super(attitudesAPI);
    }

    @Override
    protected RepostHolder onCreateContentViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View rootView = inflater.inflate(R.layout.item_repost, parent, false);
        return new RepostHolder(rootView);
    }

    @Override
    protected void onBindContentViewHolder(@NonNull RepostHolder holder, @NonNull Status status) {
        // 设置转发微博
        // 设置数据
        StringBuffer buffer = new StringBuffer();
        if (status.getRetweeted_status().getUser() != null) {
            buffer.append("@");
            buffer.append(status.getRetweeted_status().getUser().getName() != null ? status.getRetweeted_status().getUser().getName() :
                    status.getRetweeted_status().getUser().getScreen_name() != null ? status.getRetweeted_status().getUser().getScreen_name() : "")
                    .append(" :");
        }
        buffer.append(status.getRetweeted_status().getText());

        final Context context = holder.itemView.getContext();
        holder.mTextView.setText(
                TextUtil.convertText(
                        context,
                        buffer.toString(),
                        ContextCompat.getColor(context, R.color.material_blue_700),
                        (int) holder.mTextView.getTextSize()
                )
        );
        holder.mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    static class RepostHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        RepostHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_retweeted_text);
        }
    }
}