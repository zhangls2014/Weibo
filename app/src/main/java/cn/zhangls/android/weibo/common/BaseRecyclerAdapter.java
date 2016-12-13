/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package cn.zhangls.android.weibo.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangls on 2016/12/6.
 * <p>
 * 对 RecyclerView.Adapter 进行封装
 */

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 上下文对象
     */
    protected final Context mContext;
    /**
     * 数据
     */
    protected List<T> mDataList = new ArrayList<>();

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
    }

    public BaseRecyclerAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    /**
     * 插入一条记录
     *
     * @param data     需要加入的数据结构
     * @param position 插入位置
     */
    public void insertItem(T data, int position) {
        if (mDataList != null && position <= mDataList.size()) {
            mDataList.add(position, data);
            notifyItemInserted(position);
        }
    }

    /**
     * 批量插入记录
     *
     * @param data          需要插入的数据结构
     * @param positionStart 插入的开始位置
     */
    public void rangeInsertItems(List<T> data, int positionStart) {
        if (mDataList != null && positionStart <= mDataList.size()) {
            mDataList.addAll(positionStart, data);
            notifyItemRangeInserted(positionStart, data.size());
        }
    }

    /**
     * 移除一条记录
     *
     * @param position 移除位置
     */
    public void removeItem(int position) {
        if (mDataList != null && position <= mDataList.size()) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 批量移除记录
     *
     * @param positionStart 移除位置
     * @param count         移除数量
     */
    public void rangeRemoveItems(int positionStart, int count) {
        if (mDataList != null && positionStart + count <= mDataList.size() && positionStart >= 0 && count > 0) {
            List<T> subList = mDataList.subList(positionStart, positionStart + count);
            mDataList.removeAll(subList);
            notifyItemRangeRemoved(positionStart, count);
        }
    }

    /**
     * 替换一条记录
     *
     * @param data     需要替换的数据结构
     * @param position 替换位置
     */
    public void changeItem(T data, int position) {
        if (mDataList != null && position <= mDataList.size()) {
            mDataList.set(position, data);
            notifyItemChanged(position);
        }
    }

    /**
     * 批量替换记录
     *
     * @param data          需要替换的数据结构
     * @param positionStart 开始替换的位置
     * @param count         替换的数量
     */
    public void rangeChangeItems(List<T> data, int positionStart, int count) {
        if (mDataList != null && positionStart + count <= mDataList.size() && positionStart >= 0 && count > 0) {
            for (int i = 0; i < count; i++) {
                mDataList.set(positionStart + i, data.get(count));
            }
            notifyItemRangeChanged(positionStart, count);
        }
    }

    /**
     * 添加初始记录（未在构造方法中传入记录时调用）
     *
     * @param dataList 需要添加的数据结构
     */
    public void setData(List<T> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
