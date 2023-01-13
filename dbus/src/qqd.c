//
// Created by lsk on 1/13/23.
//

#include "qqd.h"

int sendMessageToBus(char *messageJson) {

}
void setSignalCB(const DBusSignalListener cbIn) {
    cb = cbIn;
}
int dbusInit() {
    DBusError error;
    dbus_error_init(&error);
    int ret;
    // initialize dbus connection
    conn = dbus_bus_get(DBUS_BUS_SYSTEM, &error);
    if (dbus_error_is_set(&error)) {
        fprintf(stderr, "Connection Error (%s)\n", error.message);
        ret = 1;
        goto finalize;
    }
    if (conn == NULL) {
        ret = 1;
        goto finalize;
    }
    // request name on dbus
    ret = dbus_bus_request_name(conn, QQD_DBUS_NAME, DBUS_NAME_FLAG_REPLACE_EXISTING, &error);
    if (dbus_error_is_set(&error)) {
        fprintf(stderr, "Name Error (%s)\n", error.message);
        ret = 2;
        goto finalize;
    }
    if (DBUS_REQUEST_NAME_REPLY_PRIMARY_OWNER != ret) {
        fprintf(stderr, "Not Primary Owner (%d)\n", ret);
        ret = 3;
        goto finalize;
    }
    ret = 0;
finalize:
    dbus_error_free(&error);
    return ret;
}
int dbusFinalize() {
    dbus_connection_close(conn);
}
void dbusListen() {
    if (cb == NULL) {
        fprintf(stderr, "Callback is not set");
        return;
    }
    DBusMessage* msg;
    DBusMessageIter args;
    char *json;
    while(runDBusLoop) {
        dbus_connection_read_write(conn, 0);
        msg = dbus_connection_pop_message(conn);
        if (NULL == msg) {
            sleep(1);
            continue;
        }
        if (dbus_message_is_method_call(msg, INTERFACE_NAME_SEND_MESSAGE, METHOD_NAME_SEND_MESSAGE)) {
            if (!dbus_message_iter_init(msg, &args)) {
                (*cb)(REPORT_NO_ARG);
            } else if (DBUS_TYPE_STRING != dbus_message_iter_get_arg_type(&args)) {
                (*cb)(REPORT_INV_ARG);
            } else {
                dbus_message_iter_get_basic(&args, &json);
                char *final_message = (char *) malloc(sizeof(char) * (strlen(json) + strlen(REPORT_TEMPLATE) - 2) + 1);
                sprintf(final_message, REPORT_TEMPLATE, json);
                (*cb)(final_message);
                free(final_message);
            }
        }
        dbus_message_unref(msg);
    }
}