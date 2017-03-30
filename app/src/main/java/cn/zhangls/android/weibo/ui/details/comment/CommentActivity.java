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
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import java.util.ArrayList;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityDetailsCommentBinding;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.AttitudesAPI;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.ErrorInfo;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.weibo.content.Picture;
import cn.zhangls.android.weibo.ui.weibo.content.PictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.Repost;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPicture;
import cn.zhangls.android.weibo.ui.weibo.content.RepostPictureViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.RepostViewProvider;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleText;
import cn.zhangls.android.weibo.ui.weibo.content.SimpleTextViewProvider;
import cn.zhangls.android.weibo.ui.edit.EditActivity;
import cn.zhangls.android.weibo.utils.ToastUtil;
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
    private ActivityDetailsCommentBinding mBinding;
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
    /**
     * AlertDialog, 用于"转发列表"点击事件响应
     */
    private AlertDialog mAlertDialog;

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_comment);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        // Appbar 返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWeiboStatus = getIntent().getParcelableExtra(WEIBO_STATUS);
        // 转发微博被删除
        if (mWeiboStatus != null && mWeiboStatus.getRetweeted_status() != null
                && mWeiboStatus.getRetweeted_status().getUser() == null) {
            mBinding.repost.setEnabled(false);
        } else {
            mBinding.repost.setEnabled(true);
        }

        new CommentPresenter(this.getApplicationContext(), this);
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
        mBinding.acCommentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 对 CommentFragment 进行点击事件监听
                if (position == 1) {
                    CommentFragment commentFragment = (CommentFragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.ac_comment_view_pager, mBinding.acCommentViewPager.getCurrentItem()));
                    commentFragment.setItemClickListener(new CommentFragment.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView recyclerView, View view, int position, Comment comment) {
                            createDialog(comment);
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.acCommentViewPager.setOffscreenPageLimit(0);
        mBinding.acCommentViewPager.setScrollable(false);
        mBinding.acCommentTab.setupWithViewPager(mBinding.acCommentViewPager);

        // 底部转发、评论、点赞点击事件监听
        setClickListeners();

        mItems.clear();
        mItems.add(mWeiboStatus);
        mMultiTypeAdapter.notifyDataSetChanged();

        // 获取数据
        mBinding.acCommentSwipeRefresh.setEnabled(true);
        mCommentPresenter.getCommentById(mWeiboStatus.getId(), 0, 0, 50, 1, CommentsAPI.AUTHOR_FILTER_ALL);
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    private void setClickListeners() {
        mBinding.repost.setOnClickListener(this);
        mBinding.comment.setOnClickListener(this);
        mBinding.like.setOnClickListener(this);
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
                    && status.getRetweeted_status().getPic_urls().size() > 0) {// 被转发微博存在图片
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
        getMenuInflater().inflate(R.menu.menu_ac_details_comment, menu);
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
                break;
            case R.id.menu_ac_comment_open_in_weibo:
                openInWeibo();
                break;
        }
        return true;
    }

    /**
     * 在官方微博中打开该微博详情页
     */
    private void openInWeibo() {
        AuthInfo authInfo = new AuthInfo(CommentActivity.this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        WeiboPageUtils
                .getInstance(CommentActivity.this.getApplicationContext(), authInfo)
                .startWeiboDetailPage(String.valueOf(mWeiboStatus.getMid()), mWeiboStatus.getIdstr());
    }

    @Override
    public void onBackPressed() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
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
     * 加载微博评论
     *
     * @param commentList 微博评论列表
     */
    @Override
    public void showComment(CommentList commentList) {
        mBinding.acCommentSwipeRefresh.setRefreshing(false);
        if (mOnLoadCommentListener != null) {
            mOnLoadCommentListener.loadCommentList(commentList);
        }
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
                EditActivity.actionStart(
                        CommentActivity.this,
                        mWeiboStatus,
                        EditActivity.TYPE_CONTENT_REPOST,
                        null
                );
                break;
            case R.id.comment:
                EditActivity.actionStart(
                        CommentActivity.this,
                        mWeiboStatus,
                        EditActivity.TYPE_CONTENT_COMMENT,
                        null
                );
                break;
            case R.id.like:
                Observer<ErrorInfo> observer = new BaseObserver<ErrorInfo>(getApplicationContext()) {

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

    /**
     * 创建对话框
     */
    private void createDialog(final Comment comment) {
        mAlertDialog = new AlertDialog.Builder(CommentActivity.this)
                .setTitle(comment.getUser().getScreen_name())
                .setMessage(comment.getText())
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.fg_comment_reply), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditActivity.actionStart(
                                CommentActivity.this,
                                mWeiboStatus,
                                EditActivity.TYPE_CONTENT_REPLY,
                                comment
                        );
                    }
                })
                .setNegativeButton(getResources().getString(R.string.fg_comment_repost), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditActivity.actionStart(
                                CommentActivity.this,
                                mWeiboStatus,
                                EditActivity.TYPE_CONTENT_REPOST,
                                comment
                        );
                    }
                })
                .setNeutralButton(getResources().getString(R.string.fg_comment_copy), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showShortToast(CommentActivity.this, "成功复制到剪切板");
                    }
                }).create();
        mAlertDialog.show();
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