package com.xuechuan.xcedu.jg;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.jg
 * @Description: 注册别名
 * @author: L-BackPacker
 * @date: 2018/5/29 17:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RegisterTag {

    private Context mContext;
    private static RegisterTag service;

    public RegisterTag(Context mContext) {
        this.mContext = mContext;
    }

    public static RegisterTag getInstance(Context context) {
        if (service == null) {
            service = new RegisterTag(context);
        }
        return service;
    }
   public void registJG(){
       JPushInterface.init(mContext);
       JPushInterface.resumePush(mContext);
   }

    public  void setTagAndAlias(String  id) {

        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!StringUtil.isEmpty(id)) {
            tags.add(id);//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(mContext, id, tags,
                mAliasCallback);
        // }
//        JPushInterface.setAlias(mContext,String.valueOf(id),mAliasCallback);

    }

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private  final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("别名", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("别名", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("别名", logs);
                    break;
            }
        }
    };

    /**
     * 取消设置标签与别名
     */
    public void cancleTagAndAlias() {
        //TODO 上下文、别名、标签、回调  退出后空数组与空字符串取消之前的设置
        Set<String> tags = new HashSet<String>();
        JPushInterface.setAliasAndTags(mContext, "", tags, mAliasCallback);
        JPushInterface.stopPush(mContext);
    }
}
