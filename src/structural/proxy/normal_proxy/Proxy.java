package structural.proxy.normal_proxy;

public class Proxy implements ISubject {
    RealSubject subject;

    Proxy(RealSubject subject) {
        this.subject = subject;
    }

    @Override
    public void action() {
        subject.action();
        System.out.println("프록시 객체 액션");
    }
}
