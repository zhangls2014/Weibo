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

package cn.zhangls.android.weibo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityHomeBinding;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.ui.edit.EditActivity;
import cn.zhangls.android.weibo.ui.search.SearchActivity;
import cn.zhangls.android.weibo.ui.setting.SettingsActivity;
import cn.zhangls.android.weibo.ui.user.UserActivity;
import cn.zhangls.android.weibo.ui.weibo.SwipeRefreshFragment;
import cn.zhangls.android.weibo.ui.weibo.WeiboFragment;
import cn.zhangls.android.weibo.views.NoSwipeViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    /**
     * ViewPager 容纳Fragment
     */
    private NoSwipeViewPager mViewPager;
    /**
     * Presenter
     */
    private HomeContract.Presenter mHomePresenter;
    /**
     * 底部导航栏 Item 数量
     */
    private static final int BOTTOM_NAV_ITEM_NUM = 3;
    /**
     * 侧边栏用户头像
     */
    private CircleImageView mCircleImageView;
    /**
     * 侧边栏用户名
     */
    private TextView mNameText;
    /**
     * 侧边栏用户简介
     */
    private TextView mDescriptionText;
    /**
     * BottomNavigationView
     */
    private BottomNavigationView mBottomNavigationView;
    /**
     * Toolbar
     */
    private Toolbar mToolbar;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton mFab;
    /**
     * ActivityHomeBinding
     */
    private ActivityHomeBinding mBinding;
    /**
     * DrawerLayout 是否刚开始滑出
     */
    private boolean mDrawerStartSlide = true;

    private boolean USER_INFO_LOADED = false;
    /**
     * WeiboFragment
     */
    private WeiboFragment.WeiboListType mWeiboListType = WeiboFragment.WeiboListType.FRIEND;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        findViews();
        init();
    }

    /**
     * 通过 findViewById() 方法获取控件
     */
    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_item_home_app_bar);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.ac_home_bottom_nav_bar);
        mViewPager = (NoSwipeViewPager) findViewById(R.id.ac_home_view_pager);
        mFab = (FloatingActionButton) findViewById(R.id.item_home_app_bar_fab);
        mCircleImageView = (CircleImageView) mBinding.navView.getHeaderView(0).findViewById(R.id.item_home_header_avatar);
        mNameText = (TextView) mBinding.navView.getHeaderView(0).findViewById(R.id.item_home_header_name);
        mDescriptionText = (TextView) mBinding.navView.getHeaderView(0).findViewById(R.id.item_home_header_description);
    }

    /**
     * 初始化方法
     */
    private void init() {
        // 初始化Presenter
        new HomePresenter(getApplicationContext(), this);

        // 设置 Toolbar
        setSupportActionBar(mToolbar);

        // 设置底部导航栏
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setBottomNavChecked(item.getItemId());
                        return true;
                    }
                });

        // 对 ViewPager 进行设置
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(BOTTOM_NAV_ITEM_NUM);//设置缓存的页数
            mViewPager.setScrollable(false);
            mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), HomeActivity.this, BOTTOM_NAV_ITEM_NUM));
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                    if (position == 0) {
                        mFab.setVisibility(View.VISIBLE);
                    } else {
                        mFab.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0 && mDrawerStartSlide) {
                    mHomePresenter.start();
                    mHomePresenter.getUser();
                    switch (mWeiboListType) {
                        case FRIEND:
                            mBinding.navView.getMenu().getItem(0).setChecked(true);
                            break;
                        case USER:
                            mBinding.navView.getMenu().getItem(1).setChecked(true);
                            break;
                        case FAVORITE:
                            mBinding.navView.getMenu().getItem(2).setChecked(true);
                            break;
                        default:
                            mBinding.navView.getMenu().getItem(0).setChecked(true);
                            break;
                    }
                    mDrawerStartSlide = false;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerStartSlide = true;
            }
        };
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // 设置侧边栏
        mBinding.navView.setNavigationItemSelectedListener(this);
        // FloatingActionButton 点击事件监听
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(HomeActivity.this, null, EditActivity.TYPE_CONTENT_UPDATE_STATUS, null);
            }
        });
    }

    /**
     * 在官方微博中打开该用户主页
     *
     * @param uid 用户 uid
     */
    private void openInWeibo(String uid) {
        AuthInfo authInfo = new AuthInfo(HomeActivity.this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        WeiboPageUtils
                .getInstance(HomeActivity.this, authInfo)
                .startUserMainPage(uid);
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
            case R.id.ac_home_bottom_nav_discover:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);// 不保存Activity的状态
            super.onBackPressed();
        }
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    public void setPresenter(HomeContract.Presenter presenter) {
        mHomePresenter = presenter;
    }

    /**
     * 显示登录 Snackbar
     */
    @Override
    public void showLoginSnackbar() {
        showLoginSnackbar(mBottomNavigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ac_home_menu_search:
                // 打开搜索页面
                Intent intent = new Intent(this, SearchActivity.class);
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
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (mViewPager.getCurrentItem() == 0) {
            SwipeRefreshFragment fragment = (SwipeRefreshFragment) getSupportFragmentManager()
                    .findFragmentByTag(makeFragmentName(R.id.ac_home_view_pager, mViewPager.getCurrentItem()));
            switch (item.getItemId()) {
                case R.id.menu_ac_home_drawer_friend:
                    fragment.setWeiboListType(WeiboFragment.WeiboListType.FRIEND);
                    mWeiboListType = WeiboFragment.WeiboListType.FRIEND;
                    break;
                case R.id.menu_ac_home_drawer_user:
                    fragment.setWeiboListType(WeiboFragment.WeiboListType.USER);
                    mWeiboListType = WeiboFragment.WeiboListType.USER;
                    break;
                case R.id.menu_ac_home_drawer_favorites:
                    fragment.setWeiboListType(WeiboFragment.WeiboListType.FAVORITE);
                    mWeiboListType = WeiboFragment.WeiboListType.FAVORITE;
                    break;
                case R.id.nav_manage:

                    break;
            }
        }
        switch (item.getItemId()) {
            case R.id.nav_share:

                break;
            case R.id.menu_ac_home_drawer_settings:
                SettingsActivity.actionStart(HomeActivity.this);
                break;
        }
        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    /**
     * 设置用户信息
     *
     * @param user 用户信息
     */
    @Override
    public void loadUserInfo(final User user) {
        mNameText.setText(user.getScreen_name());
        mDescriptionText.setText(user.getDescription());
        //设置圆形图片
        Glide.with(this)
                .load(user.getAvatar_large())
                .centerCrop()
                .placeholder(R.drawable.avator_default)
                .dontAnimate()
                .into(mCircleImageView);
        USER_INFO_LOADED = true;
        mBinding.navView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (USER_INFO_LOADED) {
                    UserActivity.actonStart(HomeActivity.this, user);
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
    }
}