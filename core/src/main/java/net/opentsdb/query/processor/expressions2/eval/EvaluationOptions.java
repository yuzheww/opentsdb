package net.opentsdb.query.processor.expressions2.eval;

public class EvaluationOptions {
    public class Builder {
        private boolean infectiousNaN;

        public Builder() {
        }

        public void setInfectiousNaN(final boolean flag) {
            this.infectiousNaN = flag;
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
