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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.home.weibo.PictureRecyclerAdapter;
import cn.zhangls.android.weibo.ui.home.weibo.SpaceItemDecoration;
import cn.zhangls.android.weibo.ui.home.weibo.WeiboFrameProvider;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/22.
 *
 * 图片显示
 */
public class PictureViewProvider extends WeiboFrameProvider<PictureViewProvider.PictureHolder> {


    /**
     * 唯一的构造方法
     *
     * @param attitudesAPI   AttitudesAPI，用于调用点赞API
     * @param showControlBar 是否显示转发、评论、点赞栏
     */
    public PictureViewProvider(AttitudesAPI attitudesAPI, boolean showControlBar) {
        super(attitudesAPI, showControlBar);
    }

    @Override
    protected PictureHolder onCreateContentViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View rootView = inflater.inflate(R.layout.item_picture, parent, false);
        return new PictureHolder(rootView);
    }

    @Override
    protected void onBindContentViewHolder(@NonNull PictureHolder holder, @NonNull Status status) {
        // 设置图片 RecyclerView
        ArrayList<Status> statuses = new ArrayList<>();
        statuses.add(status);
        final Context context = holder.itemView.getContext();
        PictureRecyclerAdapter picAdapter = new PictureRecyclerAdapter(context, statuses);
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        holder.mRecyclerView.addItemDecoration(new SpaceItemDecoration(context));
        holder.mRecyclerView.setAdapter(picAdapter);
    }

    static class PictureHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;

        PictureHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_weibo_9_pic);
        }
    }
}