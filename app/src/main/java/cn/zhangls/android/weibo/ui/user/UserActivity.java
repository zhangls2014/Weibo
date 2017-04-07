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

package cn.zhangls.android.weibo.ui.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityUserBinding;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.ui.weibo.WeiboFragment;

public class UserActivity extends BaseActivity {

    private static final String TAG = "UserActivity";

    /**
     * ActivityUserBinding
     */
    private ActivityUserBinding mBinding;

    private final static String USER_DATA = "user_data";
    /**
     * User
     */
    private User mUser;

    public static void actonStart(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_DATA, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(UserActivity.this, R.layout.activity_user);

        mUser = getIntent().getParcelableExtra(USER_DATA);

        // 设置Toolbar
        setSupportActionBar(mBinding.toolbarActivityUser);
        getSupportActionBar().setTitle(mUser.getScreen_name());
        getSupportActionBar().setSubtitle(mUser.getStatuses_count() + getString(R.string.activity_user_statuses_count));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        // 设置微博头像
//        if (mUser != null) {
//            Glide.with(this)
//                    .load(mUser.getProfile_image_url())
//                    .centerCrop()
//                    .crossFade()
//                    .dontAnimate()
//                    .error(R.drawable.avator_default)
//                    .placeholder(R.drawable.avator_default)
//                    .into(mBinding.fabActivityUserAvatar);
//        }

        mBinding.setUser(mUser);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_ac_user_weibo_fragment, WeiboFragment.newInstance(WeiboFragment.WeiboListType.USER, mUser))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_ac_comment_open_in_weibo:
                openInWeibo(mUser.getIdstr());
                break;
        }
        return true;
    }

    /**
     * 在官方微博中打开该用户主页
     *
     * @param uid 用户 uid
     */
    private void openInWeibo(String uid) {
        AuthInfo authInfo = new AuthInfo(mBinding.getRoot().getContext(), Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        WeiboPageUtils
                .getInstance(mBinding.getRoot().getContext().getApplicationContext(), authInfo)
                .startUserMainPage(uid);
    }

    /**
     * 是否支持滑动返回
     *
     * @return 是否支持滑动返回
     */
    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }
}
