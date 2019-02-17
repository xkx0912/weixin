package com.itmayiedu.entity;


/**
 * Created by xkx on 2019/2/16.
 */
public class TextMessage {
    /**  参数详情见微信开发文档  https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140543
     *   参数	      是否必须	   描述
         ToUserName	  是	       接收方帐号（收到的OpenID）
         FromUserName 是	       开发者微信号
         CreateTime	  是	       消息创建时间 （整型）
         MsgType	  是	       text
         Content	  是	       回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */

    private String ToUserName;

    private String FromUserName;

    private Long CreateTime;

    private String MsgType;

    private String Content;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
