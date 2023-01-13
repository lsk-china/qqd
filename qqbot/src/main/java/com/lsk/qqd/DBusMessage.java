package com.lsk.qqd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBusMessage {
    private Integer code;
    private String message;
    private String error;
}
