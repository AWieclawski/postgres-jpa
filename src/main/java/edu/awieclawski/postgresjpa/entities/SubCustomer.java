package edu.awieclawski.postgresjpa.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = SubCustomer.TABLE_NAME)
public class SubCustomer extends Auditable<String> {

	public static final String TABLE_NAME = "sub_customers";
	public static final String SEQ_NAME = "sub_customers_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

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

	@PrePersist
	@PreUpdate
	public void initFieldsIfNull() {
		if (this.getIsDeleted() == null)
			this.setIsDeleted(Boolean.valueOf(false));
		if (this.getOrderId() == null)
			this.setOrderId(-1L);
	}

	@PostPersist
	@PostUpdate
	public void initAfterPersist() {
		if (this.getOrderId() < 0) {
			long parentOrderId = 0L;
			final long STEP = 1L;
			if (this.getCustomer() != null && this.getCustomer().getOrderId() != null)
				parentOrderId = this.getCustomer().getOrderId();
			long parentSubCustomersSetSize = this.getCustomer().getSubCustomersSet().size();
			parentSubCustomersSetSize = Long.sum(parentSubCustomersSetSize, STEP);
			this.setOrderId(Long.sum(parentOrderId, (parentSubCustomersSetSize)));
		}
	}

	@Override
	public String toString() {
		return "SubCustomer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}

}
