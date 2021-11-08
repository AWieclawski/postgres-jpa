package edu.awieclawski.postgresjpa.audits;

/**
 * Abstract class to pass the audit related fields
 * 
 * @author AWieclawski
 *
 * @param <U>
 */
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> extends VersionAudit {

	@Transient
	private final String NOW = "CURRENT_TIMESTAMP";
	@Transient
	private final String TIME_DEFAULT_NOW = "TIMESTAMP DEFAULT " + NOW;
	@Transient
	private final String LONG_DEF_ONE = "BIGINT DEFAULT 1";

	@CreatedBy
	@Column(columnDefinition = LONG_DEF_ONE, updatable = false)
	@DiffIgnore
	protected U createdBy;

	@CreatedDate
	@Column(columnDefinition = TIME_DEFAULT_NOW, updatable = false)
	@DiffIgnore
	protected LocalDateTime createdDate;

	@LastModifiedBy
	@Column(columnDefinition = LONG_DEF_ONE)
	protected U lastModifiedBy;

	@LastModifiedDate
	@Column(columnDefinition = TIME_DEFAULT_NOW)
	protected LocalDateTime lastModifiedDate;
}
