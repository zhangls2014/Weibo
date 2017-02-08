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
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityCommentBinding;
import cn.zhangls.android.weibo.network.models.Status;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CommentActivity extends BaseActivity implements CommentContract.CommentView {

    /**
     * weibo url
     */
    public final static String WEIBO_URL = "weibo_url";
    /**
     * weibo status id
     */
    public final static String WEIBO_STATUS_ID = "weibo_status_id";
    /**
     * weibo user id
     */
    public final static String WEIBO_USER_ID = "weibo_user_id";

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
     * weibo user id
     */
    private long mWeiboUserId;
    /**
     * weibo status id
     */
    private long mWeiboStatusId;

    public static void actionStart(Context context, long userId, long statusId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(WEIBO_USER_ID, userId);
        intent.putExtra(WEIBO_STATUS_ID, statusId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_comment);
//        init();
        initialize();
    }

    /**
     * 初始化方法
     */
    private void init() {
//        // Appbar 返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        mWeiboUserId = getIntent().getLongExtra(WEIBO_USER_ID, 0);
//        mWeiboStatusId = getIntent().getLongExtra(WEIBO_STATUS_ID, 0);
//
//        new CommentPresenter(this, this);
//        mCommentPresenter.start();
//
//        AttitudesAPI attitudesAPI = new AttitudesAPI(this, AccessTokenKeeper.readAccessToken(this));
//
//        // 设置RecyclerView
//        mItems = new Items();
//        // WeiboRecyclerAdapter 适配器
//        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
//        // 注册文字类型 ViewHolder
//        mMultiTypeAdapter.register(SimpleText.class, new SimpleTextViewProvider(attitudesAPI));
//        // 注册图片类型 ViewHolder
//        mMultiTypeAdapter.register(Picture.class, new PictureViewProvider(attitudesAPI));
//        // 转发类型 ViewHolder
//        mMultiTypeAdapter.register(Repost.class, new RepostViewProvider(attitudesAPI));
//        // 注册转发图片类型 ViewHolder
//        mMultiTypeAdapter.register(RepostPicture.class, new RepostPictureViewProvider(attitudesAPI));
//
//        mBinding.acCommentRecycler.setAdapter(mMultiTypeAdapter);
//        // 设置 Item 的类型
//        mMultiTypeAdapter.setFlatTypeAdapter(new FlatTypeAdapter() {
//            @NonNull
//            @Override
//            public Class onFlattenClass(@NonNull Object o) {
//                Class m;
//                switch (getItemViewType((Status) o)) {
//                    case ITEM_VIEW_TYPE_STATUS_NO_PIC:
//                        m = SimpleText.class;
//                        break;
//                    case ITEM_VIEW_TYPE_STATUS_HAVE_PIC:
//                        m = Picture.class;
//                        break;
//                    case ITEM_VIEW_TYPE_RETWEETED_STATUS_NO_PIC:
//                        m = Repost.class;
//                        break;
//                    case ITEM_VIEW_TYPE_RETWEETED_STATUS_HAVE_PIC:
//                        m = RepostPicture.class;
//                        break;
//                    default:
//                        m = SimpleText.class;
//                        break;
//                }
//                return m;
//            }
//
//            @NonNull
//            @Override
//            public Object onFlattenItem(@NonNull Object o) {
//                return o;
//            }
//        });
//
//        //设置SwipeRefreshLayout
//        mBinding.acCommentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mCommentPresenter.getStatus(mWeiboStatusId);
//            }
//        });
//        mBinding.acCommentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
    }

    private void initialize() {
        // Appbar 返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWeiboUserId = getIntent().getLongExtra(WEIBO_USER_ID, 0);
        mWeiboStatusId = getIntent().getLongExtra(WEIBO_STATUS_ID, 0);

//        new CommentPresenter(this, this);
//        mCommentPresenter.start();

        //设置SwipeRefreshLayout
        mBinding.acCommentSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.acCommentWebView.reload();
            }
        });
        mBinding.acCommentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));

        final StringBuilder url = new StringBuilder();
        url.append(WEIBO_BASE_URL)
                .append("/").append(String.valueOf(mWeiboUserId))
                .append("/").append(String.valueOf(mWeiboStatusId));
        mBinding.acCommentWebView.loadUrl(url.toString());
        mBinding.acCommentWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mBinding.acCommentWebView.loadUrl(url.toString());
                return true;
            }
        });
        // 启用支持javascript
        mBinding.acCommentWebView.getSettings().setJavaScriptEnabled(true);
        // 优先使用缓存
        mBinding.acCommentWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.acCommentWebView.canGoBack()) {
            mBinding.acCommentWebView.goBack();
        } else {
            super.onBackPressed();
        }
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
