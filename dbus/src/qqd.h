//
// Created by lsk on 1/13/23.
//

#ifndef QQD_QQD_H
#define QQD_QQD_H

#include <dbus/dbus.h>
#include <unistd.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define QQD_DBUS_NAME "com.lsk.qqd"
#define SIGNAL_SOURCE_NAME "com.lsk.qqd.receiver"
#define SIGNAL_OBJECT_NAME_MESSAGE_RECEIVED "/com/lsk/qqd/received"
#define SIGNAL_INTERFACE_NAME_MESSAGE_RECEIVED "com.lsk.qqd.Received"
#define SIGNAL_NAME_MESSAGE_RECEIVED "Received"
#define METHOD_NAME_SEND_MESSAGE "Send"
#define INTERFACE_NAME_SEND_MESSAGE "com.lsk.qqd.Send"
#define REPORT_NO_ARG "{\"code\": -1, \"message\":\"No argument is passed to method\", \"json\":\"\"}"
#define REPORT_INV_ARG "{\"code\": -2, \"message\":\"Invalid argument\", \"json\":\"\"}"
#define REPORT_TEMPLATE "{\"code\": 0, \"message\":\"Success\", \"json\": %s}"

typedef void(*DBusSignalListener)(char *messageJson);
static DBusSignalListener cb = NULL;
static DBusConnection *conn = NULL;
static volatile int runDBusLoop = 1;

int sendMessageToBus(char *messageJson);
void setSignalCB(const DBusSignalListener cb);
int dbusInit();
void dbusListen();
int dbusFinalize();

#endif //QQD_QQD_H
