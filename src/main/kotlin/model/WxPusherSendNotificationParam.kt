package model

import com.google.gson.annotations.Expose

data class WxPusherSendNotificationParam(
    // 消息内容 支持HTML代码 必填
    @Expose
    val content: String,
    // 消息摘要
    @Expose
    val summary: String? = null,
    // 消息类型 1表示文字  2表示html
    @Expose
    val contentType: Int,
    // 发送的消息主题
    @Expose
    val topicIds: List<Int>? = null,
    // 发送的用户UID列表
    @Expose
    val uids: List<String>? = null,
    // 原文链接，可选
    @Expose
    val url: String? = null,
    // 是否验证订阅时间，0：不验证，1:只发送给付费的用户，2:只发送给未订阅或者订阅过期的用户
    @Expose
    val verifyPayType: Int? = null,
)
