package behavior.chain_of_responsibility.example;

public class PortHandler extends Handler {
    @Override
    protected void process(String url) {
        int index = url.lastIndexOf(":");
        if (index != -1) {
            String startPort = url.substring(index + 1);
            try {
                int port = Integer.parseInt(startPort);
                System.out.println("Port: " + port);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
