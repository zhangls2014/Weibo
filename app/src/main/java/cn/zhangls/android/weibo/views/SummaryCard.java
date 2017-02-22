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

package cn.zhangls.android.weibo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cn.zhangls.android.weibo.R;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/1/4.
 * <p>
 * 内容提要卡片
 */

public class SummaryCard extends LinearLayoutCompat {

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    /**
     * avatar
     */
    private AppCompatImageView mAvatar;
    /**
     * title
     */
    private AppCompatTextView mTitle;
    /**
     * content
     */
    private AppCompatTextView mContent;

    public SummaryCard(Context context) {
        this(context, null);
    }

    public SummaryCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummaryCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * init func
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.summary_card, this);

        findViews();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SummaryCard,
                defStyleAttr, 0);

        getAttrs(a);
    }

    /**
     * find views by id
     */
    private void findViews() {
        mAvatar = (AppCompatImageView) findViewById(R.id.item_summary_picture);
        mTitle = (AppCompatTextView) findViewById(R.id.item_summary_title);
        mContent = (AppCompatTextView) findViewById(R.id.item_summary_content);
    }

    /**
     * set custom attr
     *
     * @param a TypedArray
     */
    private void getAttrs(TypedArray a) {
        // 图片 ID
        int picResId = -1;
        int picScaleIndex = -1;
        String title = "";
        String content = "";
        try {
            picResId = a.getResourceId(R.styleable.SummaryCard_srcCompat, -1);
            picScaleIndex = a.getInt(R.styleable.SummaryCard_android_scaleType, -1);
            title = a.getString(R.styleable.SummaryCard_title);
            content = a.getString(R.styleable.SummaryCard_content);
        } finally {
            a.recycle();
        }

        if (picResId != -1) {
            setImageResource(picResId);
        }
        if (picScaleIndex != -1) {
            setScaleType(sScaleTypeArray[picScaleIndex]);
        }
        if (title != null && !title.isEmpty()) {
            mTitle.setText(title);
        } else {
            mTitle.setVisibility(View.GONE);
        }
        if (content != null && !content.isEmpty()) {
            mContent.setText(content);
        }
    }

    /**
     * Sets a drawable as the content of this ImageView.
     * <p>
     * <p>Allows the use of vector drawables when running on older versions of the platform.</p>
     *
     * @param resId the resource identifier of the drawable
     */
    public void setImageResource(@DrawableRes int resId) {
        if (resId != -1) {
            mAvatar.setImageResource(resId);
        }
    }

    /**
     * Controls how the image should be resized or moved to match the size
     * of this ImageView.
     *
     * @param scaleType The desired scaling mode.
     */
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }
        mAvatar.setScaleType(scaleType);
    }

    /**
     * set title
     *
     * @param title title
     */
    public void setTitle(CharSequence title) {
        if (mTitle.getVisibility() == View.GONE) {
            mTitle.setVisibility(View.VISIBLE);
        }
        mTitle.setText(title);
    }

    /**
     * set content
     *
     * @param content content
     */
    public void setContent(CharSequence content) {
        mContent.setText(content);
    }

}
