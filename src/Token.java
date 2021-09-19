import java.util.Arrays;
import java.util.stream.Stream;

public class Token {

    public String value;
    public Symbol type;
    public int start;
    public int length;

    Token(Symbol type, int start, int length, String value) {
        this.type = type;
        this.start = start;
        this.length = length;
        this.value = value;
    }
}