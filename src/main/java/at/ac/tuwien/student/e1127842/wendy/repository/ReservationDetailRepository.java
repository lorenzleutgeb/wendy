package at.ac.tuwien.student.e1127842.wendy.repository;

import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, UUID> {
}
