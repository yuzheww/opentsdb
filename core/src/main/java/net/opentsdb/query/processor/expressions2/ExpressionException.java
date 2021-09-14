package net.opentsdb.query.processor.expressions2;

public class ExpressionException extends RuntimeException {
    public ExpressionException() {
        super();
    }

    public ExpressionException(final String message) {
        super(message);
    }
}
