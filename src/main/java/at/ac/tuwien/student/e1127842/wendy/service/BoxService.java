package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.repository.BoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BoxService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxService.class);

	private final BoxRepository repo;

	@Autowired
	public BoxService(BoxRepository repo) {
		this.repo = repo;
	}

	public List<Box> findAll() {
		return repo.findAll();
	}

	public Box createBox(Box box) {
		LOGGER.info("service: creating box");
		validate(box);
		return repo.save(box);
	}

	public List<Box> findAll(BigDecimal minPrice, BigDecimal maxPrice, String name, boolean window) {
		//return repo.findAll(BoxSpecifications.priceInRange(minPrice, maxPrice));
		return repo.findAllByPriceAndWindowAndName(minPrice, maxPrice, window, name);
	}

	public List<Box> findAll(BigDecimal minPrice, BigDecimal maxPrice, String name) {
		//return repo.findAll(BoxSpecifications.priceInRange(minPrice, maxPrice));
		return repo.findAllByPriceAndName(minPrice, maxPrice, name);
	}

	public void delete(Box box) {
		box.setDeleted(true);
		repo.save(box);
	}

	public Box updateBox(Box box) {
		LOGGER.info("service: updating box");
		validate(box);
		return repo.save(box);
	}

	public List<Box> getBoxen() {
		LOGGER.info("service: get all boxes");
		LOGGER.debug("service: get all boxen");
		return repo.findAll();
	}

	public Box findOne(UUID id) throws IllegalArgumentException{
		LOGGER.debug("Getting Box by id " + id);
		return repo.findOne(id);
	}

	private void validate(Box box) {
		/* nicht sicher ob notwendig
		 * if(box == null){
			throw new IllegalArgumentException("Bitte uebergeben sie gueltige Eintraege");
		}*/
		if (box.getName().isEmpty() || box.getName() == null) {
			throw new IllegalArgumentException("Name darf nicht frei bleiben.");
		}
		if (box.getPrice().compareTo(BigDecimal.ZERO) < 0 || box.getPrice() == null) {
			throw new IllegalArgumentException("Tagespreis darf nicht frei bleiben.");
		}
		if (box.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Preis darf nicht 0 oder negativ sein.");
		}
		if (box.getSize() <= 0) {
			throw new IllegalArgumentException("Groesse darf nicht 0 oder negativ sein.");
		}
		if(box.getPhoto() == null || box.getPhoto().length == 0){
			throw new IllegalArgumentException("Pfad des Fotos muss angegeben werden.");
		}
	}
}
