package com.ramdan.trainingkaryawan.repository.oauth;

import com.ramdan.trainingkaryawan.model.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}
