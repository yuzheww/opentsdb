package net.opentsdb.query.processor.expressions2.eval;

public class EvaluationOptions {
    public static class Builder {
        private boolean infectiousNaN;
        private boolean forceFloatingPointDivision;
        private boolean allowMetricReuse;

        public Builder() {
            infectiousNaN = false;
            forceFloatingPointDivision = true;
            allowMetricReuse = false;
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

        /**
         * If flag is false, which is the default value, then any evaluation
         * will consume metrics from the EvaluationContext. This minimizes
         * copies, but you cannot reuse the context object.
         *
         * If flag is true, then evaluation will make copies of all metrics
         * defined in the EvaluationContext as needed so that the same context
         * object may be used for multiple evaluations.
         * @param flag
         */
        public Builder setAllowMetricReuse(final boolean flag) {
            this.allowMetricReuse = flag;
            return this;
        }

        public EvaluationOptions build() {
            return new EvaluationOptions(this);
        }
    }

    private final boolean infectiousNaN;
    private final boolean forceFloatingPointDivision;
    private final boolean allowMetricReuse;

    private EvaluationOptions(final Builder builder) {
        this.infectiousNaN = builder.infectiousNaN;
        this.forceFloatingPointDivision = builder.forceFloatingPointDivision;
        this.allowMetricReuse = builder.allowMetricReuse;
    }

    public boolean getInfectiousNaN() {
        return infectiousNaN;
    }

    public boolean getForceFloatingPointDivision() {
        return forceFloatingPointDivision;
    }

    public boolean getAllowMetricReuse() {
        return allowMetricReuse;
    }
}
