package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
public class Reservation extends IdentifiableEntity {
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false, updatable = false)
	private Customer customer;

	@Column(nullable = false, updatable = false)
	private Instant creation = Instant.now();

	@Column(nullable = false, updatable = false)
	private Instant start;

	@Column(nullable = false, updatable = false)
	private Instant end;

	@OneToMany(mappedBy = "key.reservation", fetch = FetchType.EAGER)
	private List<ReservationDetail> details;

	private boolean deleted;

	public Reservation(Customer customer, Instant start, Instant end) {
		if (start == null || end == null|| start.isAfter(end)) {
			throw new IllegalArgumentException();
		}
		this.customer = customer;
		this.start = start;
		this.end = end;
	}

	private Reservation() {
	}

	public Instant getCreation() {
		return creation;
	}

	private void setCreation(Instant creation) {
		this.creation = creation;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Instant getStart() {
		return start;
	}

	public Instant getEnd() {
		return end;
	}

	public List<ReservationDetail> getDetails() {
		return details;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
