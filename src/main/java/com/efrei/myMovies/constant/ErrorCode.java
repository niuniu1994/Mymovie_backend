package com.efrei.myMovies.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(0, "Success"), NO_PERMISSION(-1, "No permission"), FORMATINCORRECT(-2, "Data format incorrect"), NULLOBJECT(-3, "Object is null"), FAILED(-4, "Fail");


    ErrorCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private Integer errorCode;

    private String errorMsg;
}
