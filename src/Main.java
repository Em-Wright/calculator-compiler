import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {


        Parser parser = new Parser();

        /*
        ParsingTable parsingTable = new ParsingTable();
        List<Set<List<Integer>>> canonCollection = parsingTable.getCanonCollection();


        for (Set<List<Integer>> set: canonCollection){
            System.out.println(set);
            System.out.println();
        }*/

        List<Token> lexed = Lexer.lex(new String[]{"cos 1235.673278! + 3 * 2 + 1.2"});

        System.out.println();
        System.out.println();

        boolean parsed = parser.parse(lexed);
        System.out.println(parsed);

    }

}
