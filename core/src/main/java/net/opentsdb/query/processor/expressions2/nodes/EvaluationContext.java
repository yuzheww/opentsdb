package net.opentsdb.query.processor.expressions2.nodes;

public class EvaluationContext {
    public class Builder {
        private boolean infectiousNaN;

        public Builder() {
        }

        public void setInfectiousNaN(final boolean flag) {
            this.infectiousNaN = flag;
        }

        public EvaluationContext build() {
            return new EvaluationContext(this);
        }
    }

    private final boolean infectiousNaN;

    private EvaluationContext(final Builder builder) {
        this.infectiousNaN = builder.infectiousNaN;
    }

    public boolean getInfectiousNaN() {
        return infectiousNaN;
    }
}
