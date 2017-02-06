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
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.GroupList;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

class WeiboPresenter implements WeiboContract.Presenter {

    private static final String TAG = "WeiboPresenter";
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
     * WeiboView 接口
     */
    private WeiboContract.WeiboView mWeiboView;
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * AccessToken 对象
     */
    private Oauth2AccessToken mAccessToken;
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
     * 音量
     */
    private float volume;

    WeiboPresenter(Context context, WeiboContract.WeiboView weiboView) {
        mWeiboView = weiboView;
        mContext = context;

        mAccessToken = AccessTokenKeeper.readAccessToken(context);

        mWeiboView.setPresenter(this);

        createSoundPool();
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
    }

    @Override
    public void requestFriendsTimeline() {
        mWeiboView.onWeiboRefresh();
        if (mAccessToken.isSessionValid()) {
            getFriendsTimeline(0, 0, WEIBO_COUNT, WEIBO_PAGE, StatusesAPI.BASE_APP_ALL,
                    StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
        } else {
            mWeiboView.stopRefresh();
        }
    }

    /**
     * 获取分组列表
     */
    @Override
    public void requestGroupList() {
        if (mAccessToken.isSessionValid()) {
            getGroupList();
        }
    }

    /**
     * 获取分组微博
     */
    @Override
    public void requestGroupTimeline() {
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

    /**
     * 获取最新的公共微博
     *
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    private void getPublicTimeline(int count, int page, int base_app) {
        Observer<StatusList> observer = new Observer<StatusList>() {

            @Override
            public void onError(Throwable e) {
                mWeiboView.stopRefresh();
                ToastUtil.showLongToast(mContext, "刷新出错，请重试");
            }

            @Override
            public void onComplete() {
                mWeiboView.stopRefresh();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StatusList publicTimelineHttpResult) {
                mWeiboView.refreshCompleted(publicTimelineHttpResult);
            }
        };

        mStatusesAPI.publicTimeline(observer, count, page, base_app);
    }

    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count 单页返回的记录条数，最大不超过100，默认为20。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     */
    private void getFriendsTimeline(long since_id, long max_id, int count,
                                    int page, int base_app, int feature, int trim_user) {
        Observer<StatusList> observer = new Observer<StatusList>() {
            @Override
            public void onError(Throwable e) {
                mWeiboView.stopRefresh();
                ToastUtil.showLongToast(mContext, "刷新出错，请重试");
            }

            @Override
            public void onComplete() {
                mWeiboView.stopRefresh();
                playNewBlogToast();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StatusList timelineHttpResult) {
                mWeiboView.refreshCompleted(timelineHttpResult);
            }
        };

        mStatusesAPI.friendsTimeline(observer, since_id, max_id, count, page, base_app,
                feature, trim_user);
    }

    /**
     * 获取当前登陆用户好友分组列表
     */
    private void getGroupList() {
        Observer<GroupList> observer = new Observer<GroupList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GroupList value) {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: getGroupList", e);
            }

            @Override
            public void onComplete() {

            }
        };
//        mStatusesAPI.groupList(observer);
    }

    /**
     * 获取当前登录用户某一好友分组的微博列表。
     *
     * @param list_id      需要查询的好友分组ID，建议使用返回值里的idstr，当查询的为私有分组时，则当前登录用户必须为其所有者
     * @param since_id     若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id       若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count        单页返回的记录条数，默认为50
     * @param page         返回结果的页码，默认为1
     * @param base_app     是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param feature      过滤类型ID，0：全部，1：原创， 2：图片，3：视频，4：音乐
     */
    private void getGroupTimeline(long list_id, long since_id, long max_id, int count, int page,
                                  int base_app, int feature) {
        Observer<StatusList> observer = new Observer<StatusList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StatusList value) {
                mWeiboView.refreshCompleted(value);
            }

            @Override
            public void onError(Throwable e) {
                mWeiboView.stopRefresh();
                ToastUtil.showLongToast(mContext, "刷新出错，请重试");
            }

            @Override
            public void onComplete() {
                mWeiboView.stopRefresh();
                playNewBlogToast();
            }
        };
//        mStatusesAPI.getGroupTimeline(observer, list_id, since_id, max_id,
//                count, page, base_app, feature);
    }
}