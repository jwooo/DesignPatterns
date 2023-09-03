package creational.builder;

public class BuilderTest {
	public static void main(String[] args) {
		Student student1 = new StudentBuilder()
			.id(1)
			.name("user")
			.grade("Senior")
			.phoneNumber("000-0000-0000")
			.build();

		System.out.println(student1.getClass());
		System.out.println("student1.getId() = " + student1.getId());
		System.out.println("student1.getName() = " + student1.getName());
		System.out.println("student1.getGrade() = " + student1.getGrade());
		System.out.println("student1.getPhoneNumber() = " + student1.getPhoneNumber());
	}
}
