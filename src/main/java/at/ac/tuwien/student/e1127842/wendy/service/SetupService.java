package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.repository.BoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SetupService.class);

	private final BoxRepository boxRepository;

	@Autowired
	public SetupService(BoxRepository boxRepository) {
	this.boxRepository = boxRepository;
	}

	public void setup() {
		LOGGER.info("Bootstrapping database...");

		for (int i = 0; i < 10; i++) {
			Box box = new Box();
			boxRepository.save(box);
		}
	}
}
