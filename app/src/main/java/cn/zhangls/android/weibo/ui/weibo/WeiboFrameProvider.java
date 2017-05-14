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

package cn.zhangls.android.weibo.ui.weibo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.ItemFgHomeWeiboContainerBinding;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.api.FavoritesAPI;
import cn.zhangls.android.weibo.network.models.ErrorInfo;
import cn.zhangls.android.weibo.network.models.Favorite;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.details.comment.CommentActivity;
import cn.zhangls.android.weibo.ui.edit.EditActivity;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.utils.TextUtil;
import io.reactivex.Observer;
import me.drakeet.multitype.ItemViewProvider;

public abstract class WeiboFrameProvider<SubViewHolder extends RecyclerView.ViewHolder>
        extends ItemViewProvider<Status, WeiboFrameProvider.FrameHolder> {

    /**
     * ItemFgHomeWeiboContainerBinding
     */
    private ItemFgHomeWeiboContainerBinding mBinding;

    protected abstract SubViewHolder onCreateContentViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindContentViewHolder(
            @NonNull SubViewHolder holder, @NonNull Status status);

    @NonNull
    @Override
    protected FrameHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_fg_home_weibo_container,
                parent,
                false
        );

        SubViewHolder subViewHolder = onCreateContentViewHolder(inflater, parent);
        FrameHolder frameHolder;
        if (subViewHolder != null) {
            frameHolder = new FrameHolder(mBinding.getRoot(), subViewHolder);
        } else {
            frameHolder = new FrameHolder(mBinding.getRoot());
        }
        frameHolder.setBinding(mBinding);
        return frameHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FrameHolder holder, @NonNull final Status status) {
        holder.binding.setStatus(status);
        // 设置按钮监听
        setClickListeners(holder);
        //
        setupPopupBar(holder, status);

        Context context = holder.binding.getRoot().getContext();
        // 设置微博头像
        if (status.getUser() != null) {
            Glide.with(context)
                    .load(status.getUser().getProfile_image_url())
                    .centerCrop()
                    .crossFade()
                    .dontAnimate()
                    .error(R.drawable.avator_default)
                    .placeholder(R.drawable.avator_default)
                    .into(holder.binding.fgHomeRecyclerItemAvatar);
        }
        holder.binding.fgHomeRecyclerItemAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.actonStart(holder.binding.fgHomeRecyclerItemAvatar.getContext(), status.getUser());
            }
        });
        // 设置微博正文 getBinding()
        holder.binding.tvWeiboText.setText(
                TextUtil.convertText(
                        context,
                        status.getText(),
                        ContextCompat.getColor(context, R.color.colorAccent),
                        (int) holder.binding.tvWeiboText.getTextSize()
                )
        );
        holder.binding.tvWeiboText.setMovementMethod(LinkMovementMethod.getInstance());
        holder.binding.tvWeiboText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(
                        holder.binding.comment.getContext(),
                        holder.binding.llWeiboContentList,
                        holder.binding.getStatus()
                );
            }
        });
        // 转发微博已被删除，则转发按钮不可用
        if (status.getRetweeted_status() != null && status.getRetweeted_status().getUser() == null) {
            holder.binding.repost.setEnabled(false);
        } else {
            holder.binding.repost.setEnabled(true);
        }

        onBindContentViewHolder((SubViewHolder) holder.subViewHolder, status);
    }

    /**
     * 设置事件监听
     *
     * @param holder ViewHolder
     */
    private void setClickListeners(final FrameHolder holder) {
        holder.binding.repost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(
                        holder.binding.getRoot().getContext(),
                        holder.binding.getStatus(),
                        EditActivity.TYPE_CONTENT_REPOST,
                        null
                );
            }
        });
        holder.binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(
                        holder.binding.comment.getContext(),
                        holder.binding.getStatus(),
                        EditActivity.TYPE_CONTENT_COMMENT,
                        null
                );
            }
        });
        holder.binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(holder.binding.getStatus().getId());
            }
        });
    }

    /**
     * 点赞
     *
     * @param id 微博 ID
     */
    private void like(long id) {
        Observer<ErrorInfo> observer = new BaseObserver<ErrorInfo>(mBinding.getRoot().getContext()) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        };
        AttitudesAPI attitudesAPI = new AttitudesAPI(mBinding.getRoot().getContext().getApplicationContext(),
                AccessTokenKeeper.readAccessToken(mBinding.getRoot().getContext().getApplicationContext()));
        attitudesAPI.create(observer, id);
    }

    /**
     * 创建微博右上角的弹出菜单
     *
     * @param holder
     * @param status
     */
    private void setupPopupBar(final FrameHolder holder, final Status status) {
        LayoutInflater layoutInflater = LayoutInflater.from(holder.binding.fgHomeWeiboPopupBar
                .getContext().getApplicationContext());
        // 弹出的视图
        final View popupView;
        if (status.isFavorited() && status.getUser().isFollowing()) {// 已收藏、已关注
            popupView = layoutInflater.inflate(R.layout.popup_window_weibo_follow_save_post,
                    holder.binding.flWeiboContainer, false);
        } else if (!status.isFavorited() && status.getUser().isFollowing()) {// 未收藏、已关注
            popupView = layoutInflater.inflate(R.layout.popup_window_weibo_follow_unsave_post,
                    holder.binding.flWeiboContainer, false);
        } else if (status.isFavorited() && !status.getUser().isFollowing()) {// 已收藏、未关注
            popupView = layoutInflater.inflate(R.layout.popup_window_weibo_unfollow_save_post,
                    holder.binding.flWeiboContainer, false);
        } else {// 未收藏、未关注
            popupView = layoutInflater.inflate(R.layout.popup_window_weibo_unfollow_unsave_post,
                    holder.binding.flWeiboContainer, false);
        }
        // 创建 PopupWindow
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // popupWindow 关闭时，显示按钮旋转动画
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Animation animation = AnimationUtils.loadAnimation(
                        holder.binding.fgHomeWeiboPopupBar.getContext().getApplicationContext(),
                        R.anim.rotate_180_end);
                animation.setDuration(300);
                animation.setFillAfter(true);
                holder.binding.fgHomeWeiboPopupBar.startAnimation(animation);
            }
        });
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimStyle);
        // popupWindow 的 Item 点击事件监听
        if (status.isFavorited()) {// 已经收藏、则实现取消收藏按钮的点击事件监听
            popupWindow.getContentView().findViewById(R.id.menu_item_weibo_more_save_post)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FavoritesAPI favoritesAPI = new FavoritesAPI(mBinding.getRoot().getContext().getApplicationContext(),
                                    AccessTokenKeeper.readAccessToken(mBinding.getRoot().getContext().getApplicationContext()));
                            BaseObserver<Favorite> observer = new BaseObserver<Favorite>(
                                    mBinding.getRoot().getContext().getApplicationContext()) {
                                @Override
                                public void onNext(Favorite value) {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                }

                                @Override
                                public void onComplete() {

                                }
                            };

                            favoritesAPI.destroy(observer, status.getId());

                            popupWindow.dismiss();
                        }
                    });
        } else {
            popupWindow.getContentView().findViewById(R.id.menu_item_weibo_more_unsave_post)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FavoritesAPI favoritesAPI = new FavoritesAPI(mBinding.getRoot().getContext().getApplicationContext(),
                                    AccessTokenKeeper.readAccessToken(mBinding.getRoot().getContext().getApplicationContext()));
                            BaseObserver<Favorite> observer = new BaseObserver<Favorite>(
                                    mBinding.getRoot().getContext().getApplicationContext()) {
                                @Override
                                public void onNext(Favorite value) {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                }

                                @Override
                                public void onComplete() {

                                }
                            };

                            favoritesAPI.create(observer, status.getId());

                            popupWindow.dismiss();
                        }
                    });
        }
        if (status.getUser().isFollowing()) {// 已经关注、则实现取消关注按钮的点击事件监听
            popupWindow.getContentView().findViewById(R.id.menu_item_weibo_more_follow)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
        } else {
            popupWindow.getContentView().findViewById(R.id.menu_item_weibo_more_unfollow)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
        }

        // 弹出菜单、显示旋转动画
        holder.binding.fgHomeWeiboPopupBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && !popupWindow.isShowing()) {
                    Animation animation = AnimationUtils.loadAnimation(
                            holder.binding.fgHomeWeiboPopupBar.getContext().getApplicationContext(),
                            R.anim.rotate_180_start);
                    animation.setDuration(300);
                    animation.setFillAfter(true);
                    holder.binding.fgHomeWeiboPopupBar.startAnimation(animation);
                    popupWindow.showAsDropDown(holder.binding.fgHomeWeiboPopupBar);
                }
            }
        });
    }

    /**
     * ViewHolder
     */
    static class FrameHolder extends RecyclerView.ViewHolder {

        ItemFgHomeWeiboContainerBinding binding;
        RecyclerView.ViewHolder subViewHolder;

        FrameHolder(View itemView) {
            super(itemView);
        }

        FrameHolder(View itemView, RecyclerView.ViewHolder subViewHolder) {
            super(itemView);
            if (subViewHolder != null && subViewHolder.itemView != null) {
                ((FrameLayout) itemView.findViewById(R.id.fl_weibo_container))
                        .addView(subViewHolder.itemView);
            }
            this.subViewHolder = subViewHolder;
        }

        void setBinding(ItemFgHomeWeiboContainerBinding binding) {
            this.binding = binding;
        }
    }
}