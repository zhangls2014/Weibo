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
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.ui.setting.SettingsActivity;

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
        mPresenter.start();
        //获取用户信息
        mPresenter.getUser();
        // 设置按键监听
        setListeners();
    }

    /**
     * 设置按键监听
     */
    private void setListeners() {
        binding.fgMeBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.actionStart(getContext());
            }
        });
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
