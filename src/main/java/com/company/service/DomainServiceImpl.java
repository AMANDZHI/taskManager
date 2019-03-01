package com.company.service;

import com.company.api.DomainRepository;
import com.company.api.DomainService;
import com.company.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Override
    public Domain getDomain() {
        return domainRepository.getDomain();
    }
}