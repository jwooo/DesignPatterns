package creational.prototype;

import java.util.Objects;

public class GithubIssue implements Cloneable {
	private int id;
	private String title;

	@Override
	public GithubIssue clone() {
		try {
			GithubIssue clone = (GithubIssue) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GithubIssue that = (GithubIssue)o;
		return getId() == that.getId() && Objects.equals(getTitle(), that.getTitle());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitle());
	}
}
