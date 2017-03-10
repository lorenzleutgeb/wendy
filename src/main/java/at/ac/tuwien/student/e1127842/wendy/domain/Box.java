package at.ac.tuwien.student.e1127842.wendy.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "box")
public class Box extends IdentifiableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal price;

	private String name;
	private byte[] photo;
	private double size;
	private boolean window;
	private boolean outside;
	private boolean deleted;

	public Box(UUID id) {
		super(id);
	}

	private Box() {
	}

	public Box(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
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

	public boolean isOutside() {
		return outside;
	}

	public void setOutside(boolean outside) {
		this.outside = outside;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}