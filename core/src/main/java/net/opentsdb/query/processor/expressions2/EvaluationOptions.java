package net.opentsdb.query.processor.expressions2;

public class EvaluationOptions {
    public static class Builder {
        private boolean infectiousNaN;
        private boolean forceFloatingPointDivision;

        public Builder() {
            infectiousNaN = false;
            forceFloatingPointDivision = true;
        }

        /**
         * @param flag
         */
        public Builder setInfectiousNaN(final boolean flag) {
            this.infectiousNaN = flag;
            return this;
        }

        /**
         * If flag is true, which is the default value, then evaluation will
         * always use floating-point division, even with long-valued operands.
         * @param flag
         */
        public Builder setForceFloatingPointDivision(final boolean flag) {
            this.forceFloatingPointDivision = flag;
            return this;
        }

        public EvaluationOptions build() {
            return new EvaluationOptions(this);
        }
    }

    private final boolean infectiousNaN;
    private final boolean forceFloatingPointDivision;

    private EvaluationOptions(final Builder builder) {
        this.infectiousNaN = builder.infectiousNaN;
        this.forceFloatingPointDivision = builder.forceFloatingPointDivision;
    }

    public boolean getInfectiousNaN() {
        return infectiousNaN;
    }

    public boolean getForceFloatingPointDivision() {
        return forceFloatingPointDivision;
    }
}
