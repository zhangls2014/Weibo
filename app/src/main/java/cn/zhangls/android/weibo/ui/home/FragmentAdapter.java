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

package cn.zhangls.android.weibo.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.zhangls.android.weibo.ui.home.message.MessageFragment;
import cn.zhangls.android.weibo.ui.home.weibo.WeiboFragment;

/**
 * Created by zhangls on 2016/6/10.
 *
 */
class FragmentAdapter extends FragmentPagerAdapter {
    private int length;

    FragmentAdapter(FragmentManager fm, int length) {
        super(fm);
        this.length = length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.FRIEND);
                break;
            case 1:
                fragment = MessageFragment.newInstance();
                break;
            case 2:
                fragment = MessageFragment.newInstance();
                break;
            case 3:
                fragment = MessageFragment.newInstance();
                break;
            default:
                fragment = WeiboFragment.newInstance(WeiboFragment.WeiboListType.FRIEND);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return length;
    }
}
