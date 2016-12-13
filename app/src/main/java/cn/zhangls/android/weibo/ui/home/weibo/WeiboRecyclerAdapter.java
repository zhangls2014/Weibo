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

package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseRecyclerAdapter;
import cn.zhangls.android.weibo.databinding.ItemFgHomeRtHavePicBinding;
import cn.zhangls.android.weibo.databinding.ItemFgHomeRtNoPicBinding;
import cn.zhangls.android.weibo.databinding.ItemFgHomeStatusHavePicBinding;
import cn.zhangls.android.weibo.databinding.ItemFgHomeStatusNoPicBinding;
import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.utils.TextUtil;

/**
 * Created by zhangls on 2016/10/20.
 *
 */

class WeiboRecyclerAdapter extends BaseRecyclerAdapter<Status> implements View.OnClickListener {

    private static final String TAG = "WeiboRecyclerAdapter";

    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    /**
     * ItemViewType 微博不包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_NO_PIC = 0;
    /**
     * ItemViewType 微博包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_HAVE_PIC = 1;
    /**
     * ItemViewType 被转发微博不包含图片
     */
    private static final int ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC = 2;
    /**
     * ItemViewType 被转发微博包含图片
     */
    private static final int ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC = 3;

    WeiboRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_STATUS_NO_PIC:
                return createStatusNoPicHolder(parent);
            case ITEM_VIEW_TYPE_STATUS_HAVE_PIC:
                return createStatusHavePicHolder(parent);
            case ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC:
                return createRtNoPicHolder(parent);
            case ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC:
                return createRtHavePicHolder(parent);
            default:
                return createStatusNoPicHolder(parent);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent 父容器
     * @return StatusNoPicHolder
     */
    private StatusNoPicHolder createStatusNoPicHolder(ViewGroup parent) {
        ItemFgHomeStatusNoPicBinding statusNoPicBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_fg_home_status_no_pic,
                parent,
                false
        );
        StatusNoPicHolder statusNoPicHolder = new StatusNoPicHolder(statusNoPicBinding.getRoot());
        statusNoPicHolder.setBinding(statusNoPicBinding);
        statusNoPicHolder.binding.llWeiboContentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class));
            }
        });
        return statusNoPicHolder;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent 父容器
     * @return StatusHavePicHolder
     */
    private StatusHavePicHolder createStatusHavePicHolder(ViewGroup parent) {
        ItemFgHomeStatusHavePicBinding statusHavePicBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_fg_home_status_have_pic,
                parent,
                false
        );
        StatusHavePicHolder statusHavePicHolder = new StatusHavePicHolder(statusHavePicBinding.getRoot());
        statusHavePicHolder.setBinding(statusHavePicBinding);
        statusHavePicHolder.binding.llWeiboContentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class));
            }
        });
        return statusHavePicHolder;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent 父容器
     * @return RtNoPicHolder
     */
    private RtNoPicHolder createRtNoPicHolder(ViewGroup parent) {
        ItemFgHomeRtNoPicBinding rtNoPicBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_fg_home_rt_no_pic,
                parent,
                false
        );
        RtNoPicHolder rtNoPicHolder = new RtNoPicHolder(rtNoPicBinding.getRoot());
        rtNoPicHolder.setBinding(rtNoPicBinding);
        rtNoPicHolder.binding.llWeiboContentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class));
            }
        });
        return rtNoPicHolder;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent 父容器
     * @return RtHavePicHolder
     */
    private RtHavePicHolder createRtHavePicHolder(ViewGroup parent) {
        ItemFgHomeRtHavePicBinding rtHavePicBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_fg_home_rt_have_pic,
                parent,
                false
        );
        RtHavePicHolder rtHavePicHolder = new RtHavePicHolder(rtHavePicBinding.getRoot());
        rtHavePicHolder.setBinding(rtHavePicBinding);
        rtHavePicHolder.binding.llWeiboContentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class));
            }
        });
        return rtHavePicHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //为Status注入上下文对象
        Status status = mDataList.get(position);
        if (holder instanceof StatusNoPicHolder) {
            showStatusNoPic((StatusNoPicHolder) holder, status);
        } else if (holder instanceof StatusHavePicHolder) {
            showStatusHavePic((StatusHavePicHolder) holder, status);
        } else if (holder instanceof RtNoPicHolder) {
            showRtNoPic((RtNoPicHolder) holder, status);
        } else if (holder instanceof RtHavePicHolder) {
            showRtsHavePic((RtHavePicHolder) holder, status);
        } else {
            Log.e(TAG, "onBindViewHolder: ===类型出错===");
        }
    }

    /**
     * 显示微博，无图片，无被转发微博
     *
     * @param holder StatusNoPicHolder
     * @param status Status
     */
    private void showStatusNoPic(StatusNoPicHolder holder, Status status) {
        holder.binding.setStatus(status);
        holder.binding.setUser(status.getUser());
        //设置微博头像
        Glide.with(mContext)
                .load(holder.binding.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.binding.fgHomeRecyclerItemAvatar);
        //设置微博正文getBinding()
        holder.binding.tvWeiboText.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.binding.tvWeiboText.getTextSize()));
    }

    /**
     * 显示微博，有图片，无被转发微博
     *
     * @param holder StatusHavePicHolder
     * @param status Status
     */
    private void showStatusHavePic(StatusHavePicHolder holder, Status status) {
        holder.binding.setStatus(status);
        holder.binding.setUser(status.getUser());
        //设置微博头像
        Glide.with(mContext)
                .load(holder.binding.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.binding.fgHomeRecyclerItemAvatar);
        //设置微博正文getBinding()
        holder.binding.tvWeiboText.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.binding.tvWeiboText.getTextSize()));
        // 设置图片 RecyclerView
        List<Status> statuses = new ArrayList<>();
        statuses.add(status);
        PictureRecyclerAdapter picAdapter = new PictureRecyclerAdapter(mContext, statuses);
        holder.binding.rvWeibo9Pic.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        holder.binding.rvWeibo9Pic.addItemDecoration(new SpaceItemDecoration(mContext));
        holder.binding.rvWeibo9Pic.setAdapter(picAdapter);
    }

    /**
     * 显示微博，有被转发微博（无图片）
     *
     * @param holder RtNoPicHolder
     * @param status Status
     */
    private void showRtNoPic(RtNoPicHolder holder, Status status) {
        holder.binding.setStatus(status);
        holder.binding.setUser(status.getUser());
        //设置微博头像
        Glide.with(mContext)
                .load(holder.binding.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.binding.fgHomeRecyclerItemAvatar);
        //设置微博正文getBinding()
        holder.binding.tvWeiboText.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.binding.tvWeiboText.getTextSize()));
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
        holder.binding.tvRetweetedText.setText(
                TextUtil.convertText(
                        mContext,
                        buffer.toString(),
                        (int) holder.binding.tvRetweetedText.getTextSize()
                )
        );
    }

    /**
     * 显示微博，有图片，有被转发微博（有图片）
     *
     * @param holder RtHavePicHolder
     * @param status Status
     */
    private void showRtsHavePic(RtHavePicHolder holder, Status status) {
        holder.binding.setStatus(status);
        holder.binding.setUser(status.getUser());
        //设置微博头像
        Glide.with(mContext)
                .load(holder.binding.getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.binding.fgHomeRecyclerItemAvatar);
        //设置微博正文getBinding()
        holder.binding.tvWeiboText.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.binding.tvWeiboText.getTextSize()));
        // 设置转发微博
        // 设置数据
        StringBuffer buffer = new StringBuffer();
        if (status.getRetweeted_status().getUser() != null) {// 如果不存在，则该被转发微博被删除
            buffer.append("@");
            buffer.append(status.getRetweeted_status().getUser().getName() != null ? status.getRetweeted_status().getUser().getName() :
                    status.getRetweeted_status().getUser().getScreen_name() != null ? status.getRetweeted_status().getUser().getScreen_name() : "")
                    .append(" :");
        }
        buffer.append(status.getRetweeted_status().getText());
        holder.binding.tvRetweetedText.setText(
                TextUtil.convertText(
                        mContext,
                        buffer.toString(),
                        (int) holder.binding.tvRetweetedText.getTextSize()
                )
        );
        // 设置图片 RecyclerView
        List<Status> statuses = new ArrayList<>();
        statuses.add(status.getRetweeted_status());
        PictureRecyclerAdapter picAdapter = new PictureRecyclerAdapter(mContext, statuses);
        holder.binding.rvWeibo9Pic.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        holder.binding.rvWeibo9Pic.addItemDecoration(new SpaceItemDecoration(mContext));
        holder.binding.rvWeibo9Pic.setAdapter(picAdapter);
    }

    @Override
    public void onClick(View v) {
        if (mRecyclerView != null && mOnItemClickListener != null) {
            int position = mRecyclerView.getChildAdapterPosition((View) v.getParent());
            mOnItemClickListener.OnItemClick(mRecyclerView, v, position);
        }
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * ItemViewType:
     *      0：微博不包含图片
     *      1：微博包含图片
     *      2：被转发微博不包含图片
     *      3：欸转发微博包含图片
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        Status status = mDataList.get(position);
        if (status.getRetweeted_status() != null
                && status.getRetweeted_status().getPic_urls() != null
                && !status.getRetweeted_status().getPic_urls().isEmpty()) {// 被转发微博存在图片
            return ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC;
        } else if (status.getRetweeted_status() != null
                && status.getRetweeted_status().getPic_urls() == null) {// 被转发微博不存在图片
            return ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC;
        } else if (status.getRetweeted_status() == null
                && status.getPic_urls() != null
                && !status.getPic_urls().isEmpty()) {// 微博包含图片
            return ITEM_VIEW_TYPE_STATUS_HAVE_PIC;
        } else {// 微博不包含图片
            return ITEM_VIEW_TYPE_STATUS_NO_PIC;
        }
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

    /**
     * 设置RecyclerView Item 点击事件监听
     *
     * @param mOnItemClickListener 监听接口
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * RecyclerView Item 点击事件接口
     */
    interface OnItemClickListener {
        void OnItemClick(RecyclerView recyclerView, View view, int position);
    }

    /**
     * ViewHolder
     * StatusNoPic
     */
    private static class StatusNoPicHolder extends RecyclerView.ViewHolder {
        private ItemFgHomeStatusNoPicBinding binding;

        StatusNoPicHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemFgHomeStatusNoPicBinding binding) {
            this.binding = binding;
        }
    }

    /**
     * ViewHolder
     * StatusHavePic
     */
    private static class StatusHavePicHolder extends RecyclerView.ViewHolder {
        private ItemFgHomeStatusHavePicBinding binding;

        StatusHavePicHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemFgHomeStatusHavePicBinding binding) {
            this.binding = binding;
        }
    }

    /**
     * ViewHolder
     * RetwetedStatusNoPic
     */
    private static class RtNoPicHolder extends RecyclerView.ViewHolder {
        private ItemFgHomeRtNoPicBinding binding;

        RtNoPicHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemFgHomeRtNoPicBinding binding) {
            this.binding = binding;
        }
    }

    /**
     * ViewHolder
     * RetwetedStatusHavePic
     */
    private static class RtHavePicHolder extends RecyclerView.ViewHolder {
        private ItemFgHomeRtHavePicBinding binding;

        RtHavePicHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemFgHomeRtHavePicBinding binding) {
            this.binding = binding;
        }
    }
}