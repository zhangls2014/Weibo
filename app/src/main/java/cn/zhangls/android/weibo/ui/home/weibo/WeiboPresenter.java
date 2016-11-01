package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.network.HttpMethods;
import cn.zhangls.android.weibo.network.model.HttpResult;
import cn.zhangls.android.weibo.network.model.Timeline;
import cn.zhangls.android.weibo.utils.ToastUtil;
import rx.Subscriber;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

class WeiboPresenter implements WeiboContract.Presenter {
    /**
     * WeiboView 接口
     */
    private WeiboContract.WeiboView mWeiboView;
    /**
     * 上下文对象
     */
    private Context mContext;
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
     * AccessToken 对象
     */
    private Oauth2AccessToken mAccessToken;

    WeiboPresenter(Context context, @NonNull WeiboContract.WeiboView weiboView) {
        mWeiboView = weiboView;
        mContext = context;

        mAccessToken = AccessTokenKeeper.readAccessToken(context);

        mWeiboView.setPresenter(this);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {

    }

    @Override
    public void getTimeline() {
        mWeiboView.onWeiboRefresh();
        if (mAccessToken != null && mAccessToken.isSessionValid()) {
            getFriendsTimeline(mAccessToken.getToken(), 0, 0, WEIBO_COUNT, WEIBO_PAGE, 0, 0, 0);
        } else {
            mWeiboView.stopRefresh();
        }
    }

    /**
     * fab 点击事件
     */
    @Override
    public void fabClick() {
        ++WEIBO_PAGE;
        getTimeline();
    }

    /**
     * 获取最新的公共微博
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    private void getPublicTimeline(String access_token, int count, int page, int base_app) {
        Subscriber<HttpResult<Timeline>> subscriber = new Subscriber<HttpResult<Timeline>>() {
            @Override
            public void onCompleted() {
                mWeiboView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                mWeiboView.stopRefresh();
                ToastUtil.showLongToast(mContext, "刷新出错，请重试");
            }

            @Override
            public void onNext(HttpResult<Timeline> publicTimelineHttpResult) {
                mWeiboView.refreshCompleted(publicTimelineHttpResult);
            }
        };

        HttpMethods.getInstance().getPublicTimeline(subscriber, access_token, count, page, base_app);
    }

    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param access_token 用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count 单页返回的记录条数，最大不超过100，默认为20。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     */
    private void getFriendsTimeline(@NonNull String access_token, long since_id, long max_id, int count,
                                   int page, int base_app, int feature, int trim_user) {
        Subscriber<HttpResult<Timeline>> subscriber = new Subscriber<HttpResult<Timeline>>() {
            @Override
            public void onCompleted() {
                mWeiboView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                mWeiboView.stopRefresh();
                ToastUtil.showLongToast(mContext, "刷新出错，请重试");
            }

            @Override
            public void onNext(HttpResult<Timeline> timelineHttpResult) {
                mWeiboView.refreshCompleted(timelineHttpResult);
            }
        };

        HttpMethods.getInstance().getFriendsTimeline(subscriber, access_token, since_id, max_id,
                count, page, base_app, feature, trim_user);
    }
}
