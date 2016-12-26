/*
 * MIT License
 *
 * Copyright (c) 2016 zhangls2014
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
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity implements HomeContract.View {

    /**
     * ViewPager 容纳Fragment
     */
    private ViewPager mViewPager;
    /**
     * Presenter
     */
    private HomeContract.Presenter mPresenter;
    /**
     * 底部导航栏 Item 数量
     */
    private static final int BOTTOM_NAV_ITEM_NUM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_home);

        // 初始化Presenter
        new HomePresenter(this);

        final BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.ac_home_bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setBottomNavChecked(item.getItemId());
                        return true;
                    }
                });

        // 对 ViewPager 进行设置
        mViewPager = (ViewPager) findViewById(R.id.ac_home_view_pager);
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(BOTTOM_NAV_ITEM_NUM);//设置缓存的页数
            mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), BOTTOM_NAV_ITEM_NUM));
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    /**
     * 设置底部导航栏选中
     *
     * @param resId item id
     */
    private void setBottomNavChecked(int resId) {
        switch (resId) {
            case R.id.ac_home_bottom_nav_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ac_home_bottom_nav_message:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ac_home_bottom_nav_add:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.ac_home_bottom_nav_discover:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.ac_home_bottom_nav_me:
                mViewPager.setCurrentItem(4);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);//保存Activity的状态
        super.onBackPressed();
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