package org.rlsv.adapters.primaries.application.springapp.config.security;

import domains.PersonneDN;
import domains.RoleDN;
import exceptions.CleanException;
import localizations.MessageKeys;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ports.localization.LocalizeServicePT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.RoleRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private PersonneRepoPT personneRepo;
    private RoleRepoPT roleRepo;
    private LocalizeServicePT localizeService;
    private TransactionManagerPT transactionManager;

    public CustomUserDetailsService(PersonneRepoPT personneRepo, RoleRepoPT roleRepo, LocalizeServicePT localizeService, TransactionManagerPT transactionManager) {
        this.personneRepo = personneRepo;
        this.roleRepo = roleRepo;
        this.localizeService = localizeService;
        this.transactionManager = transactionManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        DataProviderManager dpm = null;
        try {

            dpm = transactionManager.createDataProviderManager(null);

            PersonneDN personne = this.personneRepo.findByEmail(dpm, email);

            if (Objects.isNull(personne)) {
                throw new UsernameNotFoundException(String.format(localizeService.getMsg(String.format(MessageKeys.USER_NOT_FOUND, email))));
            }

            List<RoleDN> roleDNList = roleRepo.findByPersonne(dpm, personne);
            if (CollectionUtils.isEmpty(roleDNList)) {
                throw new UsernameNotFoundException(String.format(localizeService.getMsg(String.format(MessageKeys.USER_HAS_NO_ROLE, email))));
            }

            return new User(roleDNList.stream().map(item -> item.getNom()).collect(Collectors.toList()), personne);

        } catch (CleanException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            transactionManager.close(dpm);
        }

        return null;
    }
}
