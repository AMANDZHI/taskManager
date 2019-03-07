package com.company.api;

import java.util.List;
import java.util.Optional;

public interface Service<T, M> {
    M save(M object);
    Optional<M> findByName(T name);
    Optional<M> findByNameAndUserId(T name, T userId);
    Optional<M> findById(T id);
    Optional<M> findByIdAndUserId(T id, T userId);
    M update(M object);
    boolean removeByName(T name);
    boolean removeByNameAndUserId(T name, T userId);
    void removeById(T id);
    boolean removeByIdAndUserId(T id, T userId);
    List<M> getList();
    List<M> getListByUserId(T id);
}
