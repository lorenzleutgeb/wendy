package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.repository.BoxRepository;
import com.sun.scenario.effect.impl.state.BoxRenderState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoxServiceTest {
	private BoxService service;
	private BoxRepository repository;

	@Before
	public void setUp() throws Exception {
		this.repository = mock(BoxRepository.class);
		this.service = new BoxService(repository);
	}

	@Test
	public void findAll() {
		UUID id = UUID.randomUUID();

		final Box box = new Box(id);

		when(repository.findAll()).thenReturn(Collections.singletonList(box));

		List<Box> actual = service.findAll();

		assertEquals("There should be exactly one box.", 1, actual.size());
		assertEquals(actual.get(0), box);
	}
}