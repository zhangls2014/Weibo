package cn.zhangls.android.weibo.ui.home.weibo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.StatusList;


public class WeiboFragment extends Fragment implements WeiboContract.WeiboView {

    private static final String TAG = "WeiboFragment";
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
    private WeiboRecyclerAdapter mWeiboRecyclerAdapter;
    /**
     * 数据源
     */
    private StatusList mStatusData;
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
        mWeiboRecyclerAdapter = new WeiboRecyclerAdapter(getContext());
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
        mToolbar = (Toolbar) view.findViewById(R.id.fg_home_toolbar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Display show toolbar title
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
     * 回到顶部
     */
    @Override
    public void backToTop() {
        linearLayoutManager.scrollToPosition(0);
    }

    /**
     * 完成数据加载
     *
     * @param statusList 数据源
     */
    @Override
    public void refreshCompleted(StatusList statusList) {
        mStatusData = statusList;
        Log.d(TAG, "refreshCompleted: " + statusList.getStatuses().size());
        mWeiboRecyclerAdapter.setData(mStatusData.getStatuses());
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
