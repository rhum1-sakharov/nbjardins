package usecase;

import domain.models.Domain;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;

public interface IUsecase<T extends Domain> {

    ResponseDN<T> execute(RequestDN<T> instance);
}
