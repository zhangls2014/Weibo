/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.GroupList;
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
     * AppCompatSpinner
     */
    private AppCompatSpinner mSpinner;
    /**
     * WeiboRecyclerAdapter 适配器
     */
    private WeiboRecyclerAdapter mWeiboRecyclerAdapter;
    /**
     * presenter 接口
     */
    private WeiboContract.Presenter mWeiboPresenter;
    /**
     * RecyclerView 的 LayoutManager
     */
    private LinearLayoutManager linearLayoutManager;
    private ArrayAdapter<CharSequence> mAdapter;

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
//        mSpinner = (AppCompatSpinner) view.findViewById(R.id.fg_home_spinner);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // don't show toolbar title
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        // TODO NullPointerException
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        // set spinner
        setSpinner();
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

    private void setSpinner() {
        mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.group_list_name, android.R.layout.simple_spinner_item);
        mSpinner.setDropDownWidth(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, String.format("你选择了 %s", mSpinner.getSelectedItem().toString()), Snackbar.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
     * 完成数据加载
     *
     * @param statusList 数据源
     */
    @Override
    public void refreshCompleted(StatusList statusList) {
        mWeiboRecyclerAdapter.setData(statusList.getStatuses());
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
     * 设置 Spinner 数据
     *
     * @param groupList GroupList
     */
    @Override
    public void setSpinnerData(GroupList groupList) {
        String[] groupNames = new String[groupList.getGroupList().size()];
        for (int i = 0; i < groupList.getGroupList().size(); i++) {
            groupNames[i] = groupList.getGroupList().get(i).getName();
        }
        mAdapter.clear();
        mAdapter.addAll(groupNames);
        mAdapter.notifyDataSetChanged();
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
