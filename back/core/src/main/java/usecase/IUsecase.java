package usecase;

import domain.models.Domain;
import domain.response.ResponseDN;

public interface IUsecase<T extends Domain> {

    ResponseDN<T> execute(T instance);
}
