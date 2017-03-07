package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxService {
	private final BoxRepository boxRepository;

	@Autowired
	public BoxService(BoxRepository boxRepository) {
		this.boxRepository = boxRepository;
	}

	public List<Box> list() {
		return boxRepository.findAll();
	}

	public Box create() {
		return null;
	}
}
