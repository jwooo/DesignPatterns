package structural.proxy.virtual_proxy;

import structural.proxy.normal_proxy.ISubject;
import structural.proxy.normal_proxy.RealSubject;

public class Proxy implements ISubject {
    private RealSubject subject;

    Proxy() {}

    @Override
    public void action() {
        if (subject == null) {
            subject = new RealSubject();
        }
        subject.action();
        System.out.println("프록시 객체 생성");
    }
}
