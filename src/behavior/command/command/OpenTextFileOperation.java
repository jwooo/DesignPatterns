package behavior.command.command;

import behavior.command.receiver.TextFile;

public class OpenTextFileOperation implements TextFileOperation {
    private TextFile textFile;

    public OpenTextFileOperation(TextFile textfile) {
        this.textFile = textfile;
    }

    @Override
    public String execute() {
        return textFile.open();
    }
}
