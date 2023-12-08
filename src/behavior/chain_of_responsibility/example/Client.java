package behavior.chain_of_responsibility.example;

public class Client {
    public static void main(String[] args) {
        Handler handler1 = new ProtocolHandler();
        Handler handler2 = new DomainHandler();
        Handler handler3 = new PortHandler();

        handler1.setNext(handler2).setNext(handler3);

        String url1 = "http://www.youtube.com:80";
        System.out.println("Input: " + url1);
        handler1.run(url1);

        String url2 = "https://www.jwooo.tistory.com:443";
        System.out.println("Input: " + url2);
        handler1.run(url2);

        String url3 = "http://localhost:8080";
        System.out.println("Input: " + url3);
        handler1.run(url3);
    }
}
