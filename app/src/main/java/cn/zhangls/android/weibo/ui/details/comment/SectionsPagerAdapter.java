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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/11.
 * <p>
 * FragmentPagerAdapter 实体类
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {
    /**
     * 数据源
     */
    private ArrayList<String> mTitleList;

    private Object mCurrentFragment;

    SectionsPagerAdapter(FragmentManager fm, ArrayList<String> titleList) {
        super(fm);
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return CommentFragment.newInstance();
    }

    @Override
    public int getCount() {
        if (mTitleList != null) {
            return mTitleList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mCurrentFragment = super.instantiateItem(container, position);
        return mCurrentFragment;
    }

    public Object getCurrentFragment() {
        return mCurrentFragment;
    }
}
