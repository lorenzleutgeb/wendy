package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "organization_membership")
@AssociationOverrides({
	@AssociationOverride(name = "key.box", joinColumns = {@JoinColumn(name = "box_id")}),
	@AssociationOverride(name = "key.reservation", joinColumns = {@JoinColumn(name = "reservation_id")})
})
public final class ReservationDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReservationDetailKey key;

	@Column(nullable = false, updatable = false, precision = 9, scale = 3)
	private BigDecimal price;

	@Column(nullable = false, updatable = false)
	private Instant start;

	@Column(nullable = false, updatable = false)
	private Instant end;

	@Column(nullable = false, updatable = false)
	private String horseName;

	public ReservationDetail(Box box, Reservation reservation, Instant start, Instant end, String horseName) {
		this.key = new ReservationDetailKey(box, reservation);
		this.price = box.getPrice();
		this.start = start;
		this.end = end;
		this.horseName = horseName;
	}

	private ReservationDetail() {
	}

	public ReservationDetailKey getKey() {
		return key;
	}

	private void setKey(ReservationDetailKey key) {
		this.key = key;
	}

	@Transient
	public Box getBox() {
		return key.getBox();
	}

	@Transient
	public Reservation getReservation() {
		return key.getReservation();
	}
}
