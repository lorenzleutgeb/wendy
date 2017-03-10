package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Reservation extends IdentifiableEntity {
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false, updatable = false)
	private Customer customer;

	@Column(nullable = false, updatable = false)
	private Instant creation = Instant.now();

	public Reservation(Customer customer) {
		this.customer = customer;
	}

	private Reservation() {
	}

	public Instant getCreation() {
		return creation;
	}

	private void setCreation(Instant creation) {
		this.creation = creation;
	}
}
