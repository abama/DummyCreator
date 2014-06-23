package de.abama.dummycreator.articles;

public class NavisionDB {
	private String name;
	private String password;
	private String serverURI;
	private String user;
	
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getServerURI() {
		return serverURI;
	}
	public String getUser() {
		return user;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setServerURI(String serverURI) {
		this.serverURI = serverURI;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
