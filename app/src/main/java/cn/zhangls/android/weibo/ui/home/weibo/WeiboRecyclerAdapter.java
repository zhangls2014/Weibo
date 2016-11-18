package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemFragmentHomeRecyclerBinding;
import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.network.model.StatusList;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.utils.TextUtil;

/**
 * Created by zhangls on 2016/10/20.
 *
 */

class WeiboRecyclerAdapter extends RecyclerView.Adapter<WeiboRecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 数据\
     */
    private StatusList publicData;
    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    WeiboRecyclerAdapter(Context mContext, StatusList publicData) {
        this.mContext = mContext;
        this.publicData = publicData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFragmentHomeRecyclerBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_fragment_home_recycler,
                parent,
                false);

        MyViewHolder myViewHolder = new MyViewHolder(binding.getRoot());
        myViewHolder.setBinding(binding);
        //设置链接
        myViewHolder.getBinding().tvWeiboText.setLinkTextColor(ContextCompat
                .getColor(mContext, R.color.text_color_blue));
        myViewHolder.getBinding().tvWeiboText.setMovementMethod(LinkMovementMethod.getInstance());

        myViewHolder.getBinding().llWeiboContentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class));
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //为Status注入上下文对象
        Status status = publicData.getStatuses().get(position);
        status.setContext(mContext);
        holder.getBinding().setStatus(status);
        holder.getBinding().setUser(status.getUser());

        //设置微博头像
        Glide.with(mContext)
                .load(holder.getBinding().getUser().getProfile_image_url())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .error(R.drawable.avator_default)
                .placeholder(R.drawable.avator_default)
                .into(holder.getBinding().fgHomeRecyclerItemAvatar);

        //设置微博正文
        holder.getBinding().tvWeiboText.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.getBinding().tvWeiboText.getTextSize()));
        /**
         * 根据具体的微博内容添加item
         * 1.文字（可添加照片、视频）
         * 2.转发类型
         * 3.头条文章
         * 4.分享类
         */
        showPic(holder, status);
    }

    /**
     * 显示图片
     *
     * @param holder MyViewHolder
     * @param status Status
     */
    private void showPic(MyViewHolder holder, Status status) {
        if (!status.getPic_urls().isEmpty()) {
            // 设置 RecyclerView
            holder.getBinding().rvWeibo9Pic.setVisibility(View.VISIBLE);
            PictureRecyclerAdapter picAdapter = new PictureRecyclerAdapter(mContext, status);
            holder.getBinding().rvWeibo9Pic.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
            holder.getBinding().rvWeibo9Pic.addItemDecoration(new SpaceItemDecoration(mContext));
            holder.getBinding().rvWeibo9Pic.setAdapter(picAdapter);
        } else {
            holder.getBinding().rvWeibo9Pic.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return publicData.getStatuses() != null ? publicData.getStatuses().size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mRecyclerView != null && mOnItemClickListener != null) {
            int position = mRecyclerView.getChildAdapterPosition((View) v.getParent());
            mOnItemClickListener.OnItemClick(mRecyclerView, v, position);
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
     * 刷新所有数据
     */
    void changeData(StatusList publicData) {
        if (publicData.getStatuses().size() > 0) {
            this.publicData = publicData;
            notifyDataSetChanged();
        }
    }

    /**
     * RecyclerView Item 点击事件接口
     */
    interface OnItemClickListener {
        void OnItemClick(RecyclerView recyclerView, View view, int position);
    }

    /**
     * ViewHolder
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemFragmentHomeRecyclerBinding binding;

        MyViewHolder(View itemView) {
            super(itemView);
        }

        ItemFragmentHomeRecyclerBinding getBinding() {
            return binding;
        }

        void setBinding(ItemFragmentHomeRecyclerBinding binding) {
            this.binding = binding;
        }
    }
}
