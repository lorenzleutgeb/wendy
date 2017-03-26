package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.domain.Customer;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import at.ac.tuwien.student.e1127842.wendy.repository.ReservationDetailRepository;
import at.ac.tuwien.student.e1127842.wendy.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {
	private static Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

	private final CustomerService customerService;
	private final BoxService boxService;

	private final ReservationDetailRepository reservationDetailRepository;
	private final ReservationRepository repo;

	@Autowired
	public ReservationService(CustomerService customerService, BoxService boxService, ReservationDetailRepository reservationDetailRepository, ReservationRepository reservationRepository) {
		this.customerService = customerService;
		this.boxService = boxService;
		this.reservationDetailRepository = reservationDetailRepository;
		this.repo = reservationRepository;
	}

	public List<Box> getFreeBoxes(Instant start, Instant end) {
		List<Box> boxes = boxService.findAll();
		List<Reservation> reservations = repo.findByStartAfterAndEndBeforeAndDeleted(start, end, false);

		return boxes.stream().filter(b -> {
			return reservations.stream().noneMatch(r -> {
				return r.getDetails().stream().anyMatch(d -> {
					return d.getBox().equals(b);
				});
			});
		}).collect(Collectors.toList());
	}

	public Reservation create(Customer customer, Instant start, Instant end, ReservationDetail... details) {
		return create(customer, start, end, Arrays.asList(details));
	}

	public Reservation create(Customer customer, Instant start, Instant end, List<ReservationDetail> details) {
		customerService.save(customer);

		Reservation reservation = repo.save(new Reservation(customer, start, end));

		details.stream().map(detail -> {
			return new ReservationDetail(detail.getBox(), reservation, detail.getHorseName());
		}).forEach(reservationDetailRepository::save);

		return reservation;
	}

	public List<Reservation> find(String customer, Instant start, Instant end) {
		return repo.findByCustomerNameLikeAndStartAfterAndEndBefore(customer, start, end);
	}

	public Reservation getReservation(UUID id) throws IllegalArgumentException {
		LOGGER.info("service: getResbyid");
		LOGGER.debug("service: getReservation by id");
		return repo.findOne(id);
	}

	public List<Reservation> findAll() {
		LOGGER.info("service: get all reservierugen");
		LOGGER.debug("service: get all Reservationen");
		return repo.findAll();
	}

	public List<Reservation> getAllResByDate()
		throws IllegalArgumentException {
		LOGGER.info("service: get reservierugen by findAll");
		LOGGER.debug("service: get Reservationen by findAll");

		// TODO: Actually findAll.
		return repo.findAll();
	}

	public List<Box> getAllFreeBoxes(String von, String bis) {
		LOGGER.info("service: get all free boxes");
		LOGGER.debug("service: get all free boxes");

		// TODO: Actually findAll.
		return boxService.findAll();
	}

	public void delete(Reservation item) {
		item.setDeleted(true);
		repo.save(item);
	}
}
