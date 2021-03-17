package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;
import ports.localization.LocalizeServicePT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static localizations.MessageKeys.LIST_IS_EMPTY;

@RunWith(MockitoJUnitRunner.class)
public class RepoARTest {


    @Mock
    RepoAR repo;


    @Mock
    LocalizeServicePT ls;

    DataProviderManager dpm;


    @Mock
    EntityManager em;

    @Mock
    TypedQuery<Devis> query;

    @Before
    public void setUp() throws Exception {

        dpm = new DataProviderManager();
        dpm.setManager(em);

        this.repo.localizeService =this.ls;


    }

    @Test
    public void deleteById_should_not_have_null_args() throws TechnicalException {

        final String errMsg1 = "L'argument id est obligatoire.";
        final String errMsg2 = "L'argument clazz est obligatoire.";
        final String errMsg3 = "L'argument dataProviderManager est obligatoire.";

        Mockito.when(this.repo.deleteById(null, null, null)).thenCallRealMethod();

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "id"))
                .thenReturn(errMsg1);

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "clazz"))
                .thenReturn(errMsg2);

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "dataProviderManager"))
                .thenReturn(errMsg3);

        Assertions.assertThatCode(() -> this.repo.deleteById(null, null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1)
                .hasMessageContaining(errMsg2)
                .hasMessageContaining(errMsg3)
        ;

    }

    @Test
    public void deleteById_should_throw_error_if_deleteByIdList_is_empty_or_null() throws TechnicalException {

        final String errMsg1 = "La liste ids est vide.";

        Mockito.when(this.ls.getMsg(LIST_IS_EMPTY, "ids"))
                .thenReturn(errMsg1);

        Mockito.when(repo.deleteByIdList(dpm,Devis.class,Arrays.asList("1")))
                .thenReturn(null);

        Mockito.when(repo.deleteById(dpm,Devis.class,"1")).thenCallRealMethod();

        Assertions.assertThatCode(() -> this.repo.deleteById(dpm, Devis.class, "1"))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1)
        ;

    }
}