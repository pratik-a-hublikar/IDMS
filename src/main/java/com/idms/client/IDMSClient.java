package com.idms.client;

import com.idms.model.response.AccountResult;
import com.idms.model.response.AuthenticateResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "idms-client",value= "idms-client",url = "${idms.base.url}")
public interface IDMSClient {


    @GetMapping(path = "/api/authenticate/GetUserAuthorizationToken?username={userName}&password={password}&InstitutionID={institutionID}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthenticateResult authenticateIDMS(@PathVariable("userName") String userName, @PathVariable("password") String password, @PathVariable("institutionID") int institutionID);


    @GetMapping(path = "/api/Account/GetAccountList",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    AccountResult getAccountList(@RequestParam("Token") String token,
                                 @RequestParam("AccountStatus") String accountStatus,
                                 @RequestParam("InstitutionID") int institutionID,
                                 @RequestParam("LayoutID") int layoutID,
                                 @RequestParam("PageNumber") int pageNumber);
}
