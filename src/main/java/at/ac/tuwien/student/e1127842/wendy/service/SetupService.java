package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.domain.Customer;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import at.ac.tuwien.student.e1127842.wendy.repository.BoxRepository;
import at.ac.tuwien.student.e1127842.wendy.repository.CustomerRepository;
import at.ac.tuwien.student.e1127842.wendy.repository.ReservationDetailRepository;
import at.ac.tuwien.student.e1127842.wendy.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class SetupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SetupService.class);

	private final BoxRepository boxRepository;
	private final CustomerRepository customerRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationDetailRepository reservationDetailRepository;

	@Autowired
	public SetupService(BoxRepository boxRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository, ReservationDetailRepository reservationDetailRepository) {
	this.boxRepository = boxRepository;
		this.customerRepository = customerRepository;
		this.reservationRepository = reservationRepository;
		this.reservationDetailRepository = reservationDetailRepository;
	}

	public void setup() {
		LOGGER.info("Bootstrapping database...");

		Customer sabine = customerRepository.save(new Customer("Sabine", "Reiterer"));
		Customer heinz = customerRepository.save(new Customer("Heinz", "Mustermann"));

		Box[] boxes = new Box[10];
		for (int i = 0; i < 10; i++) {
			boxes[i] = boxRepository.save(new Box("Box " + i, BigDecimal.TEN));
		}

		Reservation osterAusritt = reservationRepository.save(new Reservation(heinz));
		reservationDetailRepository.save(new ReservationDetail(boxes[2], osterAusritt, Instant.parse("2017-04-07T00:00:00.00Z"), Instant.parse("2017-04-24T00:00:00.00Z"), "Wilhelm"));
	}
}
