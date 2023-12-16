package behavior.command;

import behavior.command.command.OpenTextFileOperation;
import behavior.command.command.SaveTextFileOperation;
import behavior.command.invoker.TextFileOperationExecutor;
import behavior.command.receiver.TextFile;

public class Client {
    public static void main(String[] args) {
        TextFileOperationExecutor textFileOperationExecutor = new TextFileOperationExecutor();
        String openFile = textFileOperationExecutor.executeOperation(new OpenTextFileOperation(new TextFile("file1.txt")));
        String saveFile = textFileOperationExecutor.executeOperation(new SaveTextFileOperation(new TextFile("file2.txt")));

        System.out.println(openFile);
        System.out.println(saveFile);

        TextFile textFile = new TextFile("file3.txt");
        String openFileMethod = textFileOperationExecutor.executeOperation(textFile::open);
        String saveFileMethod = textFileOperationExecutor.executeOperation(textFile::save);

        System.out.println(openFileMethod);
        System.out.println(saveFileMethod);
    }
}
