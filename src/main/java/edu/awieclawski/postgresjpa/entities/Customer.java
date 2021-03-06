package edu.awieclawski.postgresjpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import edu.awieclawski.postgresjpa.audits.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
/**
 * 
 * @author AWieclawski
 *
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends Auditable<String> {

	public static final String TABLE_NAME = "customers";
	public static final String SEQ_NAME = "customers_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
	private Long id;

	@Column(updatable = true, name = "order_id", nullable = false)
	private Long orderId;

	@Column(updatable = true, name = "surname", nullable = false, length = 50)
	private String firstName;

	@Column(updatable = true, name = "name", nullable = false, length = 50)
	private String lastName;

	@Column(updatable = true, name = "email", nullable = true, length = 50)
	private String email;

	@Column(updatable = true, name = "is_deleted", columnDefinition = "boolean NOT NULL DEFAULT false")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "customer")
	private Set<SubCustomer> subCustomersSet;

	@PrePersist
	@PreUpdate
	public void initFieldsIfNull() {
		if (this.getIsDeleted() == null)
			this.setIsDeleted(Boolean.valueOf(false));
		if (this.getSubCustomersSet() == null)
			this.setSubCustomersSet(new HashSet<>());
		if (this.getOrderId() == null)
			this.setOrderId(-1L);
	}

	@PostPersist
	@PostUpdate
	public void initAfterPersist() {
		if (this.getOrderId() < 0)
			this.setOrderId(this.getId() * 100);
	}

	public void addSubCustomer(SubCustomer subCustomer) {
		if (subCustomersSet == null)
			subCustomersSet = new HashSet<>();
		this.subCustomersSet.add(subCustomer);
		if (subCustomer != null)
			subCustomer.setCustomer(this);
	}

	public void removeSubCustomer(SubCustomer subCustomer) {
		this.subCustomersSet.remove(subCustomer);
		subCustomer.setCustomer(null);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}
