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

package cn.zhangls.android.weibo.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.common.BaseActivity;
import cn.zhangls.android.weibo.databinding.ActivityEditBinding;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.ui.edit.share.ShareActivity;
import cn.zhangls.android.weibo.utils.SharedPreferenceInfo;
import cn.zhangls.android.weibo.utils.TextUtil;

public class EditActivity extends BaseActivity implements EditContract.EditView {

    private final static String DATA_NAME = "data_name";
    private final static String TYPE_CONTENT = "type_content";
    private final static String COMMENT_INFO = "comment_info";
    /**
     * start ShareActivity request code
     */
    private final static int REQUEST_CODE_SHARE = 1;
    /**
     * start ShareActivity result code
     */
    private final static int RESULT_CODE_SHARE = 1;
    /**
     * EditActivity 内容类型：0：转发微博、1：回复评论、2：发表评论
     */
    public final static int TYPE_CONTENT_REPOST = 0;
    public final static int TYPE_CONTENT_REPLY = 1;
    public final static int TYPE_CONTENT_COMMENT = 2;
    /**
     * EditPresenter
     */
    private EditContract.EditPresenter mEditPresenter;
    /**
     * SharedPreferenceInfo
     */
    private SharedPreferenceInfo mPreferenceInfo;
    /**
     * 微博结构体
     */
    private Status mStatus;
    /**
     * EditActivity 编辑的文本类型
     */
    private int mContentType;
    /**
     * Comment
     */
    private Comment mComment;
    /**
     * ActivityRepostBinding
     */
    private ActivityEditBinding mBinding;
    /**
     * 转发并评论、评论并转发
     */
    private boolean isCommentRepost = false;
    /**
     * Topic 集合
     */
    ArrayList<TextUtil.StrHolder> mTopicList;
    /**
     * Name 集合
     */
    ArrayList<TextUtil.StrHolder> mNameList;

    /**
     * 唯一构造方法
     *
     * @param context     上下文对象
     * @param status      微博信息
     * @param contentType EditActivity 内容类型
     * @param comment     评论
     */
    public static void actionStart(Context context, Status status, int contentType, Comment comment) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(DATA_NAME, status);
        intent.putExtra(TYPE_CONTENT, contentType);
        intent.putExtra(COMMENT_INFO, comment);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = getIntent().getParcelableExtra(DATA_NAME);
        mContentType = getIntent().getIntExtra(TYPE_CONTENT, 0);
        mComment = getIntent().getParcelableExtra(COMMENT_INFO);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        mBinding.setStatus(mStatus);

        new EditPresenter(this, this);
        mEditPresenter.start();

        mPreferenceInfo = new SharedPreferenceInfo(this);

        initialize();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 初始化界面
     */
    private void initialize() {
        // set toolbar
        setSupportActionBar(mBinding.acEditToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSubTitle();
        // 获取"是否评论"属性值
        isCommentRepost = mBinding.acEditCommentRepost.isChecked();
        mBinding.acEditCommentRepost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCommentRepost = isChecked;
            }
        });
        switch (mContentType) {
            case TYPE_CONTENT_REPOST:
                mBinding.acEditCommentRepost.setText(R.string.ac_edit_comment);
                getSupportActionBar().setTitle(R.string.activity_repost);
                mBinding.acRepostWeiboSummaryCard.setVisibility(View.VISIBLE);
                mBinding.acEditWeiboVisible.setVisibility(View.VISIBLE);
                mBinding.acEditText.setHint(getString(R.string.ac_edit_edit_text_repost_hint));
                if (mComment == null) {
                    setText("//@" + mStatus.getUser().getScreen_name() + ":" + mStatus.getText(), false);
                } else {
                    setText("//@" + mComment.getUser().getScreen_name() + ":" + mComment.getText(), true);
                }
                // 显示微博的可见性
                setGroup();
                // 显示转发微博信息
                setWeiboCard();
                break;
            case TYPE_CONTENT_REPLY:
                mBinding.acEditCommentRepost.setText(R.string.ac_edit_repost);
                getSupportActionBar().setTitle(R.string.activity_reply);
                mBinding.acRepostWeiboSummaryCard.setVisibility(View.GONE);
                mBinding.acEditWeiboVisible.setVisibility(View.GONE);
                if (mComment != null) {
                    mBinding.acEditText.setHint(getString(R.string.ac_edit_edit_text_reply_hint) + "@" + mComment.getUser().getScreen_name());
                }
                break;
            case TYPE_CONTENT_COMMENT:
                mBinding.acEditCommentRepost.setText(R.string.ac_edit_repost);
                getSupportActionBar().setTitle(R.string.activity_comment);
                mBinding.acRepostWeiboSummaryCard.setVisibility(View.GONE);
                mBinding.acEditWeiboVisible.setVisibility(View.GONE);
                mBinding.acEditText.setHint(getString(R.string.ac_edit_edit_text_comment_hint));
                break;
        }
    }

    /**
     * 设置微博内容提要
     */
    private void setWeiboCard() {
        if (mStatus.getRetweeted_status() != null) {
            if (mStatus.getRetweeted_status().getPic_urls() != null &&
                    !mStatus.getRetweeted_status().getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(mStatus.getRetweeted_status().getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) mBinding.
                        acRepostWeiboSummaryCard.findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = mStatus.getRetweeted_status().getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) mBinding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            }
            mBinding.acRepostWeiboSummaryCard.setTitle(mStatus.getRetweeted_status().getUser().getScreen_name());
            mBinding.acRepostWeiboSummaryCard.setContent(mStatus.getRetweeted_status().getText());
        } else {
            if (mStatus.getPic_urls() != null && !mStatus.getPic_urls().isEmpty()) {
                // 将缩略图 url 转换成高清图 url
                String url = replaceUrl(mStatus.getPic_urls().get(0).getThumbnail_pic());
                showPic(url, (AppCompatImageView) mBinding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            } else {
                // 将缩略图 url 转换成高清图 url
                String url = mStatus.getUser().getProfile_image_url();
                showPic(url, (AppCompatImageView) mBinding.acRepostWeiboSummaryCard
                        .findViewById(R.id.item_summary_picture));
            }
            mBinding.acRepostWeiboSummaryCard.setTitle(mStatus.getUser().getScreen_name());
            mBinding.acRepostWeiboSummaryCard.setContent(mStatus.getText());
        }
    }

    /**
     * 设置转发文字
     */
    private void setText(String text, boolean isComment) {
        if (isComment) {
            mBinding.acEditText.setText(
                    TextUtil.convertText(
                            this,
                            text,
                            ContextCompat.getColor(this, R.color.material_blue_700),
                            (int) mBinding.acEditText.getTextSize()
                    )
            );
            return;
        }

        if (mStatus.getRetweeted_status() != null) // 设置转发评论
            mBinding.acEditText.setText(
                    TextUtil.convertText(
                            this,
                            text,
                            ContextCompat.getColor(this, R.color.material_blue_700),
                            (int) mBinding.acEditText.getTextSize()
                    )
            );
        // 获取转发内容中的话题、用户名集合
        mTopicList = TextUtil.findRegexString(mBinding.acEditText.getText().toString(),
                Constants.RegularExpression.TopicRegex);
        mNameList = TextUtil.findRegexString(mBinding.acEditText.getText().toString(),
                Constants.RegularExpression.NameRegex);

        // 软键盘输入事件监听，实现对 UserName 和 Topic 删除时全部选中
        mBinding.acEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 放删除键按下时
                return keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN
                        && (setSelection(mBinding.acEditText, mTopicList, true)
                        || setSelection(mBinding.acEditText, mNameList, true));
            }
        });
        // EditText 点击事件监听，实现对 UserName 和 Topic 点击，光标位于 UserName 和 Topic 之后
        mBinding.acEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果点击的不是 Topic，则对 Name 进行检测
                boolean topic = setSelection(mBinding.acEditText, mTopicList, false);
                if (!topic) {
                    setSelection(mBinding.acEditText, mNameList, false);
                }
            }
        });
    }

    /**
     * 对光标进行设置
     *
     * @param editText   EditText 对象
     * @param list       文本中包含的符合匹配规则的字符串集合
     * @param isSelected 是否是为了选中文字
     * @return 是否是点击的匹配文字
     */
    private boolean setSelection(AppCompatEditText editText, ArrayList<TextUtil.StrHolder> list, boolean isSelected) {
        int selectionStart = editText.getSelectionStart();
        // 如果没有查找到匹配的规则，则返回
        if (list == null || list.size() <= 0) {
            return false;
        }
        // 如果光标起始和结束在不同一位置,说明是选中效果
        if (editText.getSelectionStart() != editText.getSelectionEnd() && isSelected) {
            return false;
        }
        int lastPos = 0;
        for (TextUtil.StrHolder strHolder : list) {
            lastPos = editText.getText().toString().indexOf(strHolder.getName(), lastPos);
            if (lastPos != -1) {
                if (selectionStart > lastPos
                        && selectionStart <= lastPos + strHolder.getName().length()) {
                    if (isSelected) {
                        editText.setSelection(lastPos, lastPos + strHolder.getName().length());
                    } else {
                        editText.setSelection(lastPos + strHolder.getName().length());
                    }
                    return true;
                } else {
                    lastPos = lastPos + strHolder.getName().length();
                }
            }
        }
        return false;
    }

    /**
     * 设置微博可见性按钮
     */
    private void setGroup() {
        mBinding.acEditWeiboVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareActivity.actionStart(EditActivity.this, REQUEST_CODE_SHARE);
            }
        });
    }

    /**
     * 显示图片
     *
     * @param picUrl    图片连接
     * @param imageView 显示的 ImageView
     */
    private void showPic(String picUrl, AppCompatImageView imageView) {
        Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.pic_bg)
                .placeholder(R.drawable.pic_bg)
                .into(imageView);
    }

    /**
     * 转换URL
     *
     * @param thumbnailUrl 缩略图　URL
     * @return 高清图 URL
     */
    private String replaceUrl(String thumbnailUrl) {
        return thumbnailUrl.replace("thumbnail", "bmiddle");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ac_repost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.ac_repost_menu_send:
                submit();
                break;
        }
        return true;
    }

    /**
     * 点击发送按钮的处理逻辑
     */
    private void submit() {
        switch (mContentType) {
            case TYPE_CONTENT_REPOST:
                mEditPresenter.repost(
                        mStatus.getId(),
                        mBinding.acEditText.getText().toString(),
                        isCommentRepost ? StatusesAPI.COMMENTS_NONE : StatusesAPI.COMMENTS_BOTH,
                        null
                );
                break;
            case TYPE_CONTENT_REPLY:
                mEditPresenter.reply(
                        mComment.getId(),
                        mStatus.getId(),
                        mBinding.acEditText.getText().toString(),
                        0,
                        0
                );
                break;
            case TYPE_CONTENT_COMMENT:
                mEditPresenter.create(
                        mBinding.acEditText.getText().toString(),
                        mStatus.getId(),
                        0
                );
                break;
        }
    }

    @Override
    public void setSubTitle() {
        String userName = mPreferenceInfo.getUserName();
        if (userName != null && !userName.isEmpty()) {
            getSupportActionBar().setSubtitle(userName);
        } else {
            mEditPresenter.getUserByService();
        }
    }

    /**
     * 提交完成
     */
    @Override
    public void submitCompleted() {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SHARE && resultCode == RESULT_CODE_SHARE) {

        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return 是否支持滑动返回
     */
    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    @Override
    public void setPresenter(EditContract.EditPresenter presenter) {
        mEditPresenter = presenter;
    }
}
