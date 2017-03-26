package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "box")
public class Box extends IdentifiableEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;

	private BigDecimal price;

	@Lob
	private byte[] photo;

	@Min(0)
	private double size;

	private boolean window;
	private boolean deleted;

	public Box(UUID id) {
		super(id);
	}

	public Box() {
	}

	public Box(String name, BigDecimal price, byte[] photo, double size, boolean window) {
		this.name = name;
		this.price = price;
		this.photo = photo;
		this.size = size;
		this.window = window;
	}

	public Box(String name, BigDecimal price, boolean window) {
		this.name = name;
		this.price = price;
		this.window = window;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public boolean isWindow() {
		return window;
	}

	public void setWindow(boolean window) {
		this.window = window;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}