package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.register.mobile.PinyinUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 对微博正文内容进行转换
     * <p>
     * 将汉字转换成拼音，再替换成表情
     *
     * @param str  原始文本
     * @param size 表情大小
     * @return 带表情的文本、话题、@功能的文本
     */
    public static SpannableString convertText(Context context, String str, int size) {
        //需要处理的文本
        SpannableString spannable = new SpannableString(str);
        // emoji 列表
        List<String> emojiList = findEmoji(context, str);
        // 微博用户昵称列表
        List<String> nameList = findName(str);
        // 话题列表
        List<String> topicList = findTopic(str);
        //设置话题
        if (topicList != null && topicList.size() > 0) {
            int findPos = 0;
            for (String topic : topicList) {//遍历话题
                findPos = str.indexOf(topic, findPos);//从findPos位置开始查找topic字符串
                if (findPos != -1) {
                    //使用ForegroundColorSpan 为文本指定颜色
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                            ContextCompat.getColor(context, R.color.card_more_suggest_text));
                    ClickableSpan clickableSpan = new TextClickableSpan(context, topic);
                    spannable.setSpan(colorSpan, findPos, findPos + topic.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    spannable.setSpan(clickableSpan, findPos, findPos + topic.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    findPos = findPos + topic.length();
                }
            }
        }
        //设置用户昵称
        if (nameList != null && nameList.size() > 0) {
            //为editable,中的用户昵称加入colorSpan
            int findPos = 0;
            for (String name : nameList) {//遍历用户昵称
                findPos = str.indexOf(name, findPos);//从findPos位置开始查找topic字符串
                if (findPos != -1) {
                    //使用ForegroundColorSpan 为文本指定颜色
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                            ContextCompat.getColor(context, R.color.card_more_suggest_text));
                    ClickableSpan clickableSpan = new TextClickableSpan(context, name);
                    spannable.setSpan(colorSpan, findPos, findPos + name.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    spannable.setSpan(clickableSpan, findPos, findPos + name.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    findPos = findPos + name.length();
                }
            }
        }
        //设置表情
        if (emojiList != null && emojiList.size() > 0) {
            int findPos = 0;
            for (String emoji : emojiList) {
                if (getResId("d_" + emoji.substring(1, emoji.length() - 1), R.drawable.class) != -1) {
                    findPos = str.indexOf(emoji, findPos);//从findPos位置开始查找topic字符串
                    if (findPos != -1) {
                        //去掉中括号，并转换成资源Id
                        Drawable drawable = ContextCompat.getDrawable(context, getResId(emoji, R.drawable.class));
                        drawable.setBounds(0, 0, size, size);
                        //要让图片替代指定的文字就要用ImageSpan
                        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                        //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
                        //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12
                        spannable.setSpan(imageSpan,
                                findPos,
                                findPos + emoji.length(),
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        findPos = findPos + emoji.length();
                    }
                }
            }
        }
        return spannable;
    }

    /**
     * 显示Emoji表情
     *
     * @param str 原始文本
     * @return Emoji List
     */
    public static ArrayList<String> findEmoji(Context context, String str) {
        //匹配规则
        Pattern patternEmoji = Pattern.compile(Constants.RegularExpression.EmojiRegex);
        Matcher matcherEmoji = patternEmoji.matcher(str);

        ArrayList<String> emoji = new ArrayList<>();
        PinyinUtils pinyinUtils = PinyinUtils.getInstance(context);
        while (matcherEmoji.find()) {//查找到匹配的字符串时
            String key = matcherEmoji.group();// 获取匹配到的具体字符
            //获取拼音，格式：[pinyin]
            String pinyin = pinyinUtils.getPinyin(key.substring(0, key.length()));
            emoji.add(pinyin);
        }
        return emoji;
    }

    /**
     * 显示话题
     *
     * @param str 原始文本
     * @return 话题List
     */
    public static ArrayList<String> findTopic(String str) {
        //匹配规则
        Pattern patternTopic = Pattern.compile(Constants.RegularExpression.TopicRegex);
        Matcher matcherTopic = patternTopic.matcher(str);

        ArrayList<String> topic = new ArrayList<>();

        while (matcherTopic.find()) {//查找到匹配的字符串时
            String key = matcherTopic.group();// 获取匹配到的具体字符
            topic.add(key);
        }
        return topic;
    }

    /**
     * 查找微博用户昵称
     *
     * @param str 原始文本
     * @return 微博用户昵称List
     */
    public static ArrayList<String> findName(String str) {
        //匹配规则
        Pattern patternName = Pattern.compile(Constants.RegularExpression.NameRegex);
        Matcher matcherName = patternName.matcher(str);

        ArrayList<String> name = new ArrayList<>();

        while (matcherName.find()) {//查找到匹配的字符串时
            String key = matcherName.group();// 获取匹配到的具体字符
            name.add(key);
        }
        return name;
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
            Logger.d(variableName);
            return -1;
        }
    }

    /**
     * 将创建时间转换成易读的时间
     *
     * @param createTime 创建时间，标准格式：EEE MMM dd HH:mm:ss z yyyy
     * @return 距离创建时间的时间段，大致时间
     */
    public static String convertCreateTime(String createTime) {
        String returnTime;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            Date time = format.parse(createTime);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间

            long timeDifference = curDate.getTime() - time.getTime();//时间差
            long second = timeDifference / 1000;// 秒
            long min = second / 60;// 分
            long hour = min / 60;// 时
            long day = hour / 24;// 天
            long mouth = day / 30;// 月
            long year = mouth / 12;// 年

            if (min >= 0 && min < 1) {
                returnTime = "刚刚";
            } else if (min >= 1 && min <= 60) {
                returnTime = min + "分钟前";
            } else if (hour >= 1 && hour <= 24) {
                returnTime = hour + "小时前";
            } else if (day >= 1 && day <= 30) {
                returnTime = day + "天前";
            } else if (mouth >= 1 && mouth <= 12) {
                returnTime = mouth + "个月前";
            } else {
                returnTime = year + "年前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            returnTime = createTime;
        }
        return returnTime;
    }
}
