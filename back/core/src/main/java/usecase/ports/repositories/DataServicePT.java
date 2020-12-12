package usecase.ports.repositories;

import domain.models.EntityDN;

import java.util.Set;

public interface DataServicePT<T extends EntityDN> {

    T find(String id);

    Set<T> findAll();

    T save(T instance);

    T savelAll(Set<T> instances);

    void delete(String id);

    void deleteAll();


}
