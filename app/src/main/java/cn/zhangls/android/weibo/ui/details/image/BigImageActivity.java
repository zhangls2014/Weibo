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

package cn.zhangls.android.weibo.ui.details.image;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;

public class BigImageActivity extends BaseActivity implements BigImageContract.BigImageView {

    /**
     * 图片地址
     */
    public static final String PIC_URLS = "pic_urls";
    /**
     * 当前显示图片
     */
    public static final String CURRENT_PIC = "current_pic";
    /**
     * Presenter
     */
    private BigImageContract.Presenter mPresenter;

    /**
     * startActivity 方法
     *
     * @param activity   上下文对象
     * @param picUrls    图片的 URL
     * @param currentPic 当前图片
     */
    public static void actionStart(Activity activity, View sharedView, String sharedName, ArrayList<String> picUrls, int currentPic) {
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, sharedView, sharedName);
        Intent intent = new Intent(activity, BigImageActivity.class);
        intent.putStringArrayListExtra(PIC_URLS, picUrls);
        intent.putExtra(CURRENT_PIC, currentPic);
        activity.startActivity(intent, activityOptions.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        // 初始化 BigImageViewer
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        // 获取 Intent 传递的数据
        int currentPic = getIntent().getIntExtra(BigImageActivity.CURRENT_PIC, 0);
        List<String> picUrls = getIntent().getStringArrayListExtra(BigImageActivity.PIC_URLS);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.fg_big_image_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ImagePagerAdapter(this, picUrls));
        mViewPager.setCurrentItem(currentPic, true);

        new BigImagePresenter(this, this);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    /**
     * 保存图片到相册
     */
    @Override
    public void saveImage() {

    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(BigImageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
