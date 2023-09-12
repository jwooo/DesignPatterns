package structural.bridge;

import structural.bridge.abstraction.Developer;
import structural.bridge.concreteimplementor.WorkBackEnd;
import structural.bridge.concreteimplementor.WorkFrontEnd;
import structural.bridge.refinedabstraction.BackEndDeveloper;
import structural.bridge.refinedabstraction.FrontEndDeveloper;

public class Client {
	public static void main(String[] args) {
		Developer javaDeveloper = new BackEndDeveloper(new WorkBackEnd());
		javaDeveloper.startWork();

		Developer javaScriptDeveloper = new FrontEndDeveloper(new WorkFrontEnd());
		javaScriptDeveloper.startWork();
	}
}
