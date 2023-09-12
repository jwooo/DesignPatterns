package structural.bridge.concreteimplementor;

import structural.bridge.implementor.Work;

public class WorkBackEnd implements Work {

	@Override
	public void develop() {
		System.out.println("Java 언어로 개발을 시작합니다.");
	}

	@Override
	public void executeIDE() {
		System.out.println("IntelliJ를 실행 시킵니다.");
	}
}
