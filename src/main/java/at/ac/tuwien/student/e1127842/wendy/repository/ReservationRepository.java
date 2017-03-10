package at.ac.tuwien.student.e1127842.wendy.repository;

import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
