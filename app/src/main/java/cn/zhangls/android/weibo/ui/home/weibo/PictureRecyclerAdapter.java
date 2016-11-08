package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.Status;

/**
 * Created by zhangls on 2016/11/8.
 * <p>
 * 图片显示RecyclerView 适配器
 */

public class PictureRecyclerAdapter extends RecyclerView.Adapter<PictureRecyclerAdapter.PicViewHolder> implements View.OnClickListener {
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 数据源
     */
    private Status status;

    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    public PictureRecyclerAdapter(Context context, Status status) {
        this.context = context;
        this.status = status;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_weibo_9_pic,
                parent,
                false
        );
        PicViewHolder holder = new PicViewHolder(view);
        holder.imgView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
        //显示图片
        Glide.with(context).
                load(status.getPic_ids().get(position).getThumbnail_pic())
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return status.getPic_ids() != null ? status.getPic_ids().size() : 0;
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
     * RecyclerView Item 点击事件接口
     */
    interface OnItemClickListener {
        void OnItemClick(RecyclerView recyclerView, View view, int position);
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
     * ImageView 点击事件
     *
     * @param v imgView
     */
    @Override
    public void onClick(View v) {
        if (mRecyclerView != null && mOnItemClickListener != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            mOnItemClickListener.OnItemClick(mRecyclerView, v, position);
        }
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
