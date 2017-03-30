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

package cn.zhangls.android.weibo.ui.home.discovery;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;
import cn.zhangls.android.weibo.databinding.FragmentDiscoveryBinding;
import cn.zhangls.android.weibo.network.models.TrendHourly;
import cn.zhangls.android.weibo.ui.weibo.WeiboFragment;

public class DiscoveryFragment extends BaseFragment implements DiscoveryContract.FindView {

    /**
     * DiscoveryPresenter
     */
    private DiscoveryContract.Presenter mFindPresenter;
    /**
     * FragmentDiscoveryBinding
     */
    private FragmentDiscoveryBinding mBinding;
    /**
     * Item 数量
     */
    private int ITEM_COUNTS = 3;
    /**
     * FragmentAdapter
     */
    private FragmentAdapter mFragmentAdapter;

    public static DiscoveryFragment newInstance() {
        return new DiscoveryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discovery, container, false);
        return mBinding.getRoot();
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    @Override
    protected void loadData() {
        initialize();
    }

    private void initialize() {
        new DiscoveryPresenter(getContext().getApplicationContext(), DiscoveryFragment.this);
        mFindPresenter.start();

        mFragmentAdapter = new FragmentAdapter(getFragmentManager(), getContext(), ITEM_COUNTS);
        mBinding.vpFgDiscoveryContent.setAdapter(mFragmentAdapter);
        mBinding.vpFgDiscoveryContent.setOffscreenPageLimit(mFragmentAdapter.getCount());
        if (mFragmentAdapter.getCount() > 3) {
            mBinding.tabFgDiscoveryTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mBinding.tabFgDiscoveryTitle.setTabMode(TabLayout.MODE_FIXED);
        }
        mBinding.tabFgDiscoveryTitle.setupWithViewPager(mBinding.vpFgDiscoveryContent);
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(DiscoveryContract.Presenter presenter) {
        mFindPresenter = presenter;
    }

    /**
     * 显示热门话题
     *
     * @param trendHourly TrendHourly 数据结构体
     */
    @Override
    public void showHotTrend(TrendHourly trendHourly) {

    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private int length;
        private Context mContext;

        FragmentAdapter(FragmentManager fm, Context context, int length) {
            super(fm);
            mContext = context;
            this.length = length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.PUBLIC);
                    break;
                case 1:
                    fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.HOT_FAVORITE);
                    break;
                case 2:
                    fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.FAVORITE);
                    break;
                default:
                    fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.PUBLIC);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.fragment_find_tab_title_public_weibo);
                case 1:
                    return mContext.getString(R.string.fragment_find_tab_title_hot_favorites);
                case 2:
                    return mContext.getString(R.string.activity_home_discover);
            }
            return null;
        }
    }
}
