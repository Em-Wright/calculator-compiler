public class Action {

    private final ActionType type;
    private final Integer index;

    public ActionType getType() {
        return this.type;
    }
    public Integer getIndex() {
        return this.index;
    }

    Action(ActionType type, Integer index) {
        this.type = type;
        this.index = index;
    }

}