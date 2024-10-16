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
public class AccountResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 7307422681707872074L;


    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "Status")
    private String status;
    @JsonProperty(value = "Data")
    private List<AccountDTO> data;


}
