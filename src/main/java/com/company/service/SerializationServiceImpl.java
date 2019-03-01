package com.company.service;

import com.company.api.SerializationRepository;
import com.company.api.SerializationService;
import com.company.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerializationServiceImpl implements SerializationService {

    @Autowired
    private SerializationRepository serializationRepository;

    @Override
    public void writeObjectToFile(String path, List list) {
        serializationRepository.writeObjectToFile(path, list);
    }

    @Override
    public List readFileToObject(String path) {
        return serializationRepository.readFileToObject(path);
    }

    @Override
    public void writeAllToJson(String path, Domain domain) {
        serializationRepository.writeAllToJson(path, domain);
    }

    @Override
    public void writeAllToXml(String path, Domain domain) {
        serializationRepository.writeAllToXml(path, domain);
    }

    @Override
    public Domain readJsonToObjects(String path) {
        return serializationRepository.readJsonToObjects(path);
    }

    @Override
    public Domain readXmlToObjects(String path) {
        return serializationRepository.readXmlToObjects(path);
    }
}
