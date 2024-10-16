package com.idms.model.request;

import com.idms.commons.constant.ConstantMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class LoginRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 156905084447613190L;


    @NotEmpty(message = ConstantMessages.USER_NAME_NOT_NULL)
    private String username;

    @NotEmpty(message = ConstantMessages.PASSWORD_NAME_NOT_NULL)
    private String password;

}
