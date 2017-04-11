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

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;

public class SwipeRefreshFragment extends BaseFragment {

    private static final String WEIBO_LIST_TYPE = "weibo_list_type";

    /**
     * SwipeRefreshLayout
     */
    private SwipeRefreshLayout mRefreshLayout;
    /**
     * WeiboFragment
     */
    private WeiboFragment mWeiboFragment;

    public void setWeiboListType(WeiboFragment.WeiboListType weiboListType) {
        mWeiboListType = weiboListType;
        mWeiboFragment.setWeiboListType(mWeiboListType);
    }

    /**
     * 微博列表类型
     */
    private WeiboFragment.WeiboListType mWeiboListType;

    public SwipeRefreshFragment() {
        // Required empty public constructor
    }

    public static SwipeRefreshFragment newInstance(WeiboFragment.WeiboListType weiboListType) {
        SwipeRefreshFragment weiboFragment = new SwipeRefreshFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WEIBO_LIST_TYPE, weiboListType);
        weiboFragment.setArguments(bundle);
        return weiboFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWeiboListType = (WeiboFragment.WeiboListType) getArguments().getSerializable(WEIBO_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_swipe_refresh, container, false);
        mRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.srl_fg_swipe_refresh);
        return inflate;
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    @Override
    protected void loadData() {
        mWeiboFragment = WeiboFragment.newInstance(mWeiboListType);
        mWeiboFragment.setWeiboRefreshListener(new WeiboFragment.OnWeiboRefreshListener() {
            @Override
            public void startLoad() {
                mRefreshLayout.setRefreshing(true);
            }

            @Override
            public void stopLoad() {
                mRefreshLayout.setRefreshing(false);
            }
        });
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fg_swipe_refresh, mWeiboFragment)
                .commit();

        //设置SwipeRefreshLayout
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWeiboFragment.setWeiboListType(mWeiboListType);
            }
        });
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    private WeiboFragment getWeiboFragment() {
        return (WeiboFragment) getFragmentManager()
                .findFragmentByTag(makeFragmentName(R.id.ac_home_view_pager, mWeiboFragment.getId()));
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
