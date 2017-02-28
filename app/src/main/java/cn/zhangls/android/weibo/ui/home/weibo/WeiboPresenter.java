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

package cn.zhangls.android.weibo.ui.home.weibo;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.utils.ToastUtil;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

class WeiboPresenter extends ParentPresenter<WeiboContract.WeiboView> implements WeiboContract.Presenter {

    /**
     * Stream type.
     */
    private static final int streamType = AudioManager.STREAM_MUSIC;
    /**
     * 每次获取的微博数
     */
    private static int WEIBO_COUNT = 50;
    /**
     * 获取的微博页数
     *  eg:page = 1,获取的是第一页的微博
     */
    private static int WEIBO_PAGE = 1;
    /**
     * 声音池
     */
    private SoundPool sounds;
    /**
     * 新微博提示音
     */
    private int newBlogToast;
    /**
     * 声音是否加载
     */
    private boolean loaded = false;
    /**
     * AudioManager
     */
    private AudioManager audioManager;
    /**
     * 微博接口方法对象
     */
    private StatusesAPI mStatusesAPI;
    /**
     * 数据结构体
     */
    private StatusList mStatusList;
    /**
     * 音量
     */
    private float volume;

    WeiboPresenter(Context context, @NonNull WeiboContract.WeiboView subView) {
        super(context, subView);
        createSoundPool();
    }


    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
    }

    /**
     * 刷新微博
     *
     * @param weiboListType 微博列表类型
     */
    @Override
    public void requestTimeline(WeiboFragment.WeiboListType weiboListType) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        mSubView.onWeiboRefresh();
        switch (weiboListType) {
            case FRIEND:
                mStatusesAPI.friendsTimeline(getObserver(), 0, 0, WEIBO_COUNT, WEIBO_PAGE,
                        StatusesAPI.BASE_APP_ALL, StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
                break;
            case PUBLIC:
                mStatusesAPI.publicTimeline(getObserver(), WEIBO_COUNT, WEIBO_PAGE, StatusesAPI.BASE_APP_ALL);
                break;
            case MENTION:
                mStatusesAPI.mentions(getObserver(), 0, 0, WEIBO_COUNT, WEIBO_PAGE,
                        StatusesAPI.AUTHOR_FILTER_ALL, StatusesAPI.SRC_FILTER_ALL, StatusesAPI.TYPE_FILTER_ALL);
                break;
            case USER:
                mStatusesAPI.userTimeline(getObserver(), Long.parseLong(mAccessToken.getUid()), 0, 0, WEIBO_COUNT, WEIBO_PAGE,
                        0, StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
                break;
        }
    }

    /**
     * 获取 observer
     */
    private BaseObserver<StatusList> getObserver() {
        return new BaseObserver<StatusList>(mContext) {
            @Override
            public void onNext(StatusList value) {
                mStatusList = value;
            }

            @Override
            public void onComplete() {
                mSubView.refreshCompleted(mStatusList);
                mSubView.stopRefresh();
                playNewBlogToast();
            }
        };
    }

    /**
     * 创建SoundPool
     */
    protected void createSoundPool() {
        audioManager = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);
        // Volume[0, 1]
        this.volume = currentVolumeIndex / maxVolumeIndex;
//        mContext.setVolumeControlStream(streamType);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        newBlogToast = sounds.load(mContext, R.raw.newblogtoast, 1);
        sounds.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * 播放新微博提示音
     */
    private void playNewBlogToast() {
        if (loaded) {
            float leftVolume = volume;
            float rightVolume = volume;
            sounds.play(newBlogToast, leftVolume, rightVolume, 1, 0, 1f);
        }
    }
}