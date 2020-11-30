package usecase.ports;

import domain.entities.Ville;

import java.util.List;

public interface DataServicePT {

    List<Ville> findVilleListByNomContains(String nom);
}
