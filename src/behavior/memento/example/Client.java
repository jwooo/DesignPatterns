package behavior.memento.example;

public class Client {

    public static void main(String[] args) {
        Document document = new Document("글을 작성 합니다.");
        History history = new History();

        document.write("\n글을 1번째로 추가 작성 합니다.");
        history.add(document.createMemento());

        document.write("\n글을 2번째로 추가 작성 합니다.");
        history.add(document.createMemento());

        document.restoreFromMemento(history.getMemento(0));

        System.out.println(document.getContent());
    }

}
