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

package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zhangls.android.weibo.Constants;
import cn.zhangls.android.weibo.R;
import cn.zhangls.android.weibo.TextClickableSpan;

/**
 * Created by zhangls on 2016/11/8.
 * <p>
 * 对文字进行处理
 */

public class TextUtil {

    /**
     * 点击文本内容的类型：用户名
     */
    public static final int CLICK_TYPE_USER_NAME = 1;
    /**
     * 点击文本内容的类型：话题
     */
    public static final int CLICK_TYPE_TOPIC = 2;
    /**
     * 点击文本内容的类型：链接
     */
    public static final int CLICK_TYPE_LINK = 3;

    /**
     * 子字符串数据结构体
     */
    public static class StrHolder {
        private String Name;
        private int start;
        private int end;

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }
    }

    /**
     * 对微博正文内容进行转换
     * <p>
     * 将汉字转换成拼音，再替换成表情
     *
     * ClickableSpan 会自动添加下划线并改变样式，所以应该先添加点击事件后改变样式
     *
     * @param str  原始文本
     * @param colorId 文本颜色
     * @param textSize 文字大小
     * @return 高亮显示的文本
     */
    public static SpannableStringBuilder convertText(Context context, String str, int colorId, int textSize) {
        // 需要处理的文本
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        // 设置表情
        ArrayList<StrHolder> emojiList = findRegexString(str, Constants.RegularExpression.EmojiRegex);
        replaceEmoji(context, builder, emojiList, textSize);
        // 设置话题
        ArrayList<StrHolder> topicList = findRegexString(str, Constants.RegularExpression.TopicRegex);
        showLightString(context, builder, topicList, colorId, CLICK_TYPE_TOPIC);
        // 设置用户昵称
        ArrayList<StrHolder> nameList = findRegexString(str, Constants.RegularExpression.NameRegex);
        showLightString(context, builder, nameList, colorId, CLICK_TYPE_USER_NAME);
        //设置链接
        ArrayList<StrHolder> linkList = findRegexString(str, Constants.RegularExpression.HttpRegex);
        showLightString(context, builder, linkList, colorId, CLICK_TYPE_LINK);

        return builder;
    }

    /**
     * 根据正则表达式查找字符串
     *
     * @param str 原始文本
     * @param regex 匹配规则
     * @return StrHolder 符合规则的子字符串列表
     */
    public static ArrayList<StrHolder> findRegexString(String str, String regex) {
        //匹配规则
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        ArrayList<StrHolder> arrayList = new ArrayList<>();

        while (matcher.find()) {//查找到匹配的字符串时
            String key = matcher.group();// 获取匹配到的具体字符
            StrHolder strHolder = new StrHolder();
            strHolder.setName(key);
            strHolder.setStart(matcher.start());
            strHolder.setEnd(matcher.end());
            arrayList.add(strHolder);
        }
        return arrayList;
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName variableName
     * @param c            类名
     * @return 资源Id
     */
    private static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            System.out.println("===NoSuchFie===:" + variableName != null ? variableName : "null");
            return -1;
        }
    }

    /**
     * 将创建时间转换成易读的时间
     *
     * @param createTime 创建时间，标准格式：EEE MMM dd HH:mm:ss z yyyy
     * @return 距离创建时间的时间段，大致时间
     */
    public static String convertCreateTime(Context context, String createTime) {
        String returnTime = "";
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm", Locale.ENGLISH);
        try {
            Date time = format.parse(createTime);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间

            long timeDifference = curDate.getTime() - time.getTime();//时间差
            long second = timeDifference / 1000;// 秒
            long min = second / 60;// 分
            long hour = min / 60;// 时
            long day = hour / 24;// 天

            if (min >= 0 && min < 1) {
                returnTime = context.getResources()
                        .getString(R.string.weibo_container_create_time_just_now);
            } else if (min >= 1 && min <= 60) {
                returnTime = min + context.getResources()
                        .getString(R.string.weibo_container_create_time_mins_ago);
            } else if (hour >= 1 && hour <= 24) {
                returnTime = hour + context.getResources()
                        .getString(R.string.weibo_container_create_time_hours_ago);
            } else if (day >= 1 && day <= 2) {
                returnTime = context.getResources()
                        .getString(R.string.weibo_container_create_time_yesterday) + timeFormat.format(time);
            } else if (dateFormat.format(curDate).substring(0, 2).equals(dateFormat.format(time).substring(0, 2))) {// 同一年内发布的动态
                returnTime = dateFormat.format(time).substring(3, 14);
            } else if (!dateFormat.format(curDate).substring(0, 2).equals(dateFormat.format(time).substring(0, 2))) {// 非同一年内发布的动态
                returnTime = dateFormat.format(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            returnTime = createTime;
        }
        return returnTime;
    }

    /**
     * 高亮显示字符串
     *
     * @param context   上下文对象
     * @param builder   SpannableStringBuilder
     * @param arrayList 进行操作的数据
     */
    private static void showLightString(Context context, SpannableStringBuilder builder,
                                        ArrayList<StrHolder> arrayList, int colorId, int clickType) {
        if (arrayList != null && arrayList.size() > 0) {
            for (StrHolder item : arrayList) {
                ClickableSpan clickableSpan = new TextClickableSpan(context, item.getName(), clickType);
                builder.setSpan(clickableSpan, item.getStart(), item.getEnd(),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                // 使用ForegroundColorSpan 为文本指定颜色
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(colorId);
                builder.setSpan(colorSpan, item.getStart(), item.getEnd(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
    }

    /**
     * 将文字替换成 emoji 表情
     *
     * @param context   上下文对象
     * @param builder   SpannableStringBuilder
     * @param arrayList 进行操作的数据
     */
    private static void replaceEmoji(Context context, SpannableStringBuilder builder, ArrayList<StrHolder> arrayList, int textSze) {
        if (arrayList != null && arrayList.size() > 0) {
            PinyinUtil pinyinUtils = PinyinUtil.getInstance(context);
            for (StrHolder emoji : arrayList) {
                if (!emoji.getName().isEmpty()) {
                    // 汉字转换成拼音，并去掉中括号，例如： [二哈] 转换成 [erha]
                    if (emoji.getName().length() <= 2) {
                        return;
                    }
                    String substring = emoji.getName().substring(1, emoji.getName().length() - 1);
                    String pinyin = pinyinUtils.getPinyin(substring);
                    Log.d("emoji", "replaceEmoji: ======" + substring + "======" + pinyin);
                    if (getResId(pinyin, R.drawable.class) != -1) {
                        //去掉中括号，并转换成资源Id
                        Drawable drawable = ContextCompat.getDrawable(context,
                                getResId(pinyin, R.drawable.class));
                        drawable.setBounds(0, 0, textSze, textSze);
                        //要让图片替代指定的文字就要用ImageSpan
                        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                        // 开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
                        // 最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12
                        builder.setSpan(imageSpan,
                                emoji.getStart(),
                                emoji.getEnd(),
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                }
            }
        }
    }
}
