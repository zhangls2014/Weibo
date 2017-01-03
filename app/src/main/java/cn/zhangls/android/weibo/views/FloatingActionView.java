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
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cn.zhangls.android.weibo.R;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/1/2.
 *
 * 自定义组件，可在图片上添加任意组件
 */

public class FloatingActionView extends FrameLayout {

    private static final String TAG = "FloatingActionView";

    /**
     * 显示的背景图片
     */
    private AppCompatImageView mPhoto;

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

    public FloatingActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.floating_action_view, this);

        findView();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionView,
                0, 0);
        // 图片 ID
        int picResId = -1;
        int picScaleIndex = -1;
        try {
            picResId = a.getResourceId(R.styleable.FloatingActionView_srcCompat, -1);
            picScaleIndex = a.getInt(R.styleable.FloatingActionView_android_scaleType, -1);
        } finally {
            a.recycle();
        }

        if (picResId != -1) {
            setImageResource(picResId);
        }
        if (picScaleIndex != -1) {
            setScaleType(sScaleTypeArray[picScaleIndex]);
        }
    }

    private void findView() {
        mPhoto = (AppCompatImageView) findViewById(R.id.item_image_view);
    }

    public void setOnClickListener(OnClickListener l) {
        Log.i(TAG, "setOnClickListener: you clicked floating action view");
        mPhoto.setOnClickListener(l);
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        Log.i(TAG, "setOnLongClickListener: you long clicked floating action view");
        mPhoto.setOnLongClickListener(l);
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
            mPhoto.setImageResource(resId);
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
        mPhoto.setScaleType(scaleType);
    }
}
