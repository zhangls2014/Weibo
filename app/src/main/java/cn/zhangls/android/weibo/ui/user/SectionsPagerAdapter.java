package cn.zhangls.android.weibo.ui.user;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.ui.home.message.MessageFragment;
import cn.zhangls.android.weibo.ui.home.weibo.WeiboFragment;

/**
 * A simple {@link Fragment} subclass.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 页数
     */
    private int length;

    SectionsPagerAdapter(Context context, FragmentManager fm, int length) {
        super(fm);
        this.context = context;
        this.length = length;
    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = MessageFragment.newInstance();
                break;
            case 1:
                fragment = WeiboFragment.newInstance();
                break;
            case 2:
                fragment = MessageFragment.newInstance();
                break;
            default:
                fragment = MessageFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch (position) {
            case 0:
                title = context.getString(R.string.user_title_home);
                break;
            case 1:
                title = context.getString(R.string.user_title_weibo);
                break;
            case 2:
                title = context.getString(R.string.user_title_album);
                break;
            default:
                title = context.getString(R.string.user_title_home);
                break;
        }
        return title;
    }
}
