package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "customer")
public final class Customer extends IdentifiableEntity {
	private String firstName;

	private String lastName;

	private Instant since;

	@OneToMany(mappedBy = "customer")
	Set<Reservation> reservations;

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.since = Instant.now();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private Instant getSince() {
		return since;
	}

	public void setSince(Instant since) {
		this.since = since;
	}
}
