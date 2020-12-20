package ports.repositories;

import domain.models.Domain;

import java.util.Set;

public interface DataServicePT<T extends Domain> {

    T find(String id);

    Set<T> findAll();

    T save(T instance);

    Set<T> saveAll(Set<T> instances);

    void delete(String id);

    void deleteAll();




}
