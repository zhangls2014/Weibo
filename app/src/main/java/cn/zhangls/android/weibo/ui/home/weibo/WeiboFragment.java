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

package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.ui.home.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.home.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.Repost;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostPicture;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostPictureViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.home.weibo.content.SimpleTextViewProvider;
import cn.zhangls.android.weibo.ui.search.SearchActivity;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class WeiboFragment extends Fragment implements WeiboContract.WeiboView {

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

    /**
     * UI 是否可见的标识符
     */
    private boolean isVisible;
    /**
     * 是否加载过数据标识符
     */
    private boolean isLoaded = true;
    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * SwipeRefreshLayout
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * Toolbar
     */
    private Toolbar mToolbar;
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

    public WeiboFragment() {
        // Required empty public constructor
    }

    public static WeiboFragment newInstance() {
        return new WeiboFragment();
    }

    /**
     * 缓加载策略
     *
     * @param isVisibleToUser UI 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = getUserVisibleHint();
        // 视图可见且未加载过数据时，加载数据
        if (isVisible && !isLoaded) {
            loadData();
            isLoaded = true;
        }
    }

    /**
     * 加载数据
     */
    protected void loadData() {
        //初始化Presenter
        new WeiboPresenter(getContext(), this);
        mWeiboPresenter.start();

        AttitudesAPI attitudesAPI = new AttitudesAPI(getContext(),
                AccessTokenKeeper.readAccessToken(getContext()));

        //设置RecyclerView
        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 注册文字类型 ViewHolder
        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider(attitudesAPI));
        // 注册图片类型 ViewHolder
        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider(attitudesAPI));
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(Repost.class, new RepostViewProvider(attitudesAPI));
        // 注册转发图片类型 ViewHolder
        mMultiTypeAdapter.register(RepostPicture.class, new RepostPictureViewProvider(attitudesAPI));

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        // 设置 Item 的类型
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object o) {
                Class m;
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
                return m;
            }

            @NonNull
            @Override
            public Object onFlattenItem(@NonNull Object o) {
                return o;
            }
        });

        //设置SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWeiboPresenter.requestFriendsTimeline();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        // 第一次加载页面时，刷新数据
        mWeiboPresenter.requestGroupList();
        mWeiboPresenter.requestFriendsTimeline();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fg_home_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fg_home_recycler);
        mToolbar = (Toolbar) view.findViewById(R.id.fg_home_toolbar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // don't show toolbar title
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        // display options menu
        setHasOptionsMenu(true);
        // 视图可见时，加载数据
        if (isVisible) {
            loadData();
            isLoaded = true;
        } else {
            isLoaded = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_fg_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.ac_home_menu_search:
                // 打开搜索页面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_home_menu_add_friend:
                break;
            case R.id.ac_home_menu_radar:
                break;
            case R.id.ac_home_menu_scan:
                break;
            case R.id.ac_home_menu_taxi:
                break;
        }
        return true;
    }

    /**
     * 刷新微博
     */
    @Override
    public void onWeiboRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 完成数据加载
     *
     * @param statusList 返回数据
     */
    @Override
    public void refreshCompleted(StatusList statusList) {
        mItems.clear();
        mItems.addAll(statusList.getStatuses());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    /**
     * 停止刷新动画，回滚到第一个 Item
     */
    @Override
    public void stopRefresh() {
        linearLayoutManager.scrollToPosition(0);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(WeiboContract.Presenter presenter) {
        mWeiboPresenter = presenter;
    }

    /**
     * 获取 Item View Type
     *
     * @param status 数据
     * @return View Type
     */
    private int getItemViewType(Status status) {
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
}
