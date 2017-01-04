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

package cn.zhangls.android.weibo.ui.repost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.utils.SharedPreferenceInfo;

public class RepostActivity extends BaseActivity implements RepostContract.RepostView {

    /**
     * RepostPresenter
     */
    private RepostContract.RepostPresenter mRepostPresenter;
    /**
     * SharedPreferenceInfo
     */
    private SharedPreferenceInfo mPreferenceInfo;

    public static void actionStart(Context context, Status status) {
        Intent intent = new Intent(context, RepostActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repost);
        new RepostPresenter(this, this);
        mRepostPresenter.start();

        mPreferenceInfo = new SharedPreferenceInfo(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_repost_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_repost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSubTitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_repost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.ac_repost_menu_send:
                showShortToast("你点击了发送按钮");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(RepostContract.RepostPresenter presenter) {
        mRepostPresenter = presenter;
    }

    @Override
    public void setSubTitle() {
        String userName = mPreferenceInfo.getUserName();
        if (userName != null && !userName.isEmpty()) {
            getSupportActionBar().setSubtitle(userName);
        } else {
            mRepostPresenter.getUserByService();
        }
    }
}
