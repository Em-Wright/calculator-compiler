import java.util.LinkedList;
import java.util.List;

public class ParseTree {
    private LinkedList<ParseTree> children;
    private Symbol root;

    ParseTree(Symbol root) {
        this.root = root;
        this.children = new LinkedList<>();
    }

    public void addChild(ParseTree child) {
        children.addFirst(child);
    }

    public List<ParseTree> getChildren() {
        return this.children;
    }

    public Symbol getRoot() {
        return this.root;
    }

    @Override
    public String toString() {

        if (this.children.size() == 0) {
            return root.toString();
        }

        StringBuilder children = new StringBuilder();
        for (ParseTree child: this.children) {
            children.append(child.toString()).append(",");
        }
        children.deleteCharAt(children.length()-1);
        return root + " [" + children.toString() + "]";
    }
}
