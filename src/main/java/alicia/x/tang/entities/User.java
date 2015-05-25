package alicia.x.tang.entities;

import java.io.Serializable;

import com.google.api.client.auth.oauth2.Credential;

/**
 * User object to be stored in session for current user.
 */
public class User implements Serializable { 

	private static final long serialVersionUID = 1L;
	private long id;
	private Credential creds;

	public User(long id) {
		this.id = id;
	}

	// Need to add getters for fields that need to be displayed.
	// Jackson only convert filed with getters to json.
	public long getId() {
		return id;
	}
	public String getIdString() {
		return String.valueOf(id);
	}
	public void setCredential(Credential creds) {
		this.creds = creds;
	}
	public Credential getCredential() {
		return creds;
	}
}
