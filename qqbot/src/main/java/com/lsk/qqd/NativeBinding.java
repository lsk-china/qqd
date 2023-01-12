package com.lsk.qqd;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface NativeBinding extends Library {
    public static interface DBusSignalListener extends Callback {
        void invoke(Pointer messageJson);
    }

    void sendMessageToDBus(String messageJson);
    void setSignalCB(DBusSignalListener cb);
    void dbusInit();
    void dbusFinalize();
}
