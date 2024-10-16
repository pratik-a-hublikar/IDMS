package com.idms.service;

import com.idms.client.IDMSClient;
import com.idms.helper.FeignHelper;
import com.idms.model.request.AccountFilterRequest;
import com.idms.model.response.AccountDTO;
import com.idms.model.response.AccountResult;
import com.idms.model.response.AccountRowDTO;
import com.idms.model.response.AuthenticateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IDMSService {



    @Autowired
    private IDMSClient idmsClient;

    @Autowired
    private AccountService accountService;
    @Value("${idms.auth.user-name}")
    private String userName;

    @Value("${idms.auth.password}")
    private String password;

    @Value("${idms.layout-id}")
    private Integer layoutId;

    @Value("${idms.account-status}")
    private String accountStatus;
    @Value("${idms.institution-id}")
    private Integer institutionId;
    @Value("${idms.page-number}")
    private Integer pageNumber;

    public String fetchData() {
        AuthenticateResult authenticateResult = idmsClient.authenticateIDMS(userName, password, institutionId);
        if(authenticateResult.getStatus() != HttpStatus.OK.value()){
            return "Authentication to IDMS failed";
        }
        String authToken = authenticateResult.getToken();
        AccountResult accountList = idmsClient.getAccountList(authToken, accountStatus, institutionId, layoutId,pageNumber);

        if(!String.valueOf(HttpStatus.OK.value()).equals(accountList.getStatus())){
            return "Got failure response from IDMS: "+accountList.getStatus();
        }
        List<AccountRowDTO> accountRowDTOS = accountList.getData().stream().map(AccountDTO::getRow).toList();
        if(!CollectionUtils.isEmpty(accountRowDTOS)){
            accountService.processAccountInfoIntoDB(accountRowDTOS);
        }
        return "Success";
    }


}
