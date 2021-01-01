package usecases;

import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;

public interface IUsecase {

    ResponseDN execute(RequestMap requestMap) throws Exception;
}
