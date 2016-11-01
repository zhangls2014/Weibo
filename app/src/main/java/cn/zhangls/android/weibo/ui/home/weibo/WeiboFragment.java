package cn.zhangls.android.weibo.ui.home.weibo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.HttpResult;
import cn.zhangls.android.weibo.network.model.Timeline;


public class WeiboFragment extends Fragment implements WeiboContract.WeiboView {

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
    private HttpResult<Timeline> mPublicData;
    /**
     * presenter 接口
     */
    private WeiboContract.Presenter mWeiboPresenter;
    /**
     * 悬浮按钮
     */
    private FloatingActionButton fab;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fg_home_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fg_home_recycler);
        fab = (FloatingActionButton) view.findViewById(R.id.fg_home_fab);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化Presenter
        new WeiboPresenter(getContext(), this);
        //设置RecyclerView
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mPublicData = new HttpResult<>();
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

        // fab 点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeiboPresenter.fabClick();
            }
        });

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
     * @param publicTimelineHttpResult 数据源
     */
    @Override
    public void refreshCompleted(HttpResult<Timeline> publicTimelineHttpResult) {
        mPublicData = publicTimelineHttpResult;
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
