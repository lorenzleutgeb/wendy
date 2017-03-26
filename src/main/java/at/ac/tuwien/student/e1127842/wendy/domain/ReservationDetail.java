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
	private String horseName;

	public ReservationDetail(Box box, Reservation reservation, String horseName) {
		if (box == null || horseName == null || horseName.isEmpty()) {
			throw new IllegalArgumentException("empty parameter");
		}

		if (box.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("price must be greater than zero");
		}

		this.key = new ReservationDetailKey(box, reservation);
		this.price = box.getPrice();
		this.horseName = horseName;
	}

	// TODO: Make private
	public ReservationDetail() {
	}

	public ReservationDetailKey getKey() {
		return key;
	}

	private void setKey(ReservationDetailKey key) {
		this.key = key;
	}

	public String getHorseName() {
		return horseName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Transient
	public Box getBox() {
		return key.getBox();
	}

	@Transient
	public Reservation getReservation() {
		return key.getReservation();
	}

	@Override
	public String toString() {
		return getHorseName() + " in " + getBox().getName();
	}
}
