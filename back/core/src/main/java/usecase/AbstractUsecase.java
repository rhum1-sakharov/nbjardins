package usecase;

import ports.localization.LocalizeServicePT;

public class AbstractUsecase {

    protected LocalizeServicePT localizeService;

    public AbstractUsecase(LocalizeServicePT localizeService) {
        this.localizeService = localizeService;
    }
}
