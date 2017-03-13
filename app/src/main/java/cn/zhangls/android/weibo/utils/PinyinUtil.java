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
import android.text.TextUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/10.
 * <p>
 * 新浪微博官方 SDK PinyinUtil
 */

public class PinyinUtil {
    private static PinyinUtil sInstance;
    private static short[] sPinyinIndex;
    private static final String[] PINYIN = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "cen", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dia", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "ei", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "luan", "lun", "luo", "lv", "lve", "m", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "none", "nong", "nou", "nu", "nuan", "nuo", "nv", "nve", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pou", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shei", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yiao", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhei", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private static volatile boolean isLoad = false;
    private static final char SPECIAL_HANZI = '〇';
    private static final String SPECIAL_HANZI_PINYIN = "LING";
    private static final char FIRST_CHINA = '一';
    private static final char LAST_CHINA = '龥';
    private static final int DISTINGUISH_LEN = 10;

    private PinyinUtil() {
    }

    public static synchronized PinyinUtil getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new PinyinUtil();
        }

        loadData(ctx);
        return sInstance;
    }

    private static void loadData(Context ctx) {
        InputStream input = null;
        DataInputStream dataInput = null;

        try {
            if (!isLoad) {
                input = ctx.getAssets().open("pinyinindex");
                dataInput = new DataInputStream(input);
                long e = (long) (dataInput.available() >> 1);
                sPinyinIndex = new short[(int) e];

                for (int i = 0; i < sPinyinIndex.length; ++i) {
                    sPinyinIndex[i] = dataInput.readShort();
                }

                isLoad = true;
            }
        } catch (Exception var18) {
            isLoad = false;
        } finally {
            try {
                if (dataInput != null) {
                    dataInput.close();
                }

                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
            }

        }

    }

    private String getPinyin(char ch) {
        if (!isLoad) {
            return "";
        } else {
            String pinyin;
            if (ch == 12295) {
                return "LING";
            } else if (ch >= 19968 && ch <= '龥') {
                int pos = ch - 19968;
                pinyin = PINYIN[sPinyinIndex[pos]];
                if (pinyin == null) {
                    pinyin = "";
                }

                return pinyin;
            } else {
                return String.valueOf(ch);
            }
        }
    }

    public String getPinyin(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        } else if (!isLoad) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            int len = s.length();

            for (int i = 0; i < len; ++i) {
                char c = s.charAt(i);
                sb.append(this.getPinyin(c));
            }

            return sb.toString();
        }
    }

    public PinyinUtil.MatchedResult getMatchedResult(String src, String input) {
        PinyinUtil.MatchedResult result = new PinyinUtil.MatchedResult();
        result.start = -1;
        result.end = -1;
        if (!isLoad) {
            return result;
        } else if (!TextUtils.isEmpty(src) && !TextUtils.isEmpty(input)) {
            src = src.toUpperCase();
            input = input.toUpperCase();
            int n = Math.min(src.length(), input.length());
            if (n > 10) {
                src = src.substring(0, 10);
                input = input.substring(0, 10);
            }

            int index = src.indexOf(input);
            if (index >= 0) {
                result.start = index;
                result.end = index + input.length() - 1;
            }

            char[] search = new char[input.length()];

            for (int org = 0; org < input.length(); ++org) {
                search[org] = input.charAt(org);
            }

            char[] var15 = new char[src.length()];
            String[] fullPinyin = new String[src.length()];
            int srcLen = src.length();

            for (int firstSearch = 0; firstSearch < srcLen; ++firstSearch) {
                char i = src.charAt(firstSearch);
                var15[firstSearch] = i;
                String ch1 = this.getPinyin(i);
                if (!TextUtils.isEmpty(ch1)) {
                    fullPinyin[firstSearch] = ch1.toUpperCase();
                } else {
                    fullPinyin[firstSearch] = String.valueOf(i);
                }
            }

            char var16 = search[0];

            for (int var17 = 0; var17 < fullPinyin.length; ++var17) {
                char var18 = fullPinyin[var17].charAt(0);
                char ch2 = var15[var17];
                boolean pos = true;
                if (var18 == var16 || ch2 == var16) {
                    int var19 = this.distinguish(search, 0, this.subCharRangeArray(var15, var17, var15.length - 1), this.subStringRangeArray(fullPinyin, var17, fullPinyin.length - 1), 0, 0);
                    if (var19 != -1) {
                        result.start = var17;
                        result.end = var17 + var19;
                        return result;
                    }
                }
            }

            return result;
        } else {
            return result;
        }
    }

    public int distinguish(char[] search, int searchIndex, char[] src, String[] pinyin, int wordIndex, int wordStart) {
        if (searchIndex != 0 || search[0] != src[0] && search[0] != pinyin[0].charAt(0)) {
            if (pinyin[wordIndex].length() <= wordStart || searchIndex >= search.length || search[searchIndex] != src[wordIndex] && search[searchIndex] != pinyin[wordIndex].charAt(wordStart)) {
                if (pinyin.length > wordIndex + 1 && searchIndex < search.length && (search[searchIndex] == src[wordIndex + 1] || search[searchIndex] == pinyin[wordIndex + 1].charAt(0))) {
                    return searchIndex == search.length - 1 ? (this.distinguish(search, src, pinyin, wordIndex) ? wordIndex + 1 : -1) : this.distinguish(search, searchIndex + 1, src, pinyin, wordIndex + 1, 1);
                } else {
                    if (pinyin.length > wordIndex + 1) {
                        for (int i = 1; i < searchIndex; ++i) {
                            if (this.distinguish(search, searchIndex - i, src, pinyin, wordIndex + 1, 0) != -1) {
                                return wordIndex + 1;
                            }
                        }
                    }

                    return -1;
                }
            } else {
                return searchIndex == search.length - 1 ? (this.distinguish(search, src, pinyin, wordIndex) ? wordIndex : -1) : this.distinguish(search, searchIndex + 1, src, pinyin, wordIndex, wordStart + 1);
            }
        } else {
            return search.length != 1 ? this.distinguish(search, 1, src, pinyin, 0, 1) : 0;
        }
    }

    private boolean distinguish(char[] search, char[] src, String[] pinyin, int wordIndex) {
        String searchString = new String(search);
        int lastIndex = 0;
        boolean i = false;

        for (int var8 = 0; var8 < wordIndex; ++var8) {
            lastIndex = searchString.indexOf(pinyin[var8].charAt(0), lastIndex);
            if (lastIndex == -1) {
                lastIndex = searchString.indexOf(src[var8], lastIndex);
            }

            if (lastIndex == -1) {
                return false;
            }

            ++lastIndex;
        }

        return true;
    }

    private char[] subCharRangeArray(char[] org, int start, int end) {
        int len = end - start + 1;
        char[] ret = new char[len];
        int i = start;

        for (int j = 0; i <= end; ++j) {
            ret[j] = org[i];
            ++i;
        }

        return ret;
    }

    private String[] subStringRangeArray(String[] org, int start, int end) {
        int len = end - start + 1;
        String[] ret = new String[len];
        int i = start;

        for (int j = 0; i <= end; ++j) {
            ret[j] = org[i];
            ++i;
        }

        return ret;
    }

    public static PinyinUtil getObject() {
        return sInstance;
    }

    public static class MatchedResult {
        public int start = -1;
        public int end = -1;

        public MatchedResult() {
        }
    }
}
