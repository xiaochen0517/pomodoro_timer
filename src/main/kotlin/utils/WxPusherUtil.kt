package utils

import androidx.compose.ui.window.Notification
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import model.WxPusherContentType
import model.WxPusherSendNotificationParam
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import store.AppStore


class WxPusherUtil {

    companion object {

        private val log = LogUtil.create("WxPusherUtil")

        private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        private var client: OkHttpClient = OkHttpClient()

        private const val WX_PUSHER_API_URL = "https://pt.pusher.mochen.fun/api"

        fun sendNotification(notification: Notification) {
            sendNotification(notification.title, notification.message)
        }

        fun sendNotification(
            title: String,
            content: String,
            contentType: WxPusherContentType = WxPusherContentType.TEXT,
            uid: String? = null
        ) {
            log.info("send notification: $title - $content")
            var wxPusherUID: String? = uid
            if (wxPusherUID == null) {
                wxPusherUID = AppStore.state.wxPusherUID
            }
            if (wxPusherUID.isEmpty()) {
                log.error("wxPusherUID is empty")
                return
            }
            val requestParam = WxPusherSendNotificationParam(
                summary = title.ifEmpty {
                    null
                },
                content = content,
                contentType = contentType.value,
                uids = listOf(wxPusherUID)
            )
            val requestBody =
                gson.toJson(requestParam).toRequestBody("application/json".toMediaTypeOrNull())
            val request: Request = Request.Builder()
                .url("$WX_PUSHER_API_URL/send/message")
                .post(requestBody)
                .build()
            client.newCall(request).execute().use { _ ->
            }
        }
    }
}
