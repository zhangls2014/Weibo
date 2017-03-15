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

package cn.zhangls.android.weibo.ui.home.message;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseFragment;
import cn.zhangls.android.weibo.databinding.FragmentMessageBinding;
import cn.zhangls.android.weibo.ui.message.comment.CommentActivity;
import cn.zhangls.android.weibo.ui.message.mention.MentionActivity;

public class MessageFragment extends BaseFragment implements MessageContract.MessageView {

    /**
     * presenter 接口
     */
    private MessageContract.Presenter mMessagePresenter;
    /**
     * 数据
     */
    private ArrayList<MessageInfo> mMsgInfoList;
    /**
     * ViewDataBinding
     */
    private FragmentMessageBinding mBinding;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMessageBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    /**
     * 初始化方法
     */
    private void init() {
        new MessagePresenter(getContext(), this);
        mMessagePresenter.start();
        mMsgInfoList = new ArrayList<>();
        setMsgInfo();

        //设置RecyclerView
        MessageRecyclerAdapter adapter = new MessageRecyclerAdapter(getContext(), mMsgInfoList);
        adapter.setOnChildClickListener(new MessageRecyclerAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(RecyclerView recyclerView, View view, int position) {
                if (position == 0) {
                    MentionActivity.actionStart(getContext());
                } else if (position == 1) {
                    CommentActivity.actionStart(getContext());
                }
            }
        });
        mBinding.fgMessageRecycler.setAdapter(adapter);
        mBinding.fgMessageRecycler.addItemDecoration(
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL)
        );

        mBinding.fgMessageSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mMessagePresenter.getUnreadMsg();
                    }
                }
        );
        mBinding.fgMessageSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
    }

    private void setMsgInfo() {
        MessageInfo temp = new MessageInfo();
        temp.setAvatarUrl(String.valueOf(R.drawable.messagescenter_at));
        temp.setTitle("@我的");
        temp.setBody("");
        mMsgInfoList.add(temp);
        temp = new MessageInfo();
        temp.setAvatarUrl(String.valueOf(R.drawable.messagescenter_comments));
        temp.setTitle("评论");
        temp.setBody("");
        mMsgInfoList.add(temp);
        temp = new MessageInfo();
        temp.setAvatarUrl(String.valueOf(R.drawable.messagescenter_good));
        temp.setTitle("赞");
        temp.setBody("");
        mMsgInfoList.add(temp);
        temp = new MessageInfo();
        temp.setAvatarUrl(String.valueOf(R.drawable.messagescenter_messagebox));
        temp.setTitle("未关注人消息");
        temp.setBody("暂时没有收到消息");
        mMsgInfoList.add(temp);
        temp = new MessageInfo();
        temp.setAvatarUrl(String.valueOf(R.drawable.messagescenter_subscription));
        temp.setTitle("订阅消息");
        temp.setBody("暂时没有收到订阅消息");
        mMsgInfoList.add(temp);
    }

    /**
     * 加载初始化数据，该方法用于实现缓加载策略
     */
    @Override
    protected void loadData() {
        init();
    }

    /**
     * 设置 Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mMessagePresenter = presenter;
    }
}
