package command;

import java.util.Stack;

public class K2600128_Invoker {

    private Stack<K2600128_Command> history = new Stack<>();

    public void execute(K2600128_Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            K2600128_Command last = history.pop();
            last.undo();
            System.out.println("Last action undone.");
        } else {
            System.out.println("No actions to undo.");
        }
    }
}
