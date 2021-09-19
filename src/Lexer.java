import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    // need the lexer to be able to recognise signed floating point numbers
    // and then the parser can just treat numbers as one blob, rather than dealing with each separate character

    public static List<Token> lex(String[] input) {
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
                    result.add(new Token(Symbol.cos, x, 3, "cos"));
                    System.out.println("cos");
                    x +=2;
                } else {
                    // TODO: return an error message
                    System.out.println("Unknown symbol 1");
                    break;
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
                System.out.println("num");
                result.add(new Token(Symbol.num, x, y-x, String.valueOf(Arrays.copyOfRange(merged, x, y))));
                x = y-1;
            } else {
                switch (ch) {
                    case '+':
                        System.out.println("add");
                        result.add(new Token(Symbol.add, x, 1, "+"));
                        break;
                    case '-':
                        System.out.println("subtract");
                        result.add(new Token(Symbol.subtract, x, 1, "-"));
                        break;
                    case '!':
                        System.out.println("factorial");
                        result.add(new Token(Symbol.fact, x, 1, "!"));
                        break;
                    case '*':
                        System.out.println("multiply");
                        result.add(new Token(Symbol.mult, x, 1, "*"));
                        break;
                    default:
                        // TODO: return an error
                        System.out.println("Unknown symbol 2");
                        break;
                }
            }
            x +=1;
        }

        return result;
    }
}

