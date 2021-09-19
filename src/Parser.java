import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Parser {

    private final ParsingTable parsingTable = new ParsingTable();

    private final List<List<Symbol>> productions = Production.getProductions();

    private final List<Set<List<Integer>>> canonCollection = parsingTable.getCanonCollection();


    public Stack<ParseTree> parse(List<Token> inputStream) throws Exception {
        Stack<ParseTree> parseTree = new Stack<>();

        // stack used to hold (integers representing) states in the automaton
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        int i = 0;
        while(true) {

            // get the next token in the input stream, if relevant
            Token v;
            if (i < inputStream.size()) {
                v = inputStream.get(i);
            } else if (i == inputStream.size()) {
                v = new Token(Symbol.end, "$");
            } else {
                throw new Exception("Syntax Error");
            }

            Action action = parsingTable.action(stack.peek(), v.type);
            if (action.getType() == ActionType.Shift) {
                // add state to stack
                stack.push(action.getIndex());
                // add symbol to parse tree as a leaf
                parseTree.push(new ParseTree(v.type));
                i += 1;
            } else if (action.getType() == ActionType.Reduce) {

                // find the relevant production (rule) that we're using to reduce
                List<Symbol> prod = productions.get(action.getIndex());
                int len = prod.size() - 1;
                ParseTree combineTree = new ParseTree(prod.get(0));

                // pop the relevant number of states (corresponding to symbols) off the stack
                for (int x = 0; x < len; x += 1) {
                    stack.pop();
                    combineTree.addChild(parseTree.pop());
                }

                // replace the popped values with a 'parent' value
                parseTree.push(combineTree);
                Set<List<Integer>> nextState = parsingTable.goTo(canonCollection.get(stack.peek()), prod.get(0));
                Integer intNextState = canonCollection.indexOf(nextState);
                stack.push(intNextState);
            } else if (action.getType() == ActionType.Error) {
                throw new Exception("Syntax Error");
            } else {
                // Accept, but only if we're at the end of the input stream
                if (v.type == Symbol.end) {
                    return parseTree;
                } else {
                    i+=1;
                }

            }
        }
    }
}
