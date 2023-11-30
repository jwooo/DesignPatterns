package structural.proxy.virtual_proxy;

import structural.proxy.normal_proxy.ISubject;

public class Client {
    public static void main(String[] args) {
        ISubject sub = new Proxy();
        sub.action();
    }
}
