package cn.zhangls.android.weibo.ui.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;

import cn.zhangls.android.weibo.R;

public class BigImageActivity extends AppCompatActivity {

    public static final String PIC_URLS = "pic_urls";
    public static final String CURRENT_PIC = "current_pic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        // 初始化 BigImageViewer
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));

        BigImageFragment bigImageFragment = new BigImageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PIC_URLS, getIntent().getStringArrayListExtra(PIC_URLS));
        bundle.putInt(CURRENT_PIC, getIntent().getIntExtra(CURRENT_PIC, 0));
        bigImageFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.activity_big_image, bigImageFragment, "big_image")
                .commit();
    }
}
