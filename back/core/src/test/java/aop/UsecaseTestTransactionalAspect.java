package aop;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ports.transactions.TransactionManagerPT;

@RunWith(MockitoJUnitRunner.class)
public class UsecaseTestTransactionalAspect {

    UsecaseTransactionalAspect usecaseTransactionalAspect;

    @Mock
    TransactionManagerPT tm;


    @Before
    public void setUp() {
        usecaseTransactionalAspect = new UsecaseTransactionalAspect();
    }

    @Test
    public void execute_should_return_exception_when_args_have_tm_with_null() {

        Assertions.assertThatThrownBy(() -> usecaseTransactionalAspect.execute())
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining("no transaction manager provided");
    }

    @Test
    public void execute_should_return_exception_when_args_have_no_dpm() {

        Assertions.assertThatThrownBy(() -> usecaseTransactionalAspect.execute())
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining("no dpm provided");
    }




}