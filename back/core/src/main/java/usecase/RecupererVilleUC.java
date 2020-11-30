package usecase;

import domain.entities.Ville;
import domain.entityresponse.Response;
import domain.utils.Utils;
import usecase.ports.DataServicePT;
import usecase.ports.LocalizeServicePT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecupererVilleUC {

    LocalizeServicePT localizeServicePT;
    DataServicePT dataServicePT;

    public RecupererVilleUC(LocalizeServicePT localizeServicePT, DataServicePT dataServicePT) {
        this.localizeServicePT = localizeServicePT;
        this.dataServicePT = dataServicePT;
    }

    public Response<Ville> findByNom(String nom) {

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire"), Objects.isNull(nom));

        Response<Ville> entityResponse = Utils.initResponse(preconditions);

        if (!entityResponse.isError()) {

            List<Ville> villeList = dataServicePT.findVilleListByNomContains(nom);
            entityResponse = Utils.initResponse(localizeServicePT.getMsg("aucun.resultat"), Objects.isNull(villeList) || villeList.size() == 0);
            entityResponse.setResultList(villeList);

        }


        return entityResponse;
    }
}
