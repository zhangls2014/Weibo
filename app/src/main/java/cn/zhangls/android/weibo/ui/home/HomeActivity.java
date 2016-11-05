package cn.zhangls.android.weibo.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.network.HttpMethods;
import cn.zhangls.android.weibo.network.model.User;
import cn.zhangls.android.weibo.ui.login.LoginActivity;
import cn.zhangls.android.weibo.utils.KeyBoardUtil;
import cn.zhangls.android.weibo.utils.ToastUtil;
import rx.Subscriber;

public class HomeActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationBar.OnTabSelectedListener,
        HomeContract.View {

    /**
     * VIewPager 所添加的 Fragment 数量
     */
    private static final int FRAGMENT_COUNT = 5;
    /**
     * Auth RequestCode
     */
    private final int AUTH_REQUEST_CODE = 10011;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    /**
     * DrawerLayout
     */
    private DrawerLayout mDrawerLayout;
    /**
     * ViewPager 容纳Fragment
     */
    private ViewPager mViewPager;
    /**
     * NavigationView 导航栏
     */
    private NavigationView mNavigationView;
    /**
     * Presenter
     */
    private HomeContract.Presenter mPresenter;
    /**
     * SearchView
     */
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        DataBindingUtil.setContentView(this, R.layout.activity_home);

        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken =  AccessTokenKeeper.readAccessToken(this);

        // 初始化Presenter
        new HomePresenter(this);

        // 设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_home_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 设置抽屉开关
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.ac_home_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {mPresenter.start();
            }
        });

        final BottomNavigationBar bottomNavBar = (BottomNavigationBar) findViewById(R.id.ac_home_bottom_nav_bar);
        bottomNavBar.setTabSelectedListener(this);
//        bottomNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavBar.setMode(BottomNavigationBar.MODE_SHIFTING);
//        bottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavBar.addItem(new BottomNavigationItem(R.drawable.tabbar_home_highlighted, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_message_center_highlighted, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_compose_background_icon_add, "添加"))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_discover_highlighted, "发现"))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_profile_highlighted, "我"))
                .setFirstSelectedPosition(0)
                .initialise();

        // 对 ViewPager 进行设置
        mViewPager = (ViewPager) findViewById(R.id.ac_home_view_pager);
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(5);//设置缓存的页数
            mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), this, FRAGMENT_COUNT));
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

        // 初始化 ViewPager Header
        initHeader();

        //初始化SearchView
        initSearchView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    /**
     * 使用该方法显示OptionMenu 的 Icon
     *
     * @param menu menu
     * @return super.onPrepareOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if(menu != null){
//            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
//                try{
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch(Exception e){
//                    Logger.d(e.getMessage());
//                }
//            }
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 搜索菜单按钮
     */
    private MenuItem searchMenu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.ac_home_menu_search:
                searchMenu = item;
                if (mSearchView.getVisibility() == View.GONE) {
                    openSearchView();
                } else {
                    closeSearchView();
                }
                break;
            case R.id.ac_home_menu_radar:
                showShortToast("雷达");
                break;
            case R.id.ac_home_menu_scan:
                showShortToast("扫一扫");
                break;
            case R.id.ac_home_menu_taxi:
                showShortToast("打车");
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTH_REQUEST_CODE && resultCode == 0) {
            initHeader();
        }
    }

    /**
     * 获取用户信息
     *
     * avatar 头像
     * screenName 用户昵称
     * description 简介
     * mUser 用户实体类
     */
    private SimpleDraweeView avatar;
    private TextView screenName;
    private TextView description;
    private User mUser;
    private void initHeader() {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .setFailureImage(R.mipmap.ic_launcher)
                .build();
        RoundingParams params = new RoundingParams();
        params.setCornersRadius(getResources().getDimension(R.dimen.user_avatar_radius));
        hierarchy.setRoundingParams(params);
        //头像
        avatar = (SimpleDraweeView) mNavigationView.getHeaderView(0)
                .findViewById(R.id.nav_header_avatar);
        avatar.setHierarchy(hierarchy);
        //用户昵称
        screenName = (TextView) mNavigationView.getHeaderView(0)
                .findViewById(R.id.nav_header_screen_name);
        //简介
        description = (TextView) mNavigationView.getHeaderView(0)
                .findViewById(R.id.nav_header_description);
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken == null || !mAccessToken.isSessionValid()){
            screenName.setText(getString(R.string.weibo_login));
            description.setText("");
        } else {
            getUser(mAccessToken.getToken(), Long.parseLong(mAccessToken.getUid()));
        }
    }

    /**
     * 获取用户信息
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param uid 需要查询的用户ID。
     */
    private void getUser(String access_token, long uid) {
        Logger.i("access_token", access_token);
        Subscriber<User> subscriber = new Subscriber<User>() {

            @Override
            public void onCompleted() {
                avatar.setImageURI(mUser.getAvatar_large());
                screenName.setText(mUser.getScreen_name());
                description.setText(mUser.getDescription());
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(HomeActivity.this, "获取用户信息失败");
            }

            @Override
            public void onNext(User user) {
                mUser = user;
            }
        };

        HttpMethods.getInstance().getUser(subscriber, access_token, uid);
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mSearchView.getVisibility() == View.VISIBLE) {
            closeSearchView();
        } else {
            moveTaskToBack(false);//保存Activity的状态
            super.onBackPressed();
        }
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
     * NavigationView Header 的点击事件
     */
    @Override
    public void onHeaderClick() {
        if (mAccessToken == null || !mAccessToken.isSessionValid()){
            startActivityForResult(new Intent(HomeActivity.this, LoginActivity.class), AUTH_REQUEST_CODE);
        }
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

    /**
     * 初始化SearchView
     */
    private void initSearchView() {
        mSearchView = (SearchView) findViewById(R.id.ac_home_search);
        mSearchView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconified(false);//完全可见
        mSearchView.setVisibility(View.GONE);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchView();
                return true;
            }
        });
    }

    /**
     * 打开 SearchView
     */
    private void openSearchView() {
        searchMenu.setIcon(R.drawable.text_icon_search_highlighted);
        mSearchView.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mSearchView,
                "y",
                mSearchView.getTop() - mSearchView.getHeight(),
                mSearchView.getTop()
        );
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * 关闭 SearchView
     */
    private void closeSearchView() {
        searchMenu.setIcon(R.drawable.text_icon_search);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mSearchView,
                "y",
                mSearchView.getTop(),
                mSearchView.getTop() - mSearchView.getHeight()
        );
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                KeyBoardUtil.closeKeyboard(mSearchView, HomeActivity.this);
            }

            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mSearchView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }
}