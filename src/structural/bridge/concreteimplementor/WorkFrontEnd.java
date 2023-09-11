package structural.bridge.concreteimplementor;

import structural.bridge.implementor.Work;

public class WorkFrontEnd implements Work {

	@Override
	public void develop() {
		System.out.println("JavaScript 언어로 개발을 시작합니다.");
	}

	@Override
	public void executeIDE() {
		System.out.println("Visual Studio Code를 실행 시킵니다.");
	}
}
