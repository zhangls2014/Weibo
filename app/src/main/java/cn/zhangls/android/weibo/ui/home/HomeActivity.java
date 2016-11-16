package cn.zhangls.android.weibo.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
    /**
     * Toolbar
     */
    private Toolbar toolbar;
    /**
     * 工具栏名称数组
     */
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_home);

        // 初始化Presenter
        new HomePresenter(this);
        //初始化标题数组
        titles = new String[]{"首页", "消息", "添加", "发现", "我"};

        // 设置Toolbar
        toolbar = (Toolbar) findViewById(R.id.ac_home_toolbar);
        setSupportActionBar(toolbar);

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
                    toolbar.setTitle(titles[position]);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.ac_home_menu_search:
                showShortToast("搜索");
                break;
            case R.id.ac_home_menu_add_friend:
                showShortToast("添加好友");
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