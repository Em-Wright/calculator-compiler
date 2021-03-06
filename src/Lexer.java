import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    // need the lexer to be able to recognise signed floating point numbers
    // and then the parser can just treat numbers as one blob, rather than dealing with each separate character

    public static List<Token> lex(String input) throws Exception {
        List<Character> digits = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        char[] merged = String.join("", input).toCharArray();


        List<Token> result = new ArrayList<>();

        int x = 0;
        while (x < merged.length) {
            char ch = merged[x];
            if (ch == ' ') {
                //do nothing
            } else if (ch == 'c') {
                if (merged[x+1] == 'o' && merged[x+2] == 's') {
                    result.add(new Token(Symbol.cos, "cos"));
                    x +=2;
                } else {
                    throw new Exception("Unknown Symbol: "+ ch);
                }
            } else if (digits.contains(ch)) {
                // carry on til you get to a ., then carry on til you get to the end of the numbers
                // TODO: need to change to deal with signed floating point nums later, but this should work for now

                int y= x;
                while (y < merged.length && digits.contains(merged[y])) {
                    y +=1;
                }

                if (y < merged.length && merged[y] == '.') {
                    y += 1;
                    while (y < merged.length && digits.contains(merged[y])) {
                        y += 1;
                    }
                }
                result.add(new Token(Symbol.num, String.valueOf(Arrays.copyOfRange(merged, x, y))));
                x = y-1;
            } else {

                switch (ch) {
                    case '+':
                        result.add(new Token(Symbol.add, "+"));
                        break;
                    case '-' :
                        result.add(new Token(Symbol.subtract, "-"));
                        break;
                    case '!' :
                        result.add(new Token(Symbol.fact, "!"));
                        break;
                    case '*' :
                        result.add(new Token(Symbol.mult, "*"));
                        break;
                    default:
                        throw new Exception("Unknown Symbol: "+ ch);
                }
            }
            x +=1;
        }

        return result;
    }
}

