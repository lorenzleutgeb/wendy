package at.ac.tuwien.student.e1127842.wendy.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class IdentifiableEntity implements Identifiable<UUID>, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@NotNull
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "at.ac.tuwien.student.e1127842.wendy.config.UseExistingOrGenerateUuidGenerator")
	@Column(columnDefinition = "BINARY(16)")
	protected UUID id;

	protected IdentifiableEntity(UUID id) {
		this.id = id;
	}

	protected IdentifiableEntity() {
	}

	public UUID getId() {
	return id;
	}

	protected void setId(UUID id) {
		this.id = id;
	}
}