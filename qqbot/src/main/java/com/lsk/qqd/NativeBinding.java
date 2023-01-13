package com.lsk.qqd;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface NativeBinding extends Library {
    public static interface DBusSignalListener extends Callback {
        void invoke(Pointer messageJson);
    }

    int sendMessageToDBus(String messageJson);
    void setSignalCB(DBusSignalListener cb);
    int dbusInit();
    int dbusFinalize();
    void dbusListen();
}
