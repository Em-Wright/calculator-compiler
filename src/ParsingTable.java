
import java.util.*;

public class ParsingTable {

    private List<Set<List<Integer>>> canonCollection;

    ParsingTable() {
        this.canonCollection = this.canonicalCollection();
    }

    public List<Set<List<Integer>>> getCanonCollection() {
        return this.canonCollection;
    }

    private Set<List<Integer>> closure(Set<List<Integer>> items) {
        List<List<Symbol>> productions = new Production().getProductions();

        Set<List<Integer>> collectItems = new HashSet<>(items);

        boolean added = true;
        while (added) {
            List<List<Integer>> addedItems = new ArrayList<>();
            added = false;
            for (List<Integer> item: collectItems) {
                // identify the symbol 'after' the dot

                // need to skip if the dot is at the end of the item - else there'll be an error
                // if you try to get the symbolat too high an index
                if (productions.get(item.get(0)).size() <= item.get(1)) {
                    continue;
                }

                Symbol symbol = productions.get(item.get(0)).get(item.get(1));
                // find productions beginning with symbol
                // add them to the list of items, if not there already
                for (int x = 0; x < productions.size(); x += 1) {

                    List<Symbol> production = productions.get(x);
                    List<Integer> newitem = Arrays.asList(x, 1);
                    if (production.get(0) == symbol && !collectItems.contains(newitem) && !addedItems.contains(newitem)) {

                        addedItems.add(newitem);
                        added = true;
                    }
                }
            }
            collectItems.addAll(addedItems);
        }

        return collectItems;
    }


    public Set<List<Integer>> goTo(Set<List<Integer>> items, Symbol X) {
        // for each item, if the dot isn't at the end, and the symbol after the dot (i.e. at that index)
        // is X, then move the dot along one and add that to the list of items to return
        // then generate the closure of that set

        List<List<Symbol>> productions = new Production().getProductions();

        Set<List<Integer>> newItems = new HashSet<>();
        for (List<Integer> item: items) {

            // skip it if the dot is at the end
            if (productions.get(item.get(0)).size() <= item.get(1)) {
                continue;
            }

            Symbol symbol = productions.get(item.get(0)).get(item.get(1));

            if (symbol == X) {
                newItems.add(Arrays.asList(item.get(0), item.get(1) + 1));
            }

        }

        return this.closure(newItems);
    }

    private List<Set<List<Integer>>> canonicalCollection() {
        List<Set<List<Integer>>> canonCollection = new ArrayList<>();
        Set<List<Integer>> tmp = new HashSet<>();
        tmp.add(Arrays.asList(0,1));
        canonCollection.add(this.closure(tmp));

        List<Symbol> symbols = Arrays.asList(Symbol.A, Symbol.S, Symbol.M, Symbol.C, Symbol.F, Symbol.num, Symbol.add, Symbol.subtract, Symbol.mult, Symbol.fact, Symbol.cos, Symbol.end);

        boolean added = true;

        while(added) {
            List<Set<List<Integer>>> toAdd = new ArrayList<>();
            added = false;
            for (Set<List<Integer>> items: canonCollection) {
                for (Symbol symbol: symbols) {
                    Set<List<Integer>> goTo = this.goTo(items, symbol);
                    if (goTo.size() != 0 && !canonCollection.contains(goTo)) {
                        added = true;
                        toAdd.add(goTo);
                    }
                }

            }
            canonCollection.addAll(toAdd);
        }

        return canonCollection;

    }

    // need functions action and goto
    // which use the grammar

    public Action action(Integer state, Symbol a) {
        Set<List<Integer>> stateSet = this.canonCollection.get(state);
        List<List<Symbol>> productions = new Production().getProductions();


        // if there's a transition out using the symbol, do a shift to that state
        // if not, see if there's an item which ends in a dot, and reduce using that
        // if not, error
        // unless it can't reduce cause it's accepting - need to differentiate that somehow

        if (a == Symbol.end && state == 0) {
            System.out.println("Accepting cause at end");
            return new Action(ActionType.Accept, 0);
        }


        // TODO: issue is that we're accepting for no reason -
        //  should reject if there are still tokens and the next state doesn't exist
        Set<List<Integer>> nextState = this.goTo(stateSet, a);
        if (nextState.size() != 0 ) {
            System.out.println("Shift");
            System.out.println(nextState);
            return new Action(ActionType.Shift, this.canonCollection.indexOf(nextState));
        } else {
            List<List<Integer>> stateList = new ArrayList<>(stateSet);
            for (int x = 0; x < stateList.size(); x += 1) {
                Integer prodLen = productions.get(stateList.get(x).get(0)).size(); // length of production
                if (prodLen == stateList.get(x).get(1)) { // check if dot at end
                    if (stateList.get(x).get(0) == 0) { // if we're at the accepting state
                        if (a == Symbol.end) {
                            System.out.println("Accept");
                            System.out.println(productions.get(stateList.get(x).get(0)));
                            return new Action(ActionType.Accept, 0);
                        } else {
                            return new Action(ActionType.Error, -1);
                        }

                    }
                    System.out.println("Reduce");
                    System.out.println(productions.get(stateList.get(x).get(0)));
                    return new Action(ActionType.Reduce, stateList.get(x).get(0));
                }
            }
        }


        return new Action(ActionType.Error, -1);
    }

}

