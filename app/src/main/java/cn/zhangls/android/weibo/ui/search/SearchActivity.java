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

package cn.zhangls.android.weibo.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import java.util.ArrayList;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivitySearchBinding;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.ui.search.content.DetailUser;
import cn.zhangls.android.weibo.ui.search.content.DetailUserViewBinder;
import cn.zhangls.android.weibo.ui.search.content.SummaryUser;
import cn.zhangls.android.weibo.ui.search.content.SummaryUserViewBinder;
import cn.zhangls.android.weibo.ui.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.Repost;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPicture;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.RepostViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleTextViewProvider;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class SearchActivity extends BaseActivity implements SearchContract.View {

    /**
     * 显示的搜索类型
     */
    private enum RecyclerItemType {
        ITEM_RECYCLER_TYPE_STATUS_NO_PIC,// 微博不包含图片
        ITEM_RECYCLER_TYPE_STATUS_HAVE_PIC,// 微博包含图片
        ITEM_RECYCLER_TYPE_RETWEETED_STATUS_NO_PIC,// 被转发微博不包含图片
        ITEM_RECYCLER_TYPE_RETWEETED_STATUS_HAVE_PIC,// 被转发微博包含图片
        ITEM_RECYCLER_TYPE_USER_SUMMARY,// 综合搜索中的用户类型
        ITEM_RECYCLER_TYPE_USER_DETAIL// 搜索用户时的用户类型
    }
    /**
     * 搜索类型
     */
    private enum SearchType {
        ITEM_SEARCH_TYPE_ALL,// 综合搜索
        ITEM_SEARCH_TYPE_USER,// 搜索用户
        ITEM_SEARCH_TYPE_WEIBO// 搜索微博
    }

    /**
     * 搜索类型
     */
    private SearchType mSearchType = SearchType.ITEM_SEARCH_TYPE_ALL;
    /**
     * MultiTypeAdapter
     */
    private MultiTypeAdapter mMultiTypeAdapter;
    /**
     * 类型池
     */
    private Items mItems;
    /**
     * RecyclerView 的 LayoutManager
     */
    private LinearLayoutManager linearLayoutManager;
    /**
     * ActivitySearchBinding
     */
    private ActivitySearchBinding mBinding;
    /**
     * SearchPresenter
     */
    private SearchContract.Presenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);

        initialize();
    }

    /**
     * 初始化方法
     */
    private void initialize() {
        new SearchPresenter(SearchActivity.this, this);
        mSearchPresenter.start();

        setupFloatingSearch();
        setupSearchResultsView();
    }

    /**
     * 设置 FloatingSearchView
     */
    private void setupFloatingSearch() {
        // 搜索监听
        mBinding.fsvAcSearchFloatingSearch.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                switch (mSearchType) {
                    case ITEM_SEARCH_TYPE_ALL:
                        mSearchPresenter.searchUserAndWeibo(currentQuery);
                        break;
                    case ITEM_SEARCH_TYPE_WEIBO:
                        mSearchPresenter.searchStatus(currentQuery);
                        break;
                    case ITEM_SEARCH_TYPE_USER:
                        mSearchPresenter.searchUser(currentQuery);
                }
            }
        });
        // Item 点击事件监听
        mBinding.fsvAcSearchFloatingSearch.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search_menu_all:
                        mSearchType = SearchType.ITEM_SEARCH_TYPE_ALL;
                        break;
                    case R.id.action_search_menu_weibo:
                        mSearchType = SearchType.ITEM_SEARCH_TYPE_WEIBO;
                        break;
                    case R.id.action_search_menu_people:
                        mSearchType = SearchType.ITEM_SEARCH_TYPE_USER;
                        break;
                    case R.id.action_search_menu_open_in_weibo:
                        openInWeibo();
                        break;
                    default:
                        mSearchType = SearchType.ITEM_SEARCH_TYPE_ALL;
                        break;
                }
                item.setChecked(true);
            }
        });
        // Home 按钮点击事件监听
        mBinding.fsvAcSearchFloatingSearch.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化搜索结果组件
     */
    private void setupSearchResultsView() {
        AttitudesAPI attitudesAPI = new AttitudesAPI(SearchActivity.this,
                AccessTokenKeeper.readAccessToken(SearchActivity.this));

        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 注册文字类型 ViewHolder
        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider(true));
        // 注册图片类型 ViewHolder
        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider(true));
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(Repost.class, new RepostViewProvider(true));
        // 注册转发图片类型 ViewHolder
        mMultiTypeAdapter.register(RepostPicture.class, new RepostPictureViewProvider(true));
        // 注册综合搜索中的用户类型
        mMultiTypeAdapter.register(SummaryUser.class, new SummaryUserViewBinder());
        // 注册用户搜索中的用户类型
        mMultiTypeAdapter.register(DetailUser.class, new DetailUserViewBinder());

        // 实例化 LinearLayoutManager
        linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        // 设置 RecyclerView
        mBinding.rvAcSearchResults.setLayoutManager(linearLayoutManager);
        mBinding.rvAcSearchResults.setAdapter(mMultiTypeAdapter);
        // 实现对 RecyclerView Item 类型判断的逻辑
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                Class m;
                switch (getItemViewType(item)) {
                    case ITEM_RECYCLER_TYPE_STATUS_NO_PIC:
                        m = SimpleText.class;
                        break;
                    case ITEM_RECYCLER_TYPE_STATUS_HAVE_PIC:
                        m = Picture.class;
                        break;
                    case ITEM_RECYCLER_TYPE_RETWEETED_STATUS_NO_PIC:
                        m = Repost.class;
                        break;
                    case ITEM_RECYCLER_TYPE_RETWEETED_STATUS_HAVE_PIC:
                        m = RepostPicture.class;
                        break;
                    case ITEM_RECYCLER_TYPE_USER_SUMMARY:
                        m = SimpleText.class;
                        break;
                    case ITEM_RECYCLER_TYPE_USER_DETAIL:
                        m = SimpleText.class;
                        break;
                    default:
                        m = SimpleText.class;
                        break;
                }
                return m;
            }

            @NonNull
            @Override
            public Object onFlattenItem(@NonNull Object item) {
                return item;
            }
        });
    }

    /**
     * 获取 Item View Type
     *
     * @param object 数据
     */
    private RecyclerItemType getItemViewType(Object object) {
        if (object instanceof Status) {
            if (((Status) object).getRetweeted_status() != null) {
                if (((Status) object).getRetweeted_status().getPic_urls() != null
                        && !((Status) object).getRetweeted_status().getPic_urls().isEmpty()) {// 被转发微博存在图片
                    return RecyclerItemType.ITEM_RECYCLER_TYPE_RETWEETED_STATUS_HAVE_PIC;
                } else {// 被转发微博不存在图片
                    return RecyclerItemType.ITEM_RECYCLER_TYPE_RETWEETED_STATUS_NO_PIC;
                }
            } else {
                if (((Status) object).getPic_urls() != null && !((Status) object).getPic_urls().isEmpty()) {// 微博包含图片
                    return RecyclerItemType.ITEM_RECYCLER_TYPE_STATUS_HAVE_PIC;
                } else {// 微博不包含图片
                    return RecyclerItemType.ITEM_RECYCLER_TYPE_STATUS_NO_PIC;
                }
            }
        } else if (object instanceof User && mSearchType == SearchType.ITEM_SEARCH_TYPE_USER) {
            return RecyclerItemType.ITEM_RECYCLER_TYPE_USER_DETAIL;
        } else {
            return RecyclerItemType.ITEM_RECYCLER_TYPE_USER_SUMMARY;
        }
    }

    /**
     * 在官方微博中打开该微博详情页
     */
    private void openInWeibo() {
        AuthInfo authInfo = new AuthInfo(SearchActivity.this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        WeiboPageUtils
                .getInstance(SearchActivity.this.getApplicationContext(), authInfo)
                .openWeiboSearchPage("", false);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.fsvAcSearchFloatingSearch.isSearchBarFocused()) {
            mBinding.fsvAcSearchFloatingSearch.setSearchFocused(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mSearchPresenter = presenter;
    }

    /**
     * 显示登录 Snackbar
     */
    @Override
    public void showLoginSnackbar() {
        showLoginSnackbar(mBinding.rvAcSearchResults);
    }

    /**
     * 显示推荐用户
     *
     * @param users 用户
     */
    @Override
    public void loadUsers(ArrayList<User> users) {
        if (users != null && users.size() > 0) {
            mItems.clear();
            mItems.add(users);
            mMultiTypeAdapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(0);
        }
    }

    /**
     * 显示推荐微博
     *
     * @param statuses 微博
     */
    @Override
    public void loadStatuses(ArrayList<Status> statuses) {
        if (statuses != null && statuses.size() > 0) {
            mItems.clear();
            mItems.add(statuses);
            mMultiTypeAdapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(0);
        }
    }

    /**
     * 显示推荐用户和推荐微博
     *
     * @param users    用户
     * @param statuses 微博
     */
    @Override
    public void loadUserAndWeibo(ArrayList<User> users, ArrayList<Status> statuses) {

    }
}
