package com.itmayiedu.controller;

/**
 * Created by xkx on 2019/2/14.
 */

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.entity.TextMessage;
import com.itmayiedu.utils.CheckUtil;
import com.itmayiedu.utils.HttpClientUtil;
import com.itmayiedu.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * 微信事件通知
 *
 * @author 余胜军
 *
 */

@RestController
@Slf4j
public class DispatCherServlet {

    /**
     * 微信验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "/dispatCherServlet", method = RequestMethod.GET)
    public String getDispatCherServlet(String signature, String timestamp, String nonce, String echostr) {
        // 验签
        boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
        if (!checkSignature) {
            return null;
        }
        return echostr;
    }

    /**
     * 功能说明:微信事件通知
     *
     * @return request
     * @throws Exception
     */
    @RequestMapping(value = "/dispatCherServlet", method = RequestMethod.POST)
    public void postDispatCherServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
       request.setCharacterEncoding("UTF-8");
       response.setCharacterEncoding("UTF-8");
        Map<String,String> result = XmlUtils.parseXml(request);
        String toUserName = result.get("ToUserName");
        String fromUserName = result.get("FromUserName");
        String msgType = result.get("MsgType");
        String content = result.get("Content");
        switch (msgType){
            case "text":
                String resultXml = null;
                PrintWriter out = response.getWriter();
                String textMessageContent = null;
                if(content.contains("帅比")){
                    TextMessage textMessage = new TextMessage();
                    // 被动响应客户发送的消息 ToUserName、FromUserName反了
                    resultXml = setTextMessage("过奖过奖",toUserName, fromUserName,msgType);
                }else{
                    String resuleApi = HttpClientUtil.doGet("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + content);
                    JSONObject jsonObject = new JSONObject().parseObject(resuleApi);
                    Integer integer = jsonObject.getInteger("result");
                    if(integer != null && integer != 0){
                        resultXml = setTextMessage("亲,系统出错啦!", toUserName, fromUserName,msgType);
                    }else {
                        String resultApi = (String) jsonObject.get("content");
                        resultApi = resultApi.replaceAll("\\{br\\}","\n");
                        resultXml = setTextMessage(resultApi, toUserName, fromUserName,msgType);
                    }
                }
                out.print(resultXml);
                out.close();
                break;
            default:
                break;
        }
    }

    public String setTextMessage(String content, String toUserName, String fromUserName,String type) {
        TextMessage textMessage = new TextMessage();
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setContent(content);
        textMessage.setMsgType(type);
        String messageToXml = XmlUtils.messageToXml(textMessage);
        return messageToXml;
    }

}
