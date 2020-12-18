package usecase.devis;

import domain.models.DevisDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import usecase.AbstractUsecase;
import usecase.IUsecase;

public class CreerDevis extends AbstractUsecase implements IUsecase<DevisDN> {


    public CreerDevis(LocalizeServicePT localizeService) {
        super(localizeService);
    }

    @Override
    public ResponseDN<DevisDN> execute(RequestDN<DevisDN> instance) {
        return null;
    }
}
