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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;
import cn.zhangls.android.weibo.common.OnLoadMoreListener;
import cn.zhangls.android.weibo.network.models.Favorite;
import cn.zhangls.android.weibo.network.models.FavoriteList;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.ui.weibo.content.ItemEmpty;
import cn.zhangls.android.weibo.ui.weibo.content.ItemEmptyViewBinder;
import cn.zhangls.android.weibo.ui.weibo.content.ItemError;
import cn.zhangls.android.weibo.ui.weibo.content.ItemErrorViewBinder;
import cn.zhangls.android.weibo.ui.weibo.content.ItemLoadMore;
import cn.zhangls.android.weibo.ui.weibo.content.ItemLoadMoreViewBinder;
import cn.zhangls.android.weibo.ui.weibo.content.ItemProgress;
import cn.zhangls.android.weibo.ui.weibo.content.ItemProgressViewBinder;
import cn.zhangls.android.weibo.ui.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.Repost;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPicture;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.RepostViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleTextViewProvider;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static cn.zhangls.android.weibo.ui.weibo.WeiboFragment.RecyclerViewItemType.*;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/27.
 * <p>
 * WeiboFragment 用于显示微博列表，由于 WeiboListType#USER 类型需要
 */
public class WeiboFragment extends BaseFragment implements WeiboContract.WeiboView {

    /**
     * RecyclerView Item Type
     */
    enum RecyclerViewItemType {
        ITEM_VIEW_TYPE_STATUS_NO_PIC, // 微博不包含图片
        ITEM_VIEW_TYPE_STATUS_HAVE_PIC, // 微博包含图片
        ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC, // 被转发微博不包含图片
        ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC, // 被转发微博包含图片
        ITEM_VIEW_TYPE_LOAD_MORE, // 加载更多
        ITEM_VIEW_TYPE_PROGRESS, // 加载进度条
        ITEM_VIEW_TYPE_EMPTY, // Item 为空
        ITEM_VIEW_TYPE_ERROR // 加载出错
    }

    /**
     * 每页微博数
     */
    private int WEIBO_COUNT = 20;
    /**
     * 微博页数
     */
    private int WEIBO_PAGE = 1;
    /**
     * 加载更多标识符
     */
    private boolean isLoadMore = false;
    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * 微博列表类型
     */
    private WeiboListType mWeiboListType;
    /**
     * WeiboRecyclerAdapter 适配器
     */
    private MultiTypeAdapter mMultiTypeAdapter;
    /**
     * 类型池
     */
    private Items mItems;
    /**
     * presenter 接口
     */
    private WeiboContract.Presenter mWeiboPresenter;
    /**
     * RecyclerView 的 LayoutManager
     */
    private LinearLayoutManager linearLayoutManager;

    private static final String WEIBO_LIST_TYPE = "weibo_list_type";
    /**
     * RecyclerView 加载更多功能是否可用
     */
    private boolean canLoadMore = true;

    public enum WeiboListType {
        PUBLIC,// 最新的公共微博
        FRIEND,// 当前登录用户及其所关注用户的最新微博
        USER,// 某个用户最新发表的微博列表
        HOME,// 当前登录用户及其所关注用户的最新微博
        REPOST,// 指定微博的转发微博列表
        REPOST_BY_ME,// 当前用户最新转发的微博列表
        MENTION,// 最新的提到登录用户的微博列表，即@我的微博
        BILATERAL,// 双向关注用户的最新微博
        FAVORITE,// 登录用户的微博收藏列表
        HOT_FAVORITE// 推荐的热门收藏
    }

    private OnWeiboRefreshListener mListener;

    public void setWeiboRefreshListener(OnWeiboRefreshListener listener) {
        mListener = listener;
    }

    public static WeiboFragment newInstance(WeiboListType weiboListType) {
        WeiboFragment weiboFragment = new WeiboFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WEIBO_LIST_TYPE, weiboListType);
        weiboFragment.setArguments(bundle);
        return weiboFragment;
    }

    public void setWeiboListType(WeiboListType weiboListType) {
        mWeiboListType = weiboListType;
        WEIBO_COUNT = 50;
        WEIBO_PAGE = 1;
        isLoadMore = false;
        canLoadMore = true;
        mOnLoadMoreListener.setCanLoadMore(canLoadMore);
        linearLayoutManager.smoothScrollToPosition(mRecyclerView, null, 0);
        startRequest();
    }

    private void startRequest() {
        mWeiboPresenter.requestTimeline(mWeiboListType, WEIBO_COUNT, WEIBO_PAGE);
        if (mListener != null) {
            mListener.startLoad();
        }
    }

    /**
     * 加载数据
     */
    @Override
    protected void loadData() {
        //初始化Presenter
        new WeiboPresenter(getContext().getApplicationContext(), this);
        mWeiboPresenter.start();

        mWeiboListType = (WeiboListType) getArguments().getSerializable(WEIBO_LIST_TYPE);

        //设置RecyclerView
        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 注册文字类型 ViewHolder
        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider());
        // 注册图片类型 ViewHolder
        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider());
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(Repost.class, new RepostViewProvider());
        // 注册转发图片类型 ViewHolder
        mMultiTypeAdapter.register(RepostPicture.class, new RepostPictureViewProvider());
        mMultiTypeAdapter.register(ItemEmpty.class, new ItemEmptyViewBinder());
        mMultiTypeAdapter.register(ItemError.class, new ItemErrorViewBinder());
        mMultiTypeAdapter.register(ItemLoadMore.class, new ItemLoadMoreViewBinder());
        mMultiTypeAdapter.register(ItemProgress.class, new ItemProgressViewBinder());

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        // 实现加载更多功能
        mRecyclerView.addOnScrollListener(mOnLoadMoreListener);
        // 设置 Item 的类型
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object o) {
                Class m;
                if (o instanceof ItemEmpty) {
                    m = ItemEmpty.class;
                } else if (o instanceof ItemProgress) {
                    m = ItemProgress.class;
                } else if (o instanceof ItemError) {
                    m = ItemError.class;
                } else if (o instanceof ItemLoadMore) {
                    m = ItemLoadMore.class;
                } else if (o instanceof Status) {
                    switch (getItemViewType((Status) o)) {
                        case ITEM_VIEW_TYPE_STATUS_NO_PIC:
                            m = SimpleText.class;
                            break;
                        case ITEM_VIEW_TYPE_STATUS_HAVE_PIC:
                            m = Picture.class;
                            break;
                        case ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC:
                            m = Repost.class;
                            break;
                        case ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC:
                            m = RepostPicture.class;
                            break;
                        default:
                            m = SimpleText.class;
                            break;
                    }
                } else {
                    m = ItemError.class;
                }
                return m;
            }

            @NonNull
            @Override
            public Object onFlattenItem(@NonNull Object o) {
                return o;
            }
        });

        // 第一次加载页面时，显示加载进度条
//        loadProgress();
        // 第一次加载页面时，刷新数据
        startRequest();
    }

    OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener(canLoadMore) {
        @Override
        public void onLoadMore() {
            WEIBO_PAGE++;
            isLoadMore = true;
            startRequest();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weibo, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fg_home_recycler);
        return view;
    }

    /**
     * 完成数据加载
     *
     * @param statusList 返回数据
     */
    @Override
    public void loadStatuses(StatusList statusList) {
        if (statusList != null && statusList.getStatuses() != null && statusList.getStatuses().size() > 0) {
            insertItem(statusList.getStatuses(), statusList.getStatuses().size());
        }
        if (mListener != null) {
            mListener.stopLoad();
        }
    }

    /**
     * 加载收藏数据
     *
     * @param favoriteList 收藏数据
     */
    @Override
    public void loadFavorites(FavoriteList favoriteList) {
        if (favoriteList != null) {
            ArrayList<Status> statuses = new ArrayList<>();
            for (Favorite favorite : favoriteList.getFavorites()) {
                statuses.add(favorite.getStatus());
            }
            insertItem(statuses, statuses.size());
        }
        if (mListener != null) {
            mListener.stopLoad();
        }
    }

    /**
     * 加载热门收藏数据
     *
     * @param hotFavorites 热门收藏数据
     */
    @Override
    public void loadHotFavorites(ArrayList<Status> hotFavorites) {
        if (hotFavorites != null && hotFavorites.size() > 0) {
            insertItem(hotFavorites, hotFavorites.size());
        }
        if (mListener != null) {
            mListener.stopLoad();
        }
    }

    /**
     * RecyclerView 数据更新方法
     *
     * @param object 数据
     * @param count  数据长度
     */
    private void insertItem(Object object, int count) {
        int itemCount;
        if (!isLoadMore) {
            mItems.clear();
            mItems.addAll((Collection<?>) object);
            if (!(object instanceof ItemError)) {
                addEndItem();
            }
            mMultiTypeAdapter.notifyDataSetChanged();
        } else {
            // 移除最后一个特殊的 Item
            if (mItems.size() > 0) {
                mItems.remove(mItems.size() - 1);
            }
            itemCount = mItems.size();
            mItems.addAll((Collection<?>) object);
            addEndItem();
            mMultiTypeAdapter.notifyItemRangeInserted(itemCount, count);
        }
    }

    /**
     * 为 RecyclerView 添加最后一个特殊的 Item
     */
    private void addEndItem() {
        if (canLoadMore) {
            mItems.add(new ItemLoadMore());
        } else {
            mItems.add(new ItemEmpty());
        }
    }

    /**
     * 加载出错，显示 Error 页面
     */
    @Override
    public void loadError() {
        isLoadMore = false;
        insertItem(new ArrayList<ItemError>(), 1);
        if (mListener != null) {
            mListener.stopLoad();
        }
    }

    /**
     * 加载进度条
     */
    private void loadProgress() {
        isLoadMore = false;
        insertItem(new ItemProgress(), 1);
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    public void setPresenter(WeiboContract.Presenter presenter) {
        mWeiboPresenter = presenter;
    }

    /**
     * 显示登录 Snackbar
     */
    @Override
    public void showLoginSnackbar() {
        showLoginSnackbar(mRecyclerView);
    }

    /**
     * 获取 Item View Type
     *
     * @param status 数据
     * @return View Type
     */
    private RecyclerViewItemType getItemViewType(Status status) {
        if (status.getRetweeted_status() != null) {
            if (status.getRetweeted_status().getPic_urls() != null
                    && !status.getRetweeted_status().getPic_urls().isEmpty()) {// 被转发微博存在图片
                return ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC;
            } else {// 被转发微博不存在图片
                return ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC;
            }
        } else {
            if (status.getPic_urls() != null && !status.getPic_urls().isEmpty()) {// 微博包含图片
                return ITEM_VIEW_TYPE_STATUS_HAVE_PIC;
            } else {// 微博不包含图片
                return ITEM_VIEW_TYPE_STATUS_NO_PIC;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWeiboRefreshListener) {
            mListener = (OnWeiboRefreshListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnWeiboRefreshListener {
        /**
         * 开始加载数据
         */
        void startLoad();

        /**
         * 停止加载数据
         */
        void stopLoad();
    }
}
