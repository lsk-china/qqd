package com.lsk

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
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
    }
}