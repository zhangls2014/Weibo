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


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.zhangls.android.weibo.R;

public class BigImageFragment extends Fragment implements BigImageContract.BigImageView {

    /**
     * Presenter
     */
    private BigImageContract.Presenter mPresenter;
    /**
     * 图片地址
     */
    private List<String> mPicUrls;
    /**
     * 当前显示图片
     */
    private int mCurrentPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPic = getArguments().getInt(BigImageActivity.CURRENT_PIC, 0);
        mPicUrls = getArguments().getStringArrayList(BigImageActivity.PIC_URLS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_big_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.fg_big_image_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), mPicUrls));
        mViewPager.setCurrentItem(mCurrentPic, true);

        new BigImagePresenter(getActivity(), this);
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
