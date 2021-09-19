import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {


        Parser parser = new Parser();

        try {
            System.out.println("Tokenised input: ");
            List<Token> lexed = Lexer.lex("cos 1235.673278!");
            for (Token v: lexed) {
                System.out.println(v.type.toString());
            }

            Stack<ParseTree> parsed = parser.parse(lexed);
            ParseTree root = parsed.pop();

            System.out.println("\nParse tree: ");

            System.out.println(root.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

}
