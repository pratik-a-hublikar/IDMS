package com.idms.service;

import com.idms.commons.constant.ConstantMessages;
import com.idms.commons.exception.AuthenticationException;
import com.idms.commons.utils.JWTUtils;
import com.idms.dao.entity.User;
import com.idms.dao.repository.UserRepository;
import com.idms.model.request.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Value("${jwt.token.expiry-in-min}")
    private Long tokenExpiry;

    public String validateAndProcessLogin(LoginRequestDTO loginRequest) {
        User oneByUserName = userRepository.findOneByUserName(loginRequest.getUsername());

        if(null == oneByUserName || !oneByUserName.isActive() ||
                !oneByUserName.getPassword().equals(loginRequest.getPassword())){
            throw new AuthenticationException(ConstantMessages.LOGIN_FAILED_MESSAGE);
        }
        return jwtUtils.generateJwtToken(oneByUserName.getUserName(), ConstantMessages.LOGIN_TOKEN, tokenExpiry);
    }


}
