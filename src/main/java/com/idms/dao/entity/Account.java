package com.idms.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_sales_price")
    private String contractSalesPrice;

    @Column(name = "acct_type")
    private String acctType;

    @Column(name = "sales_group_person1_id")
    private String salesGroupPerson1ID;

    @Column(name = "contract_date")
    private String contractDate;

    @Column(name = "collateral_stock_number")
    private String collateralStockNumber;

    @Column(name = "collateral_year_model")
    private String collateralYearModel;

    @Column(name = "collateral_make")
    private String collateralMake;

    @Column(name = "collateral_model")
    private String collateralModel;

    @Column(name = "borrower1_first_name")
    private String borrower1FirstName;

    @Column(name = "borrower1_last_name")
    private String borrower1LastName;

    @Column(name = "acct_id")
    private String acctID;

}
