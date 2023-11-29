package structural.proxy.normal_proxy;

public class RealSubject implements ISubject {
    @Override
    public void action() {
        System.out.println("원본 객체 생성");
    }
}
