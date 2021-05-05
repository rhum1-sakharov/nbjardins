package org.rlsv.adapters.secondaries.dataproviderjpa.helpers.personnes.clients;

import keys.client.ClientKey;
import models.search.filter.Filter;
import models.search.filter.FilterString;
import models.search.sort.Sort;
import org.rlsv.adapters.secondaries.dataproviderjpa.helpers.HelperPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.Path;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.SortPath;

import java.util.ArrayList;
import java.util.List;


public class ClientPath extends HelperPath {

    private Path clientId = new Path("client", "id", true);
    private Path clientArtisanId = new Path("client.artisan", "id", false);
    private Path clientNom = new Path("client", "nom", true);
    private Path clientPrenom = new Path("client", "prenom", true);
    private Path clientAdresse = new Path("client", "adresse", true);
    private Path clientVille = new Path("client", "ville", true);
    private Path clientCodePostal = new Path("client", "codePostal", true);
    private Path clientTelephone = new Path("client", "telpehone", true);
    private Path clientEmail = new Path("client", "email", true);
    private Path clientSignature = new Path("client", "signature", true);
    private Path clientSiret = new Path("client", "siret", true);
    private Path clientSociete = new Path("client", "societe", true);
    private Path clientFonction = new Path("client", "fonction", true);


    public String firstLine() {
        return "select client from Client client";
    }

    public List<FilterPath> buildFilterPathList(List<Filter> filters) {

        List<FilterPath> filterPathList = new ArrayList<>();

        for (Filter filter : filters) {

            switch (filter.getKey()) {

                case ClientKey.ID:
                    FilterPath<FilterString> filterId = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientId)
                            .build();
                    filterPathList.add(filterId);
                    break;
                case ClientKey.ID_ARTISAN:
                    FilterPath<FilterString> filterIdArtisan = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientArtisanId)
                            .build();
                    filterPathList.add(filterIdArtisan);
                    break;
                case ClientKey.NOM:
                    FilterPath<FilterString> filterNom = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientNom)
                            .build();
                    filterPathList.add(filterNom);
                    break;
                case ClientKey.PRENOM:
                    FilterPath<FilterString> filterPrenom = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientPrenom)
                            .build();
                    filterPathList.add(filterPrenom);
                    break;
                case ClientKey.ADRESSE:
                    FilterPath<FilterString> filterAdresse = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientAdresse)
                            .build();
                    filterPathList.add(filterAdresse);
                    break;
                case ClientKey.VILLE:
                    FilterPath<FilterString> filterVille = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientVille)
                            .build();
                    filterPathList.add(filterVille);
                    break;
                case ClientKey.CODE_POSTAL:
                    FilterPath<FilterString> filterCodePostal = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientCodePostal)
                            .build();
                    filterPathList.add(filterCodePostal);
                    break;
                case ClientKey.TELEPHONE:
                    FilterPath<FilterString> filterTelephone = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientTelephone)
                            .build();
                    filterPathList.add(filterTelephone);
                    break;
                case ClientKey.EMAIL:
                    FilterPath<FilterString> filterEmail = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientEmail)
                            .build();
                    filterPathList.add(filterEmail);
                    break;
                case ClientKey.SIGNATURE:
                    FilterPath<FilterString> filterSignature = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientSignature)
                            .build();
                    filterPathList.add(filterSignature);
                    break;
                case ClientKey.SIRET:
                    FilterPath<FilterString> filterSiret = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientSiret)
                            .build();
                    filterPathList.add(filterSiret);
                    break;
                case ClientKey.SOCIETE:
                    FilterPath<FilterString> filterSociete = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientSociete)
                            .build();
                    filterPathList.add(filterSociete);
                    break;
                case ClientKey.FONCTION:
                    FilterPath<FilterString> filterFonction = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(clientFonction)
                            .build();
                    filterPathList.add(filterFonction);
                    break;

            }

        }

        return filterPathList;
    }

    public List<SortPath> buildSortPathList(List<Sort> sorts) {

        List<SortPath> sortPathList = new ArrayList<>();

        for (Sort sort : sorts) {

            switch (sort.getKey()) {

                case ClientKey.ID:
                    SortPath sortId = SortPath.builder()
                            .sort(sort)
                            .path(clientId)
                            .build();
                    sortPathList.add(sortId);
                    break;

                case ClientKey.ID_ARTISAN:
                    SortPath sortIdArtisan = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientArtisanId)
                            .build();
                    sortPathList.add(sortIdArtisan);
                    break;
                case ClientKey.NOM:
                    SortPath sortNom = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientNom)
                            .build();
                    sortPathList.add(sortNom);
                    break;
                case ClientKey.PRENOM:
                    SortPath sortPrenom = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientPrenom)
                            .build();
                    sortPathList.add(sortPrenom);
                    break;
                case ClientKey.ADRESSE:
                    SortPath sortAdresse = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientAdresse)
                            .build();
                    sortPathList.add(sortAdresse);
                    break;
                case ClientKey.VILLE:
                    SortPath sortVille = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientVille)
                            .build();
                    sortPathList.add(sortVille);
                    break;
                case ClientKey.CODE_POSTAL:
                    SortPath sortCodePostal = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientCodePostal)
                            .build();
                    sortPathList.add(sortCodePostal);
                    break;
                case ClientKey.TELEPHONE:
                    SortPath sortTelephone = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientTelephone)
                            .build();
                    sortPathList.add(sortTelephone);
                    break;
                case ClientKey.EMAIL:
                    SortPath sortEmail = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientEmail)
                            .build();
                    sortPathList.add(sortEmail);
                    break;
                case ClientKey.SIGNATURE:
                    SortPath sortSignature = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientSignature)
                            .build();
                    sortPathList.add(sortSignature);
                    break;
                case ClientKey.SIRET:
                    SortPath sortSiret = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientSiret)
                            .build();
                    sortPathList.add(sortSiret);
                    break;
                case ClientKey.SOCIETE:
                    SortPath sortSociete = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientSociete)
                            .build();
                    sortPathList.add(sortSociete);
                    break;
                case ClientKey.FONCTION:
                    SortPath sortFonction = SortPath.<FilterString>builder()
                            .sort(sort)
                            .path(clientFonction)
                            .build();
                    sortPathList.add(sortFonction);
                    break;
            }

        }

        return sortPathList;
    }

}
