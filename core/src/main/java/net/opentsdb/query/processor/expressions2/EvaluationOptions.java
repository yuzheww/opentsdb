package net.opentsdb.query.processor.expressions2;

public class EvaluationOptions {
    public static class Builder {
        private boolean infectiousNaN;

        public Builder() {
            infectiousNaN = false;
        }

        public Builder setInfectiousNaN(final boolean flag) {
            this.infectiousNaN = flag;
            return this;
        }

        public EvaluationOptions build() {
            return new EvaluationOptions(this);
        }
    }

    private final boolean infectiousNaN;

    private EvaluationOptions(final Builder builder) {
        this.infectiousNaN = builder.infectiousNaN;
    }

    public boolean getInfectiousNaN() {
        return infectiousNaN;
    }
}
