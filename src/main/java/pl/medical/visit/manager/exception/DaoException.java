package pl.medical.visit.manager.exception;

public class DaoException extends RuntimeException {

    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
