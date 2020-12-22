package usecase;

import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;

public interface IUsecase {

    ResponseDN execute(RequestDN instance) throws Exception;
}
