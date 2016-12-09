package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseRecyclerAdapter;
import cn.zhangls.android.weibo.network.model.PicUrls;
import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.ui.details.BigImageActivity;

/**
 * Created by zhangls on 2016/11/8.
 * <p>
 * 图片显示RecyclerView 适配器
 */

class PictureRecyclerAdapter extends BaseRecyclerAdapter<Status> implements View.OnClickListener {
    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    public PictureRecyclerAdapter(Context context, List<Status> dataList) {
        super(context, dataList);
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_weibo_9_pic,
                parent,
                false
        );
        PicViewHolder holder = new PicViewHolder(view);
        holder.imgView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PicViewHolder) {
            // 将缩略图 url 转换成高清图 url
            String url = mDataList.get(0).getPic_urls().get(position).getThumbnail_pic().replace("thumbnail", "bmiddle");
            // 显示图片
            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.pic_bg)
                    .placeholder(R.drawable.pic_bg)
                    .into(((PicViewHolder) holder).imgView);
            // ImageView 点击事件
            ((PicViewHolder) holder).imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext, String.format(
//                            Locale.CHINA, "你点击了第 %d 张图片", holder.getAdapterPosition()),
//                            Toast.LENGTH_SHORT).show();
                    ArrayList<String> picUrls = new ArrayList<>();
                    for (PicUrls urls : mDataList.get(0).getPic_urls()) {
                        picUrls.add(urls.getThumbnail_pic());
                    }
                    Intent intent = new Intent(mContext, BigImageActivity.class);
                    intent.putStringArrayListExtra(BigImageActivity.PIC_URLS, picUrls);
                    intent.putExtra(BigImageActivity.CURRENT_PIC, position);
                    mContext.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.get(0).getPic_urls() != null ? mDataList.get(0).getPic_urls().size() : 0;
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
        mOnItemClickListener = null;
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
     * RecyclerView Item 点击事件接口
     */
    interface OnItemClickListener {
        void OnItemClick(RecyclerView recyclerView, View view, int position);
    }

    /**
     * ViewHolder
     */
    private static class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;

        PicViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.iv_weibo_item_pic);
        }
    }
}
