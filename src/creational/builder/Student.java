package creational.builder;

public class Student {
	private int id;
	private String name;
	private String grade;
	private String phoneNumber;

	public Student(int id, String name, String grade, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGrade() {
		return grade;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
