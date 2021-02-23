package usecases.referentiel.roles;

import aop.Transactionnal;
import domains.PersonneDN;
import domains.RoleDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.RoleRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;

public class FindByPersonneUE extends AbstractUsecase {

    RoleRepoPT roleRepo;

    public FindByPersonneUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager,  RoleRepoPT roleRepo) {
        super(localizeService, transactionManager);
        this.roleRepo = roleRepo;
    }

    @Transactionnal
    public List<RoleDN> execute(DataProviderManager dpm, PersonneDN personne) throws CleanException {

        return roleRepo.findByPersonne(dpm,personne);
    }
}
