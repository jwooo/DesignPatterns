package structural.bridge.refinedabstraction;

import structural.bridge.abstraction.Developer;
import structural.bridge.implementor.Work;

public class FrontEndDeveloper extends Developer {
	public FrontEndDeveloper(Work work) {
		super(work);
	}

	@Override
	public void startWork() {
		System.out.println("프론트엔드 개발자가 출근하였습니다.");
		work.executeIDE();
		work.develop();
	}
}
