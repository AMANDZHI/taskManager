package com.company.repository;

import com.company.api.SerializationRepository;
import com.company.model.Domain;
import com.company.model.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SerializationRepositoryImpl implements SerializationRepository {

    @Override
    @SneakyThrows
    public void writeObjectToFile(String path, List list) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(list);
        }
    }

    @Override
    @SneakyThrows
    public List readFileToObject(String path) {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            List<Project> list = new ArrayList<>();
            list = ((ArrayList<Project>) objectInputStream.readObject());
            return list;
        }
    }

    @Override
    @SneakyThrows
    public void writeAllToJson(String path, Domain domain) {
        ObjectMapper mapper = new ObjectMapper();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(fileOutputStream, domain);
    }

    @Override
    @SneakyThrows
    public void writeAllToXml(String path, Domain domain) {
        ObjectMapper mapper = new XmlMapper();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(fileOutputStream, domain);
    }

    @Override
    @SneakyThrows
    public Domain readJsonToObjects(String path) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), Domain.class);
    }

    @Override
    @SneakyThrows
    public Domain readXmlToObjects(String path) {
        ObjectMapper mapper = new XmlMapper();
        return mapper.readValue(new File(path), Domain.class);
    }
}
