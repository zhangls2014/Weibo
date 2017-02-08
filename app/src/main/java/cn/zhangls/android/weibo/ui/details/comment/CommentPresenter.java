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

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.Status;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/7.
 */

class CommentPresenter extends ParentPresenter<CommentContract.CommentView> implements CommentContract.Presenter {

    /**
     * StatusesAPI
     */
    private StatusesAPI mStatusesAPI;
    /**
     * Status
     */
    private Status mStatus;


    CommentPresenter(Context context, @NonNull CommentContract.CommentView subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
    }

    /**
     * 获取微博正文
     *
     * @param id 微博ID
     */
    @Override
    public void getStatus(long id) {
        if (!mAccessToken.isSessionValid()) {
            return;
        }

        BaseObserver<Status> observer = new BaseObserver<Status>(mContext) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSubView.stopRefresh();
            }

            @Override
            public void onComplete() {
                mSubView.showContent(mStatus);
                mSubView.stopRefresh();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Status value) {
                mStatus = value;
            }
        };

        mStatusesAPI.show(observer, id);
    }
}
