package wordy.ast;

import java.util.Map;
import java.util.Objects;

import wordy.interpreter.EvaluationContext;

import static wordy.ast.Utils.orderedMap;

/**
 * Two expressions joined by an operator (e.g. “x plus y”) in a Wordy abstract syntax tree.
 */
public class BinaryExpressionNode extends ExpressionNode {
    public enum Operator {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EXPONENTIATION
    }

    private final Operator operator;
    private final ExpressionNode lhs, rhs;

    public BinaryExpressionNode(Operator operator, ExpressionNode lhs, ExpressionNode rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Map<String, ASTNode> getChildren() {
        return orderedMap(
            "lhs", lhs,
            "rhs", rhs);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        BinaryExpressionNode that = (BinaryExpressionNode) o;
        return this.operator == that.operator
            && this.lhs.equals(that.lhs)
            && this.rhs.equals(that.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, lhs, rhs);
    }

    @Override
    public String toString() {
        return "BinaryExpressionNode{"
            + "operator=" + operator
            + ", lhs=" + lhs
            + ", rhs=" + rhs
            + '}';
    }

    @Override
    protected String describeAttributes() {
        return "(operator=" + operator + ')';
    }

    @Override
    protected double doEvaluate(EvaluationContext context) {

        switch (operator) {
            case ADDITION:
                return lhs.doEvaluate(context) + rhs.doEvaluate(context);
        
            case SUBTRACTION:
                return lhs.doEvaluate(context) - rhs.doEvaluate(context);

            case MULTIPLICATION:
                return lhs.doEvaluate(context) * rhs.doEvaluate(context);

            case DIVISION:
                return lhs.doEvaluate(context) / rhs.doEvaluate(context);

            case EXPONENTIATION:
                double result = lhs.doEvaluate(context);
                for (int i = 1; i < rhs.doEvaluate(context); i ++) {
                    result = result * lhs.doEvaluate(context);
                }
                return result;
            
            default:
                throw new UnsupportedOperationException("Error operator " + getClass().getSimpleName());
        }        
    }
}
