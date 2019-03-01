package com.company.api;

import javax.jws.WebService;

@WebService
public interface SerializationWebServiceEndpoint {
    void writeObjectsToFiles();
    void readFilesToObjects();
    void writeAllToJson();
    void writeAllToXml();
    void readJsonToObjects();
    void readXmlToObjects();
}
