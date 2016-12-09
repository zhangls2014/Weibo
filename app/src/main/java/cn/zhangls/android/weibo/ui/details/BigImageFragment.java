package cn.zhangls.android.weibo.ui.details;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.utils.ToastUtil;

public class BigImageFragment extends Fragment implements BigImageContract.BigImageView {

    /**
     * Presenter
     */
    private BigImageContract.Presenter mPresenter;
    /**
     * 图片地址
     */
    private List<String> mPicUrls;
    /**
     * 当前显示图片
     */
    private int mCurrentPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicUrls = getArguments().getStringArrayList(BigImageActivity.PIC_URLS);
        mCurrentPic = getArguments().getInt(BigImageActivity.CURRENT_PIC, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_big_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.fg_big_image_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), mPicUrls));
        mViewPager.setCurrentItem(mCurrentPic, true);
        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(getActivity(), "你点击了第" + mViewPager.getCurrentItem() + "张图片");
            }
        });
        mViewPager.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.showShortToast(getActivity(), "你长按了第" + mViewPager.getCurrentItem() + "张图片");
                return true;
            }
        });

        new BigImagePresenter(getActivity(), this);

    }

    /**
     * 保存图片到相册
     */
    @Override
    public void saveImage() {

    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(BigImageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
