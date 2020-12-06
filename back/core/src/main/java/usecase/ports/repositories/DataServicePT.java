package usecase.ports.repositories;

import domain.entities.Entity;

import java.util.Set;

public interface DataServicePT<T extends Entity> {

    T find(String id);

    Set<T> findAll();

    T save(T instance);

    T savelAll(Set<T> instances);

    void delete(String id);

    void deleteAll();


}
