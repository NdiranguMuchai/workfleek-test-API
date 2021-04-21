package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.model.Webuser;

public interface WebUserService {
    Webuser create(Webuser webuser) throws Exception;
    Webuser update(Webuser webuser) throws Exception;
}
