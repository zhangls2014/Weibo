package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.Arrays;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.FragmentHomeRecyclerItemBinding;
import cn.zhangls.android.weibo.network.model.HttpResult;
import cn.zhangls.android.weibo.network.model.Timeline;

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
    private HttpResult<Timeline> publicData;
    /**
     * RecyclerView Item 点击事件接口实例
     */
    private OnItemClickListener mOnItemClickListener = null;

    private RecyclerView mRecyclerView = null;

    private GenericDraweeHierarchy hierarchy;
    /**
     * 微博图片适配器
     */
    private PicAdapter picAdapter;
    /**
     * 网格布局
     */
    private GridLayoutManager gridLayout;
    /**
     * 为Timeline添加上下文对象
     */
    private Timeline timeline;

    WeiboRecyclerAdapter(Context mContext, HttpResult<Timeline> publicData) {
        this.mContext = mContext;
        this.publicData = publicData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentHomeRecyclerItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.fragment_home_recycler_item,
                parent,
                false);

        //配置SimpleDraweeView
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        hierarchy = builder
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .setFailureImage(R.mipmap.ic_launcher)
                .build();
        RoundingParams params = new RoundingParams();
        params.setCornersRadius(mContext.getResources().getDimension(R.dimen.weibo_avatar_radius));
        hierarchy.setRoundingParams(params);

        MyViewHolder myViewHolder = new MyViewHolder(binding.getRoot());
        myViewHolder.setBinding(binding);
        myViewHolder.cardView.setOnClickListener(this);
        myViewHolder.simpleDraweeView.setHierarchy(hierarchy);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        timeline = publicData.getStatuses().get(position);
        timeline.setContext(mContext);
        holder.getBinding().setTimeline(timeline);
        holder.getBinding().setUser(timeline.getUser());
        holder.simpleDraweeView.setImageURI(timeline.getUser().getAvatar_large());

        if (timeline.getPic_urls() != null && timeline.getPic_urls().size() > 0) {
            holder.picView.setVisibility(View.VISIBLE);
            picAdapter = new PicAdapter(mContext, timeline);
            gridLayout = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            holder.picView.setLayoutManager(gridLayout);
            holder.picView.setAdapter(picAdapter);
        } else {
            holder.picView.setVisibility(View.GONE);
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
     * ViewHolder
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private FragmentHomeRecyclerItemBinding binding;
        private CardView cardView;
        private SimpleDraweeView simpleDraweeView;
        private RecyclerView picView;

        MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.fg_home_recycler_item_card);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.fg_home_recycler_item_avatar);
            picView = (RecyclerView) itemView.findViewById(R.id.rv_fg_home_recycler_item_pic);
        }

        FragmentHomeRecyclerItemBinding getBinding() {
            return binding;
        }

        void setBinding(FragmentHomeRecyclerItemBinding binding) {
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
    void changeData(HttpResult<Timeline> publicData) {
        if (publicData.getStatuses().size() > 0) {
            this.publicData = publicData;
            notifyDataSetChanged();
        }
    }
}
