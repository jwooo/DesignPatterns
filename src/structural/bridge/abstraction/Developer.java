package structural.bridge.abstraction;

import structural.bridge.implementor.Work;

public abstract class Developer {

	protected Work work;

	public Developer(Work work) {
		this.work = work;
	}

	public abstract void startWork();
}
