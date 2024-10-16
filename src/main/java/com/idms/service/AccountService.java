package com.idms.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.idms.dao.entity.Account;
import com.idms.dao.repository.AccountRepository;
import com.idms.model.request.AccountFilterRequest;
import com.idms.model.response.AccountRowDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public void processAccountInfoIntoDB(List<AccountRowDTO> accountRowDTOS) {
        List<List<AccountRowDTO>> partition = ListUtils.partition(accountRowDTOS, 10);

        AtomicReference<List<Account>> accountList = new AtomicReference<>(new ArrayList<>());

        partition.parallelStream().forEach(accountRowList -> {
            CompletableFuture<List<Account>> mapCompletableFuture = CompletableFuture.supplyAsync(() -> saveAccountInfo(accountRowList));
            try {
                accountList.get().addAll(mapCompletableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        if(!CollectionUtils.isEmpty(accountList.get())){
            Map<String, Account> collect = accountList.get().stream().distinct().collect(Collectors.toMap(Account::getAcctID, Function.identity()));
            accountRepository.saveAll(collect.values());
        }
    }

    private List<Account> saveAccountInfo(List<AccountRowDTO> accountRowList) {
        List<String> list = accountRowList.stream().map(AccountRowDTO::getAcctID).toList();
        List<Account> byAcctIDIn = accountRepository.findAllByAcctIDIn(list);
        Map<String, Account> collect = byAcctIDIn.stream().collect(Collectors.toMap(Account::getAcctID, Function.identity()));
        AtomicReference<List<Account>> accountList = new AtomicReference<>(new ArrayList<>());
        accountRowList.parallelStream().forEach(accountRowDTO -> {
            Account account = collect.get(accountRowDTO.getAcctID());
            if(account == null){
                Account build = Account.builder().acctID(accountRowDTO.getAcctID()).
                        contractSalesPrice(accountRowDTO.getContractSalesPrice()).
                        acctType(accountRowDTO.getAcctType()).
                        salesGroupPerson1ID(accountRowDTO.getSalesGroupPerson1ID()).
                        contractDate(accountRowDTO.getContractDate()).
                        collateralStockNumber(accountRowDTO.getCollateralStockNumber()).
                        collateralYearModel(accountRowDTO.getCollateralYearModel()).
                        collateralMake(accountRowDTO.getCollateralMake()).
                        collateralModel(accountRowDTO.getCollateralModel()).
                        borrower1FirstName(accountRowDTO.getBorrower1FirstName()).
                        borrower1LastName(accountRowDTO.getBorrower1LastName()).build();
                accountList.get().add(build);
            }else{
                boolean dataUpdated = false;
                dataUpdated = isDataUpdated(account,accountRowDTO.getContractSalesPrice(), account.getContractSalesPrice(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getAcctType(), account.getAcctType(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getSalesGroupPerson1ID(), account.getSalesGroupPerson1ID(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getContractDate(), account.getContractDate(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getCollateralStockNumber(), account.getCollateralStockNumber(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getCollateralYearModel(), account.getCollateralYearModel(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getCollateralMake(), account.getCollateralMake(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getCollateralModel(), account.getCollateralModel(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getBorrower1FirstName(), account.getBorrower1FirstName(), dataUpdated);
                dataUpdated = isDataUpdated(account,accountRowDTO.getBorrower1LastName(), account.getBorrower1LastName(), dataUpdated);
                if(dataUpdated){
                    accountList.get().add(account);
                }
            }
        });

        return accountList.get();
    }

    private static boolean isDataUpdated(Account account,String newData,String oldData, boolean dataUpdated) {
        if(newData != null && !newData.equals(oldData)
                || oldData !=null && !oldData.equals(newData)){
            account.setContractSalesPrice(newData);
            dataUpdated =true;
        }
        return dataUpdated;
    }

    public List<AccountRowDTO> filterAccountRowDTO(AccountFilterRequest filterRequest) {
        List<Account> accounts;
        if(!CollectionUtils.isEmpty(filterRequest.getAcctIdList()) && StringUtils.hasText(filterRequest.getAcctType())){
            accounts = accountRepository.findAllByAcctIDInAndAcctType(filterRequest.getAcctIdList(), filterRequest.getAcctType());
        }else if(!CollectionUtils.isEmpty(filterRequest.getAcctIdList())){
            accounts = accountRepository.findAllByAcctIDIn(filterRequest.getAcctIdList());
        }else if(StringUtils.hasText(filterRequest.getAcctType())){
            accounts = accountRepository.findAllByAcctType(filterRequest.getAcctType());
        }else{
            accounts = accountRepository.findAll();
        }
        AtomicReference<List<AccountRowDTO>> accountList = new AtomicReference<>(new ArrayList<>());
        accounts.forEach(account -> {
            AccountRowDTO build = AccountRowDTO.builder().acctID(account.getAcctID()).
                    contractSalesPrice(account.getContractSalesPrice()).
                    acctType(account.getAcctType()).
                    salesGroupPerson1ID(account.getSalesGroupPerson1ID()).
                    contractDate(account.getContractDate()).
                    collateralStockNumber(account.getCollateralStockNumber()).
                    collateralYearModel(account.getCollateralYearModel()).
                    collateralMake(account.getCollateralMake()).
                    collateralModel(account.getCollateralModel()).
                    borrower1FirstName(account.getBorrower1FirstName()).
                    borrower1LastName(account.getBorrower1LastName()).build();
            accountList.get().add(build);
        });

        return accountList.get();
    }

}
