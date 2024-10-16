package com.idms.dao.repository;

import com.idms.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    List<Account> findAllByAcctIDIn(List<String> acctIDs);
    List<Account> findAllByAcctIDInAndAcctType(List<String> acctIDs,String acctType);
    List<Account> findAllByAcctType(String acctType);

}
