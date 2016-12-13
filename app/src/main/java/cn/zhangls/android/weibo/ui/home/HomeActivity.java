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

package cn.zhangls.android.weibo.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity
        implements BottomNavigationBar.OnTabSelectedListener, HomeContract.View {

    /**
     * ViewPager 容纳Fragment
     */
    private ViewPager mViewPager;
    /**
     * Presenter
     */
    private HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_home);

        // 初始化Presenter
        new HomePresenter(this);
        // 工具栏名称数组
        String[] titles = new String[]{
                getResources().getString(R.string.activity_home_home),
                getResources().getString(R.string.activity_home_message),
                getResources().getString(R.string.activity_home_add),
                getResources().getString(R.string.activity_home_discover),
                getResources().getString(R.string.activity_home_me)
        };

        final BottomNavigationBar bottomNavBar = (BottomNavigationBar) findViewById(R.id.ac_home_bottom_nav_bar);
        bottomNavBar.setTabSelectedListener(this);
        bottomNavBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavBar.addItem(new BottomNavigationItem(R.drawable.tabbar_home_highlighted, titles[0]))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_message_center_highlighted, titles[1]))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_compose_background_icon_add, titles[2]))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_discover_highlighted, titles[3]))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_profile_highlighted, titles[4]))
                .setFirstSelectedPosition(0)
                .initialise();

        // 对 ViewPager 进行设置
        mViewPager = (ViewPager) findViewById(R.id.ac_home_view_pager);
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(5);//设置缓存的页数
            mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), titles.length));
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    bottomNavBar.selectTab(mViewPager.getCurrentItem());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);//保存Activity的状态
        super.onBackPressed();
    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param position The position of the tab that was selected
     */
    @Override
    public void onTabSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param position The position of the tab that was unselected
     */
    @Override
    public void onTabUnselected(int position) {
    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param position The position of the tab that was reselected.
     */
    @Override
    public void onTabReselected(int position) {
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}