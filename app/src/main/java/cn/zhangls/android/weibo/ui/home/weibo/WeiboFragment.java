package cn.zhangls.android.weibo.ui.home.weibo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.StatusList;


public class WeiboFragment extends Fragment implements WeiboContract.WeiboView {

    /**
     * UI 是否可见的标识符
     */
    private boolean isVisible;
    /**
     * 是否加载过数据标识符
     */
    private boolean isLoaded = true;
    //RecyclerView
    private RecyclerView mRecyclerView;
    //SwipeRefreshLayout
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * WeiboRecyclerAdapter 适配器
     */
    private WeiboRecyclerAdapter mWeiboRecyclerAdapter;
    /**
     * 数据源
     */
    private StatusList mPublicData;
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
        //设置RecyclerView
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mPublicData = new StatusList();
        mWeiboRecyclerAdapter = new WeiboRecyclerAdapter(getContext(), mPublicData);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mWeiboRecyclerAdapter);

        //设置SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWeiboPresenter.getTimeline();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        // 第一次加载页面时，刷新数据
        mWeiboPresenter.getTimeline();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fg_home_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fg_home_recycler);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 视图可见时，加载数据
        if (isVisible) {
            loadData();
            isLoaded = true;
        } else {
            isLoaded = false;
        }
    }

    /**
     * 刷新微博
     */
    @Override
    public void onWeiboRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 回到顶部
     */
    @Override
    public void backToTop() {
        linearLayoutManager.scrollToPosition(0);
    }

    /**
     * 完成数据加载
     *
     * @param publicTimelineStatusList 数据源
     */
    @Override
    public void refreshCompleted(StatusList publicTimelineStatusList) {
        mPublicData = publicTimelineStatusList;
        mWeiboRecyclerAdapter.changeData(mPublicData);
    }

    /**
     * 停止刷新动画，花蜜那回滚到第一个 Item
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

}
