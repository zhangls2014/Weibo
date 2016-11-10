package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemFragmentHomeRecyclerBinding;
import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.network.model.StatusList;
import cn.zhangls.android.weibo.utils.TextUtil;
import de.hdodenhof.circleimageview.CircleImageView;

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
     * 数据源
     */
    private StatusList<Status> publicData;
    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    WeiboRecyclerAdapter(Context mContext, StatusList<Status> publicData) {
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
                .dontAnimate()
                .placeholder(R.drawable.avator_default)
                .into(holder.avatar);

        //设置微博正文
        holder.textView.setText(TextUtil.convertText(mContext, status.getText(),
                (int) holder.textView.getTextSize()));

        /**
         * 根据具体的微博内容添加item
         * 1.文字（可添加照片、视频）
         * 2.转发类型
         * 3.头条文章
         * 4.分享类
         */
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
     * ViewHolder
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemFragmentHomeRecyclerBinding binding;
        private LinearLayout contentList;
        private CircleImageView avatar;
        private TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            contentList = (LinearLayout) itemView.findViewById(R.id.ll_weibo_content_list);
            avatar = (CircleImageView) itemView.findViewById(R.id.fg_home_recycler_item_avatar);
            textView = (TextView) itemView.findViewById(R.id.tv_weibo_text);
        }

        ItemFragmentHomeRecyclerBinding getBinding() {
            return binding;
        }

        void setBinding(ItemFragmentHomeRecyclerBinding binding) {
            this.binding = binding;
        }
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
     * 刷新所有数据
     */
    void changeData(StatusList<Status> publicData) {
        if (publicData.getStatuses().size() > 0) {
            this.publicData = publicData;
            notifyDataSetChanged();
        }
    }
}
