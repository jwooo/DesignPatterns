package creational.prototype;

public class App {
	public static void main(String[] args) {
		GithubIssue githubIssue = new GithubIssue();
		githubIssue.setId(1);
		githubIssue.setTitle("title");

		GithubIssue clone = githubIssue.clone();

		System.out.println("clone != githubIssue = " + (clone != githubIssue));
		System.out.println("clone.equals(githubIssue) = " + clone.equals(githubIssue));
		System.out.println("clone.getClass() == githubIssue.getClass() = " + (clone.getClass() == githubIssue.getClass()));
	}
}
