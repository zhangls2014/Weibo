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

package cn.zhangls.android.weibo.ui.home.find;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;
import cn.zhangls.android.weibo.databinding.FragmentFindBinding;
import cn.zhangls.android.weibo.ui.weibo.WeiboFragment;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class FindFragment extends BaseFragment implements FindContract.FindView {

    /**
     * FindPresenter
     */
    private FindContract.Presenter mFindPresenter;
    /**
     * FragmentFindBinding
     */
    private FragmentFindBinding mBinding;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_find, container, false);
        return mBinding.getRoot();
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    @Override
    protected void loadData() {
        initialize();
    }

    private void initialize() {
        new FindPresenter(getContext().getApplicationContext(), FindFragment.this);
        mFindPresenter.start();

        // 添加 WeiboFragment
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fg_find_hot_favorite_frame, WeiboFragment.newInstance(WeiboFragment.WeiboListType.FAVORITE))
                .commit();
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(FindContract.Presenter presenter) {
        mFindPresenter = presenter;
    }
}
