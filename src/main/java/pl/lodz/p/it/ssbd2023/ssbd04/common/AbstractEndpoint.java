package pl.lodz.p.it.ssbd2023.ssbd04.common;

import jakarta.persistence.OptimisticLockException;
import lombok.Getter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import pl.lodz.p.it.ssbd2023.ssbd04.common.TransactionInterface;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;

public class AbstractEndpoint implements TransactionInterface {
    Config config = ConfigProvider.getConfig();
    private int howMuchRetryTXCounter = config.getValue("transaction.repeat", Integer.class);

    @Getter
    private boolean lastTXRollback;

//    @Inject
//    private ConfigLoader configLoader;
//
//
//    @PostConstruct
//    public void init() {
//        transactionRepetitionLimit = configLoader.getTransactionRepetitionLimit();
//    }

    protected void repeatTransaction(VoidExecutor executor, TransactionInterface transactionComponent) throws BaseApplicationException {
        boolean isrollbackTX;
        do {
            try {
                executor.execute();
                isrollbackTX = transactionComponent.isLastTXRollback();
            } catch (AppOptimisticLockException | OptimisticLockException e) {
                isrollbackTX = true;
                if (howMuchRetryTXCounter < 3) {
                    throw new AppOptimisticLockException();
                }
            }
        } while (isrollbackTX && --howMuchRetryTXCounter > 0);
        if (isrollbackTX) {
            throw BaseApplicationException.createTransactionRollbackException();
        }
    }

    protected void repeatTransactionAgregate(VoidExecutor executor, TransactionInterface transactionComponent) throws BaseApplicationException {
        boolean isrollbackTX;
        do {
            try {
                executor.execute();
                isrollbackTX = transactionComponent.isLastTXRollback();
            } catch (OptimisticLockException e) {
                isrollbackTX = true;
            } catch (AppOptimisticLockException aopt) {
                isrollbackTX = true;
                if (howMuchRetryTXCounter < 3) {
                    throw new AppOptimisticLockException();
                }
            }
        } while (isrollbackTX && --howMuchRetryTXCounter > 0);
        if (isrollbackTX) {
            throw BaseApplicationException.createTransactionRollbackException();
        }
    }


    protected <T> T repeatTransaction(ReturnExecutor<T> executor, TransactionInterface transactionComponent) throws BaseApplicationException {
        boolean isrollbackTX;
        T result = null;
        do {
            try {
                result = executor.execute();
                isrollbackTX = transactionComponent.isLastTXRollback();
            } catch (AppOptimisticLockException | OptimisticLockException e) {
                isrollbackTX = true;
                if (howMuchRetryTXCounter < 3) {
                    throw new AppOptimisticLockException();
                }
            }
        } while (isrollbackTX && --howMuchRetryTXCounter > 0);
        if (isrollbackTX) {
            throw BaseApplicationException.createTransactionRollbackException();
        }
        return result;
    }

    @FunctionalInterface
    public interface VoidExecutor {
        void execute() throws BaseApplicationException;
    }

    /**
     * Interfejs funkcyjny metod określonego typu.
     */
    @FunctionalInterface
    public interface ReturnExecutor<T> {
        T execute() throws BaseApplicationException;
    }
}
