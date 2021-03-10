package usecases.referentiel.roles;

import aop.Transactional;
import domains.personnes.PersonneDN;
import domains.referentiel.roles.RoleDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.roles.RoleRepoPT;
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

    @Transactional
    public List<RoleDN> execute(DataProviderManager dpm, PersonneDN personne) throws CleanException {

        return roleRepo.findByPersonne(dpm,personne);
    }
}
