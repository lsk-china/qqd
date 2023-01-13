package com.lsk

import com.google.gson.Gson
import com.lsk.qqd.DBusMessage
import com.lsk.qqd.NativeBinding
import com.sun.jna.Native
import net.mamoe.mirai.utils.MiraiLogger
import java.lang.Error
import java.util.function.Consumer

class DBusInterface {
    private val nativeBinding : NativeBinding = Native.load("classpath:nativeLib/libqqd.so", NativeBinding::class.java);
    private val GSON: Gson = Gson();
    private val logger: MiraiLogger = QQD::logger.get();
    companion object {
        val sIntance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DBusInterface()
        }
    }
    init {
        val status : Int = nativeBinding.dbusInit();
        if (status != 0) run {
            logger.error("Failed to initialize dbus");
            throw Error("Failed to initialize dbus");
        }
        Thread(nativeBinding::dbusListen).start();
    }
    fun postMessage(message: String) {
        nativeBinding.sendMessageToDBus(message);
    }
    fun finalizeDBus() {
        nativeBinding.dbusFinalize();
    }
    fun onHavingMessageToBeSent(cb: Consumer<String>) {
        nativeBinding.setSignalCB {
            val jsonString: String = it.getString(0);
            val dBusMessage: DBusMessage = GSON.fromJson(jsonString, DBusMessage::class.java);
            if (dBusMessage.code != 0) {
                cb.accept(dBusMessage.json);
            } else {
                logger.error(dBusMessage.message);
            }
        }
    }
}
