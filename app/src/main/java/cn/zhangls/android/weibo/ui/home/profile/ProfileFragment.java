package cn.zhangls.android.weibo.ui.home.profile;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.databinding.FragmentProfileBinding;
import cn.zhangls.android.weibo.network.model.User;

/**
 * 登录用户信息
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {

    /**
     * Fragment 是否可见
     */
    private boolean isVisible;
    /**
     * 是否加载过数据标识符
     */
    private boolean isLoaded = true;
    /**
     * Presenter
     */
    private ProfileContract.Presenter mPresenter;
    /**
     * DataBinding 视图
     */
    private FragmentProfileBinding binding;

    /**
     * 构造方法
     */
    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    /**
     * 实现缓加载策略
     *
     * @param isVisibleToUser 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = getUserVisibleHint();
        //可见且未创建视图时，加载数据
        if (isVisible && !isLoaded) {
            getData();
            isLoaded = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //如果Fragment可见，则加载数据
        if (isVisible) {
            getData();
            isLoaded = true;
        } else {
            isLoaded = false;
        }
    }

    /**
     * 加载数据
     */
    private void getData() {
        //实例化ProfilePresenter
        new ProfilePresenter(this, getContext());
        //获取用户信息
        mPresenter.getUser();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 加载数据
     */
    @Override
    public void loadData(User user) {
        binding.setUser(user);
        //设置圆形图片
        Glide.with(getActivity())
                .load(binding.getUser().getAvatar_large())
                .centerCrop()
                .placeholder(R.drawable.avator_default)
                .dontAnimate()
                .into(binding.civFgProfileAvatar);
    }
}
