package structural.proxy.protection_proxy;

import structural.proxy.normal_proxy.ISubject;
import structural.proxy.normal_proxy.RealSubject;

public class Client {
    public static void main(String[] args) {
        ISubject sub = new Proxy(new RealSubject(), false);
        sub.action();
    }
}
