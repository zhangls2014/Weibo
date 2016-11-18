package cn.zhangls.android.weibo.ui.home.friend;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.model.User;

/**
 * Created by zhangls on 2016/10/31.
 * <p>
 * 好友列表
 */
public class FriendsListFragment extends Fragment {
    /**
     * 列表行数
     */
    private int mColumnCount = 1;
    /**
     * 用户信息类
     */
    private User mUser;

    /**
     * 构造方法
     */
    public FriendsListFragment() {
    }

    public static FriendsListFragment newInstance() {
        return new FriendsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter();
        }
        return view;
    }

}
