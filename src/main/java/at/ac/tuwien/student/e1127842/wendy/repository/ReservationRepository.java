package at.ac.tuwien.student.e1127842.wendy.repository;

import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
	List<Reservation> findByStartAfterAndEndBeforeAndDeleted(Instant start, Instant end, boolean deleted);

	@Query("SELECT DISTINCT reservation from Reservation reservation JOIN FETCH reservation.customer customer " +
		"WHERE (customer.firstName like %:name% OR customer.lastName like %:name%) " +
		"AND reservation.start >= :start " +
		"AND reservation.end <= :end " +
		"AND reservation.deleted = false")
	List<Reservation> findByCustomerNameLikeAndStartAfterAndEndBefore(@Param("name") String name,
									  @Param("start") Instant start,
									  @Param("end") Instant end);
}
