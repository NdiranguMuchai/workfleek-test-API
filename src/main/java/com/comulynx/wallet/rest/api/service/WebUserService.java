package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Webuser;

import java.util.List;

public interface WebUserService {
    Webuser create(Webuser webuser) throws Exception;
    Webuser update(String employeeId) throws ResourceNotFoundException;
    void delete(String employeeId) throws ResourceNotFoundException;
    List<Webuser> list();
    Webuser findByEmployeeId(String employeeId) throws ResourceNotFoundException;
}
