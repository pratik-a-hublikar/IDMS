package com.idms.model.request;

import com.idms.commons.constant.ConstantMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AccountFilterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -534674433510978690L;

    private List<String> acctIdList;
    private String acctType;
}
