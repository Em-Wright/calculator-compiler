import java.util.Arrays;
import java.util.stream.Stream;

public class Token {

    public String value;
    public Symbol type;

    Token(Symbol type, String value) {
        this.type = type;
        this.value = value;
    }
}