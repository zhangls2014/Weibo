package cn.zhangls.android.weibo.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by zhangls on 2016/10/28.
 *
 */

public class ActivityUtil {

    /**
     * 向Activity添加Fragment
     *
     * @param fragmentManager FragmentManager
     * @param fragment 需要添加的Fragment
     * @param frameId 添加Fragment的容器Id
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}