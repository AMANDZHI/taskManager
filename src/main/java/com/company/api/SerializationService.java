package com.company.api;

import com.company.model.Domain;

import java.util.List;

public interface SerializationService<T> {
    void writeObjectToFile(String path, List<T> list);
    List<T> readFileToObject(String path);
    void writeAllToJson(String path, Domain domain);
    void writeAllToXml(String path, Domain domain);
    Domain readJsonToObjects(String path);
    Domain readXmlToObjects(String path);
}