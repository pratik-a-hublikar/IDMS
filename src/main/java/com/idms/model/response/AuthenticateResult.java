package com.idms.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 7307422681707872074L;


    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "Status")
    private Integer status;
    @JsonProperty(value = "Token")
    private String token;
}
