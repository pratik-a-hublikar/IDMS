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
@Builder
public class AccountRowDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6050965151803727135L;


    @JsonProperty(value = "ContractSalesPrice")
    private String contractSalesPrice;

    @JsonProperty(value = "AcctType")
    private String acctType;

    @JsonProperty(value = "SalesGroupPerson1ID")
    private String salesGroupPerson1ID;

    @JsonProperty(value = "ContractDate")
    private String contractDate;

    @JsonProperty(value = "CollateralStockNumber")
    private String collateralStockNumber;

    @JsonProperty(value = "CollateralYearModel")
    private String collateralYearModel;

    @JsonProperty(value = "CollateralMake")
    private String collateralMake;

    @JsonProperty(value = "CollateralModel")
    private String collateralModel;

    @JsonProperty(value = "Borrower1FirstName")
    private String borrower1FirstName;

    @JsonProperty(value = "Borrower1LastName")
    private String borrower1LastName;

    @JsonProperty(value = "AcctID")
    private String acctID;
}
