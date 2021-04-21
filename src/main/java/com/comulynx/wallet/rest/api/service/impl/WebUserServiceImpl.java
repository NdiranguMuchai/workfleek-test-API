package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.model.Webuser;
import com.comulynx.wallet.rest.api.repository.WebuserRepository;
import com.comulynx.wallet.rest.api.service.WebUserService;
import org.springframework.stereotype.Service;

@Service
public class WebUserServiceImpl implements WebUserService {
    private final WebuserRepository webuserRepository;

    public WebUserServiceImpl(WebuserRepository webuserRepository) {
        this.webuserRepository = webuserRepository;
    }

    @Override
    public Webuser create(Webuser webuser) throws Exception{
        if (webuserRepository.existsByUsernameOrEmailOrCustomerIdOrEmployeeId(
                webuser.getUsername(),
                webuser.getEmail(),
                webuser.getCustomerId(),
                webuser.getEmployeeId())){
            throw new Exception("Web user already exists");
        }

        return webuserRepository.save(webuser);
    }

    @Override
    public Webuser update(Webuser webuser) {
        return null;
    }
}
