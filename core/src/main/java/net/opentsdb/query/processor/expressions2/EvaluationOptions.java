package net.opentsdb.query.processor.expressions2;

public class EvaluationOptions {
    public static class Builder {
        private boolean infectiousNaN;
        private boolean useFloatingPointDivision;

        public Builder() {
            infectiousNaN = false;
            useFloatingPointDivision = true;
        }

        public Builder setInfectiousNaN(final boolean flag) {
            this.infectiousNaN = flag;
            return this;
        }

        public Builder setUseFloatingPointDivision(final boolean flag) {
            this.useFloatingPointDivision = flag;
            return this;
        }

        public EvaluationOptions build() {
            return new EvaluationOptions(this);
        }
    }

    private final boolean infectiousNaN;
    private final boolean useFloatingPointDivision;

    private EvaluationOptions(final Builder builder) {
        this.infectiousNaN = builder.infectiousNaN;
        this.useFloatingPointDivision = builder.useFloatingPointDivision;
    }

    public boolean getInfectiousNaN() {
        return infectiousNaN;
    }

    public boolean getUseFloatingPointDivision() {
        return useFloatingPointDivision;
    }
}
