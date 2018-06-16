/**
 * Created by evkingen on 11.06.2018.
 */
public class CommandMessage extends AbstractMessage{
    private String expression;
    private String command;
    private String[] arguments;

    public CommandMessage(String expression) {
        this.expression = expression;
        String[] partExpression = expression.split(" ",2);
        this.command = partExpression[0].equals("") ? expression : partExpression[0];
        this.arguments = partExpression[1].split(" ");
    }

    public String getExpression() {
        return expression;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }

}
