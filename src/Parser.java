import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Parser {

    // integers on the stack refer to the states/sets of items in the canonical collection by index
    private Stack<Integer> stack = new Stack<>();

    private ParsingTable parsingTable = new ParsingTable();

    private List<List<Symbol>> productions = new Production().getProductions();

    private List<Set<List<Integer>>> canonCollection = parsingTable.getCanonCollection();


    // maybe implement inputStream using a queue, then we can easily take 1 symbol at a time???
    // or leave as list and just increment???
    // or use a stream, but figuring that out sounds like a faff
    // BufferedInputStream??

    public boolean parse(List<Token> inputStream) {
        this.stack.push(0);
        boolean accepted = false;
        int i = 0;
        Token v = inputStream.get(0);
        while(true) {
            // need to continue past the end of the input stream cause we need to reduce

            if (i < inputStream.size()) {
                v = inputStream.get(i);
            } else if (i == inputStream.size()) {
                v = new Token(Symbol.end, i, 1, "$");
            } else {
                accepted = false;
                System.out.println("Syntax Error");
                break;
            }

            System.out.println("Token: "+v.value);

            Action action = parsingTable.action(stack.peek(), v.type);
            if (action.getType() == ActionType.Shift) {
                stack.push(action.getIndex()); // TODO - adding -1 here???
                i += 1;
            } else if (action.getType() == ActionType.Reduce) {

                List<Symbol> prod = productions.get(action.getIndex());
                int len = prod.size() - 1;
                for (int x = 0; x < len; x += 1) {
                    stack.pop();
                }
                Set<List<Integer>> nextState = parsingTable.goTo(canonCollection.get(stack.peek()), prod.get(0));
                Integer intNextState = canonCollection.indexOf(nextState);
                stack.push(intNextState); // TODO - adding -1 here???
            } else if (action.getType() == ActionType.Error) {
                accepted = false;
                System.out.println("Syntax Error");
                break;
            } else {
                // Accept, but only if we're at the end of the input stream
                if (v.type == Symbol.end) {
                    accepted = true;
                    System.out.println("accepted");
                    break;
                } else {
                    i+=1;
                }

            }
        }
        return accepted;
    }
}
