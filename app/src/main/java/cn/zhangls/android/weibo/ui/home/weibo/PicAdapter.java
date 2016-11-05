package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.Timeline;

/**
 * Created by zhangls on 2016/11/4.
 * <p>
 * 显示微博九张图片
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * GenericDraweeHierarchy
     */
    private GenericDraweeHierarchy hierarchy;
    /**
     * 图片Url
     */
    private Timeline picUrl;

    public PicAdapter(Context context, Timeline picUrl) {
        this.context = context;
        this.picUrl = picUrl;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_pic_item, parent, false);
        PicViewHolder holder = new PicViewHolder(view);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        hierarchy = builder
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .setFailureImage(R.mipmap.ic_launcher)
                .build();
//        holder.picView
        holder.picView.setHierarchy(hierarchy);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
        holder.picView.setImageURI(picUrl.getPic_urls().get(position).getThumbnail_pic());
    }

    @Override
    public int getItemCount() {
        return picUrl.getOriginal_pic() != null ? picUrl.getPic_urls().size() : 0;
    }

    static class PicViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView picView;

        PicViewHolder(View itemView) {
            super(itemView);
            picView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_fg_pic_item);
        }
    }
}