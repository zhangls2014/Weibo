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

package cn.zhangls.android.weibo.ui.details.comment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityCommentBinding;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.ErrorInfo;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.home.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.home.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.Repost;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostPicture;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostPictureViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.RepostViewProvider;
import cn.zhangls.android.weibo.ui.home.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.home.weibo.content.SimpleTextViewProvider;
import cn.zhangls.android.weibo.ui.repost.RepostActivity;
import io.reactivex.Observer;
import me.drakeet.multitype.FlatTypeAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CommentActivity extends BaseActivity implements CommentContract.CommentView,
        AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    /**
     * weibo status
     */
    public final static String WEIBO_STATUS = "weibo_status";
    /**
     * Weibo base url
     */
    private final static String WEIBO_BASE_URL = "http://m.weibo.cn";
    /**
     * ItemViewType 微博不包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_NO_PIC = 0;
    /**
     * ItemViewType 微博包含图片
     */
    private static final int ITEM_VIEW_TYPE_STATUS_HAVE_PIC = 1;
    /**
     * ItemViewType 被转发微博不包含图片
     */
    private static final int ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC = 2;
    /**
     * ItemViewType 被转发微博包含图片
     */
    private static final int ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC = 3;
    /**
     * WeiboRecyclerAdapter 适配器
     */
    private MultiTypeAdapter mMultiTypeAdapter;
    /**
     * 类型池
     */
    private Items mItems;
    /**
     * Presenter
     */
    private CommentContract.Presenter mCommentPresenter;
    /**
     * ActivityCommentBinding
     */
    private ActivityCommentBinding mBinding;
    /**
     * Weibo Status
     */
    private Status mWeiboStatus;
    /**
     * AttitudesAPI
     */
    private AttitudesAPI mAttitudesAPI;

    /**
     * OnLoadCommentListener
     */
    private OnLoadCommentListener mOnLoadCommentListener;

    public void setOnLoadCommentListener(OnLoadCommentListener onLoadCommentListener) {
        mOnLoadCommentListener = onLoadCommentListener;
    }

    /**
     * 启动 Activity 方法
     *
     * @param context 上下文对象
     */
    public static void actionStart(Context context, Status status) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(WEIBO_STATUS, status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        // Appbar 返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWeiboStatus = getIntent().getParcelableExtra(WEIBO_STATUS);

        new CommentPresenter(this, this);
        mCommentPresenter.start();

        mAttitudesAPI = new AttitudesAPI(this, AccessTokenKeeper.readAccessToken(this));

        // 设置RecyclerView
        mItems = new Items();
        // WeiboRecyclerAdapter 适配器
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        // 注册文字类型 ViewHolder
        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider(mAttitudesAPI, false));
        // 注册图片类型 ViewHolder
        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider(mAttitudesAPI, false));
        // 转发类型 ViewHolder
        mMultiTypeAdapter.register(Repost.class, new RepostViewProvider(mAttitudesAPI, false));
        // 注册转发图片类型 ViewHolder
        mMultiTypeAdapter.register(RepostPicture.class, new RepostPictureViewProvider(mAttitudesAPI, false));

        mBinding.acCommentRecyclerView.setAdapter(mMultiTypeAdapter);
        // 设置 Item 的类型
        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object o) {
                Class m;
                switch (getItemViewType((Status) o)) {
                    case ITEM_VIEW_TYPE_STATUS_NO_PIC:
                        m = SimpleText.class;
                        break;
                    case ITEM_VIEW_TYPE_STATUS_HAVE_PIC:
                        m = Picture.class;
                        break;
                    case ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC:
                        m = Repost.class;
                        break;
                    case ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC:
                        m = RepostPicture.class;
                        break;
                    default:
                        m = SimpleText.class;
                        break;
                }
                return m;
            }

            @NonNull
            @Override
            public Object onFlattenItem(@NonNull Object o) {
                return o;
            }
        });

        //设置SwipeRefreshLayout
        mBinding.acCommentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentPresenter.getCommentById(mWeiboStatus.getId(), 0, 0, 50, 1, CommentsAPI.AUTHOR_FILTER_ALL);
            }
        });
        mBinding.acCommentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        mBinding.acCommentAppBar.addOnOffsetChangedListener(this);

        ArrayList<String> tabTitleList = new ArrayList<>();
        tabTitleList.add(getResources().getString(R.string.weibo_container_repost) + " " + mWeiboStatus.getReposts_count());
        tabTitleList.add(getResources().getString(R.string.weibo_container_comment) + " " + mWeiboStatus.getComments_count());
        tabTitleList.add(getResources().getString(R.string.weibo_container_likes) + " " + mWeiboStatus.getAttitudes_count());
        // 设置转发、评论、点赞列表
        mBinding.acCommentViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), tabTitleList));
        mBinding.acCommentViewPager.setCurrentItem(1, true);
        mBinding.acCommentViewPager.setOffscreenPageLimit(2);
        mBinding.acCommentViewPager.setScrollable(false);
        mBinding.acCommentTab.setupWithViewPager(mBinding.acCommentViewPager);
        setClickListeners();

        mItems.clear();
        mItems.add(mWeiboStatus);
        mMultiTypeAdapter.notifyDataSetChanged();

        // 获取数据
        mCommentPresenter.getCommentById(mWeiboStatus.getId(), 0, 0, 50, 1, CommentsAPI.AUTHOR_FILTER_ALL);
    }

    private void setClickListeners() {
        mBinding.repost.setOnClickListener(this);
        mBinding.comment.setOnClickListener(this);
        mBinding.like.setOnClickListener(this);
    }

    /**
     * 初始化方法
     */
    private void initialize() {
//        // Appbar 返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        mWeiboUserId = getIntent().getLongExtra(WEIBO_USER_ID, 0);
//        mWeiboStatusId = getIntent().getLongExtra(WEIBO_STATUS_ID, 0);
//        mWeiboUrl = getIntent().getStringExtra(WEIBO_URL);
//
//        //设置SwipeRefreshLayout
//        mBinding.acCommentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mBinding.acCommentWebView.reload();
//            }
//        });
//        mBinding.acCommentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
//
//        if (mWeiboUserId != 0 && mWeiboStatusId != 0) {
//            mWebViewUrl = WEIBO_BASE_URL
//                    + "/" + String.valueOf(mWeiboUserId)
//                    + "/" + String.valueOf(mWeiboStatusId);
//        } else if (mWeiboUrl != null){
//            mWebViewUrl = mWeiboUrl;
//        }
//        mBinding.acCommentWebView.loadUrl(mWebViewUrl);
//
//        mBinding.acCommentWebView.setWebChromeClient(new WebChromeClient() {
//            /**
//             * Tell the host application the current progress of loading a page.
//             *
//             * @param view        The WebView that initiated the callback.
//             * @param newProgress Current page loading progress, represented by
//             */
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    mBinding.acCommentSwipeRefresh.setRefreshing(false);
//                }
//            }
//        });
//        mBinding.acCommentWebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                mBinding.acCommentWebView.loadUrl(mWebViewUrl);
//                return true;
//            }
//        });
//        // 启用支持javascript
//        mBinding.acCommentWebView.getSettings().setJavaScriptEnabled(true);
//        // 优先使用缓存
//        mBinding.acCommentWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    /**
     * 获取 Item View Type
     *
     * @param status 数据
     * @return View Type
     */
    private int getItemViewType(Status status) {
        if (status.getRetweeted_status() != null) {
            if (status.getRetweeted_status().getPic_urls() != null
                    && !status.getRetweeted_status().getPic_urls().isEmpty()) {// 被转发微博存在图片
                return ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC;
            } else {// 被转发微博不存在图片
                return ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC;
            }
        } else {
            if (status.getPic_urls() != null && !status.getPic_urls().isEmpty()) {// 微博包含图片
                return ITEM_VIEW_TYPE_STATUS_HAVE_PIC;
            } else {// 微博不包含图片
                return ITEM_VIEW_TYPE_STATUS_NO_PIC;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_ac_comment_share:
                Drawable drawable = item.getIcon();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
//        if (mBinding.acCommentWebView.canGoBack()) {
//            mBinding.acCommentWebView.goBack();
//        } else {
            super.onBackPressed();
//        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return 是否支持滑动返回
     */
    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 加载微博正文
     *
     * @param status 微博正文
     */
    @Override
    public void showContent(Status status) {
        mItems.clear();
        mItems.add(status);
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    /**
     * 加载微博评论
     *
     * @param commentList 微博评论列表
     */
    @Override
    public void showComment(CommentList commentList) {
        mOnLoadCommentListener.loadCommentList(commentList);
    }

    /**
     * Called when the {@link AppBarLayout}'s layout offset has been changed. This allows
     * child views to implement custom behavior based on the offset (for instance pinning a
     * view at a certain y value).
     *
     * @param appBarLayout   the {@link AppBarLayout} which offset has changed
     * @param verticalOffset the vertical offset for the parent {@link AppBarLayout}, in px
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= 0) {
            mBinding.acCommentSwipeRefresh.setEnabled(true);
        } else {
            mBinding.acCommentSwipeRefresh.setEnabled(false);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repost:
                RepostActivity.actionStart(CommentActivity.this, mWeiboStatus);
                break;
            case R.id.comment:

                break;
            case R.id.like:
                Observer<ErrorInfo> observer = new BaseObserver<ErrorInfo>(CommentActivity.this) {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                };
                mAttitudesAPI.create(observer, mWeiboStatus.getId());
                break;
        }
    }

    /**
     * 加载评论监听接口
     */
    interface OnLoadCommentListener {
        /**
         * 加载评论方法
         *
         * @param commentList 评论列表
         */
        void loadCommentList(CommentList commentList);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mOnLoadCommentListener = null;
    }

    /**
     * 开始刷新
     */
    @Override
    public void startRefresh() {
        mBinding.acCommentSwipeRefresh.setRefreshing(true);
    }

    /**
     * 停止刷新
     */
    @Override
    public void stopRefresh() {
        mBinding.acCommentSwipeRefresh.setRefreshing(false);
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mCommentPresenter = presenter;
    }
}