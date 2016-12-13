/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package com.sina.weibo.sdk.codestyle;

/**
 * 类的大体描述放在这里。
 *         
 * @author 作者名
 * @since 2013-XX-XX
 */
@SuppressWarnings("unused")
public class SampleCode {

    /** 公有的常量注释 */
    public static final String ACTION_MAIN = "android.intent.action.MAIN";
    
    /** 私有的常量注释（同类型的常量可以分块并紧凑定义） */
    private static final int MSG_AUTH_NONE    = 0;
    private static final int MSG_AUTH_SUCCESS = 1;
    private static final int MSG_AUTH_FAILED  = 2;
    
    /** 保护的成员变量注释 */
    protected Object mObject0;
    
    /** 私有的成员变量 mObject1 注释（同类型的成员变量可以分块并紧凑定义） */
    private Object mObject1;
    /** 私有的成员变量 mObject2 注释 */
    private Object mObject2;
    /** 私有的成员变量 mObject3 注释 */
    private Object mObject3;
    
    /**
     * 对于注释多于一行的，采用这种方式来
     * 定义该变量
     */
    private Object mObject4;

    /**
     * 公有方法描述...
     * 
     * @param param1  参数1描述...
     * @param param2  参数2描述...
     * @param paramXX 参数XX描述... （注意：请将参数、描述都对齐）
     */
    public void doSomething(int param1, float param2, String paramXX) {
        // ...implementation
    }
    
    /**
     * 保护方法描述...
     */
    protected void doSomething() {
        // ...implementation        
    }
    
    /**
     * 私有方法描述...
     * 
     * @param param1  参数1描述...
     * @param param2  参数2描述...
     */
    private void doSomethingInternal(int param1, float param2) {
        // ...implementation        
    }
}
