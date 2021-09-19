public class Action {

    private ActionType type;

    private Integer index;

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