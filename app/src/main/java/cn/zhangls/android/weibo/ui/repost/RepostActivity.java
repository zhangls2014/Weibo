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
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;

import java.net.URLEncoder;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityRepostBinding;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.utils.SharedPreferenceInfo;
import cn.zhangls.android.weibo.utils.TextUtil;

public class RepostActivity extends BaseActivity implements RepostContract.RepostView {

    private final static String DATA_NAME = "data_name";
    /**
     * RepostPresenter
     */
    private RepostContract.RepostPresenter mRepostPresenter;
    /**
     * SharedPreferenceInfo
     */
    private SharedPreferenceInfo mPreferenceInfo;
    /**
     * 被转发微博结构体
     */
    private Status mStatus;
    /**
     * ActivityRepostBinding
     */
    private ActivityRepostBinding binding;
    /**
     * 转发并评论
     */
    private boolean isComment = false;

    public static void actionStart(Context context, Status status) {
        Intent intent = new Intent(context, RepostActivity.class);
        intent.putExtra(DATA_NAME, status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = getIntent().getParcelableExtra(DATA_NAME);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repost);
        binding.setStatus(mStatus);

        new RepostPresenter(this, this);
        mRepostPresenter.start();

        mPreferenceInfo = new SharedPreferenceInfo(this);

        initialize();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 初始化界面
     */
    private void initialize() {
        // set toolbar
        setSupportActionBar(binding.acRepostToolbar);
        getSupportActionBar().setTitle(R.string.activity_repost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSubTitle();
        // 获取是否评论属性值
        isComment = binding.acRepostComment.isChecked();
        binding.acRepostComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isComment = isChecked;
            }
        });
        // set weibo card
        if (mStatus.getRetweeted_status() != null) {
            if (mStatus.getRetweeted_status().getPic_urls() != null &&
                    !mStatus.getRetweeted_status().getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(mStatus.getRetweeted_status().getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) binding.
                        acRepostWeiboSummaryCard.findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = mStatus.getRetweeted_status().getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) binding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            }
            binding.acRepostWeiboSummaryCard.setTitle(mStatus.getRetweeted_status().getUser().getScreen_name());
            binding.acRepostWeiboSummaryCard.setContent(mStatus.getRetweeted_status().getText());
            // 设置转发评论
            binding.acRepostText.setText(
                    TextUtil.convertText(
                            this,
                            "//" + mStatus.getText(),
                            ContextCompat.getColor(this, R.color.material_blue_700),
                            (int) binding.acRepostText.getTextSize()
                    )
            );
        } else {
            if (mStatus.getPic_urls() != null && !mStatus.getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(mStatus.getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) binding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = mStatus.getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) binding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            }
            binding.acRepostWeiboSummaryCard.setTitle(mStatus.getUser().getScreen_name());
            binding.acRepostWeiboSummaryCard.setContent(mStatus.getText());
        }

    }

    /**
     * 显示图片
     *
     * @param picUrl    图片连接
     * @param imageView 显示的 ImageView
     */
    private void showPic(String picUrl, AppCompatImageView imageView) {
        Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.pic_bg)
                .placeholder(R.drawable.pic_bg)
                .into(imageView);
    }

    /**
     * 转换URL
     *
     * @param thumbnailUrl 缩略图　URL
     * @return 高清图 URL
     */
    private String replaceUrl(String thumbnailUrl) {
        return thumbnailUrl.replace("thumbnail", "bmiddle");
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
                mRepostPresenter.repost(
                            mStatus.getId(),
                        binding.acRepostText.getText().toString(),
                            isComment ? StatusesAPI.COMMENTS_NONE : StatusesAPI.COMMENTS_BOTH,
                            null
                    );
                break;
        }
        return true;
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
