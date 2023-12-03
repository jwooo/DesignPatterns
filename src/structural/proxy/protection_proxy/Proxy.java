package structural.proxy.protection_proxy;

import structural.proxy.normal_proxy.ISubject;
import structural.proxy.normal_proxy.RealSubject;

public class Proxy implements ISubject {
    private RealSubject subject;
    boolean access;

    Proxy(RealSubject subject, boolean access) {
        this.subject = subject;
        this.access = access;
    }

    @Override
    public void action() {
        if (access) {
            subject.action();
            System.out.println("프록시 객체 액션");
        }
    }
}
