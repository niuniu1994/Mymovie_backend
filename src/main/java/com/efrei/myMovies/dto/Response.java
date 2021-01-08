package com.efrei.myMovies.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class Response {


    /**
     * error code
     */

    public Response() {
        errorCode = 0;
    }

    public Response (Object data){
        this.data = data;
    }
    /** error code, return 0 if correct */
    private Integer errorCode;

    /** error message, return "" if correct */
    private String errorMsg = "";

    /** returned object */
    private Object data;


}
