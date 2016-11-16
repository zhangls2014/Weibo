package cn.zhangls.android.weibo.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.zhangls.android.weibo.ui.home.message.MessageFragment;
import cn.zhangls.android.weibo.ui.home.profile.ProfileFragment;
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
                fragment = WeiboFragment.newInstance();
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
            case 4:
                fragment = ProfileFragment.newInstance();
                break;
            default:
                fragment = WeiboFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return length;
    }
}
