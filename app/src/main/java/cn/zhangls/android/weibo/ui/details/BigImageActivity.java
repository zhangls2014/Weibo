/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package cn.zhangls.android.weibo.ui.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;

import cn.zhangls.android.weibo.R;

public class BigImageActivity extends AppCompatActivity {

    public static final String PIC_URLS = "pic_urls";
    public static final String CURRENT_PIC = "current_pic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        // 初始化 BigImageViewer
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));

        BigImageFragment bigImageFragment = new BigImageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PIC_URLS, getIntent().getStringArrayListExtra(PIC_URLS));
        bundle.putInt(CURRENT_PIC, getIntent().getIntExtra(CURRENT_PIC, 0));
        bigImageFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.activity_big_image, bigImageFragment, "big_image")
                .commit();
    }
}
