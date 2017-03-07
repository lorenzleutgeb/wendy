package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "box")
public class Box extends IdentifiableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private byte[] photo;
	private double size;
	boolean hasWindow;
	boolean isOutside;

	public Box(UUID id) {
		super(id);
	}

	public Box() {
	}
}