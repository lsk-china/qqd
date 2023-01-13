package com.lsk

import com.lsk.qqd.DBusMessage
import com.lsk.qqd.NativeBinding
import com.sun.org.slf4j.internal.Logger
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.MiraiLogger
import net.mamoe.mirai.utils.info

object QQD : KotlinPlugin(
    JvmPluginDescription(
        id = "com.lsk.qqd",
        name = "QQD",
        version = "0.1.0",
    ) {
        author("lsk")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
        GlobalEventChannel.registerListenerHost(object : SimpleListenerHost() {
            @EventHandler
            public fun onGroup(event: GroupMessageEvent) {}
            @EventHandler
            public fun onFriend(event: FriendMessageEvent) {}
        }, coroutineContext);
        DBusInterface.sIntance.onHavingMessageToBeSent {

        };
    }

    override fun onDisable() {
        DBusInterface.sIntance.finalizeDBus();
    }
}