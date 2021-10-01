package net.opentsdb.query.processor.expressions2;

import net.opentsdb.query.processor.expressions2.types.ExpressionType;
import net.opentsdb.query.processor.expressions2.types.FunctionType;

/**
 * TODO
 */
public class FunctionTypeError extends ExpressionException {
    private final String message;
    private final String name;
    private final FunctionType[] signatures;
    private final ExpressionType actualType;

    /**
     * C-tor.
     * @param message Human-readable description.
     * @param name Of this function.
     * @param signatures That are valid for this function.
     * @param actualType That the actual type of the user arguments given.
     */
    public FunctionTypeError(final String message, final String name,
            final FunctionType[] signatures, final ExpressionType actualType) {
        super();
        this.message = message;
        this.name = name;
        this.signatures = signatures;
        this.actualType = actualType;
    }

    public String getName() {
        return name;
    }

    public FunctionType[] getSignatures() {
        return signatures;
    }

    public ExpressionType getActualType() {
        return actualType;
    }

    public String getFunctionKind() {
        return "function";
    }

    public String getArgumentTerm() {
        return "argument";
    }

    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("for %s '%s': %s", getFunctionKind(), name, message)).
            append(System.lineSeparator());

        sb.append("given ").append(getArgumentTerm()).append("(s) of type: ").append(actualType.toString()).
            append(System.lineSeparator());

        sb.append("expected any of the following: ");
        boolean first = true;
        for (int i = 0; i < signatures.length; ++i) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(signatures[i].getDomain().toString());
        }

        return sb.toString();
    }
}
