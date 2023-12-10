package behavior.chain_of_responsibility.example;

public class ProtocolHandler extends Handler {

    @Override
    protected void process(String url) {
        int index = url.indexOf("://");
        if (index != -1) {
            System.out.println("PROTOCOL : " + url.substring(0, index));
        } else {
            System.out.println("NO PROTOCOL");
        }
    }
}
