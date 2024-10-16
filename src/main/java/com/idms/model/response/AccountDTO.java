package com.idms.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8282579728388262411L;

    @JsonProperty(value = "Row")
    private AccountRowDTO row;

}
