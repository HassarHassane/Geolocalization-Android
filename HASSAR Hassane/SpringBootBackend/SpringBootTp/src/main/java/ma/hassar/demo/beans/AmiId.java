package ma.hassar.demo.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AmiId implements Serializable {
 
    @Column(name = "user1_id")
    private int user1Id;
 
    @Column(name = "user2_id")
    private int user2Id;

	public AmiId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(int user1Id) {
		this.user1Id = user1Id;
	}

	public int getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(int user2Id) {
		this.user2Id = user2Id;
	}
    
    
}
