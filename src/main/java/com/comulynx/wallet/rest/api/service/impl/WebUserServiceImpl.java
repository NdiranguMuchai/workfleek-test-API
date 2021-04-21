package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Webuser;
import com.comulynx.wallet.rest.api.repository.WebuserRepository;
import com.comulynx.wallet.rest.api.service.WebUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WebUserServiceImpl implements WebUserService {
    private final WebuserRepository webuserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebUserServiceImpl(WebuserRepository webuserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.webuserRepository = webuserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        String encodedPassword= bCryptPasswordEncoder.encode(webuser.getPassword());

        webuser.setPassword(encodedPassword);;


        return webuserRepository.save(webuser);
    }

    @Override
    public Webuser update(String employeeId) throws ResourceNotFoundException{

        Webuser webuser =  webuserRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));

        webuser.setEmail(webuser.getEmail());
        webuser.setLastName(webuser.getLastName());
        webuser.setFirstName(webuser.getFirstName());
        return webuserRepository.save(webuser);
    }

    @Override
    public void delete(String employeeId) throws ResourceNotFoundException {
        Webuser webuser =  webuserRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));

        webuserRepository.delete(webuser);
    }

    @Override
    public List<Webuser> list() {
        return webuserRepository.findAll();
    }

    @Override
    public Webuser findByEmployeeId(String employeeId) throws ResourceNotFoundException {
        return webuserRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));
    }
}
