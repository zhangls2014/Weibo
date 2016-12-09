package cn.zhangls.android.weibo.ui.details;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;

import java.util.List;

/**
 * Created by zhangls on 2016/11/22.
 *
 */

class ImagePagerAdapter extends PagerAdapter {

    /**
     * 上下文对象
     */
    private Context context;
    /**
     * Image URL
     */
    private List<String> imageUrls;

    ImagePagerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BigImageView bigImageView = new BigImageView(context);
        bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.showImage(
                Uri.parse(imageUrls.get(position).replace("thumbnail", "bmiddle")),
                Uri.parse(imageUrls.get(position).replace("thumbnail", "large"))
        );
        container.addView(bigImageView);
        return bigImageView;
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param object    The same object that was returned by
     *                  {@link #instantiateItem(View, int)}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
