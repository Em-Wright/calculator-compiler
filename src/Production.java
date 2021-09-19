import java.util.Arrays;
import java.util.List;


public class Production {
    List<Symbol> production0 = Arrays.asList(Symbol.Start, Symbol.A);
    List<Symbol> production1 = Arrays.asList(Symbol.A, Symbol.S);
    List<Symbol> production2 = Arrays.asList(Symbol.A, Symbol.A, Symbol.add, Symbol.S);
    List<Symbol> production3 = Arrays.asList(Symbol.S, Symbol.M);
    List<Symbol> production4 = Arrays.asList(Symbol.S, Symbol.S, Symbol.subtract, Symbol.M);
    List<Symbol> production5 = Arrays.asList(Symbol.M, Symbol.C);
    List<Symbol> production6 = Arrays.asList(Symbol.M, Symbol.M, Symbol.mult, Symbol.C);
    List<Symbol> production7 = Arrays.asList(Symbol.C, Symbol.F);
    List<Symbol> production8 = Arrays.asList(Symbol.C, Symbol.cos, Symbol.F);
    List<Symbol> production9 = Arrays.asList(Symbol.F, Symbol.num);
    List<Symbol> production10 = Arrays.asList(Symbol.F, Symbol.num, Symbol.fact);

    List<List<Symbol>> productions = Arrays.asList(production0, production1, production2, production3,
            production4, production5, production6, production7, production8, production9, production10);

    List<List<Symbol>> getProductions() {
        return productions;
    }

}
