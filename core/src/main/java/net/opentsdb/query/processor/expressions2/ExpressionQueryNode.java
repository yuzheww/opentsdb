package net.opentsdb.query.processor.expressions2;

import net.opentsdb.query.AbstractQueryNode;
import net.opentsdb.query.QueryNodeFactory;
import net.opentsdb.query.QueryPipelineContext;
import net.opentsdb.query.QueryResult;
import net.opentsdb.query.QueryResultId;
import net.opentsdb.query.processor.expressions.ExpressionConfig;
import net.opentsdb.query.processor.expressions2.nodes.ExpressionNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionQueryNode extends AbstractQueryNode<ExpressionNode> {
    private static final Logger LOG = LoggerFactory.getLogger(
        ExpressionQueryNode.class);

    /** The parsed config for this particular node. */
    protected final ExpressionConfig expression_config;

    /** The map of result IDs to results. */
    protected final Map<QueryResultId, QueryResult> results;

    /**
     * Default c-tor.
     * @param factory The factory we came from.
     * @param context The non-null context.
     * @param exprConf The non-null expression config.
     */
    public BinaryExpressionNode(final QueryNodeFactory factory,
                                final QueryPipelineContext context, 
                                final ExpressionConfig exprConf) {
        super(factory, context);
        if (null == exprConf) {
            throw new IllegalArgumentException("Expression config cannot be null.");
        }
    }

    @Override
    public ExpressionParseNode config() {
        return expression_config;
    }

    @Override
    public void close() {
      if (!results.isEmpty()) {
        for (final QueryResult result : results.values()) {
          if (result != null) {
            result.close();
          }
        }
      }
    }
    
    @Override
    public void onNext(final QueryResult next) {
        // TODO
    }
}
