package com.idms.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -4392207582785482176L;

    private int status;
    private String message;
    private String token;
    private T data;
    private List<T> errorList;
    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public CommonResponse(int status,String token, String message) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public CommonResponse(int status, List<T> errorList) {
        this.status = status;
        this.errorList = errorList;
    }
    public CommonResponse(int status,T data,String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

}
