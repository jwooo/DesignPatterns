package behavior.chain_of_responsibility.example;

public class DomainHandler extends Handler {
    @Override
    protected void process(String url) {
        int startIndex = url.indexOf("://");
        int lastIndex = url.lastIndexOf(":");

        System.out.print("Domain: ");
        if (startIndex == -1) {
            if (lastIndex == -1) {
                System.out.println(url);
            } else {
                System.out.println(url.substring(0, lastIndex));
            }
        } else if (startIndex != lastIndex) {
            System.out.println(url.substring(startIndex + 3, lastIndex));
        } else {
            System.out.println(url.substring(startIndex + 3));
        }
    }
}
