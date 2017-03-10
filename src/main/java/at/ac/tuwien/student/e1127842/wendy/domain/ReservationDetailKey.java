package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public final class ReservationDetailKey implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private Box box;

	@ManyToOne
	@NotNull
	private Reservation reservation;

	public ReservationDetailKey(Box box, Reservation reservation) {
		this.box = box;
		this.reservation = reservation;
	}

	private ReservationDetailKey() {
	}

	public Box getBox() {
		return box;
	}

	public Reservation getReservation() {
		return reservation;
	}

	private void setBox(Box box) {
		this.box = box;
	}

	private void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ReservationDetailKey)) return false;

		ReservationDetailKey that = (ReservationDetailKey) o;

		if (box != null ? !box.equals(that.box) : that.box != null) return false;
		return reservation != null ? reservation.equals(that.reservation) : that.reservation == null;
	}

	@Override
	public int hashCode() {
		int result = box != null ? box.hashCode() : 0;
		result = 31 * result + (reservation != null ? reservation.hashCode() : 0);
		return result;
	}
}
