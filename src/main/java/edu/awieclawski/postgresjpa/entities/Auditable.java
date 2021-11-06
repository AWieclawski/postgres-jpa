package edu.awieclawski.postgresjpa.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

/**
 * Abstract class to pass the audit related fields
 * 
 * @author AWieclawski
 *
 * @param <U>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	private final String NOW = "CURRENT_TIMESTAMP";
	private final String TIME_DEFAULT_NOW = "TIMESTAMP DEFAULT " + NOW;

	@CreatedBy
	@Column(columnDefinition = "bigint default 1", updatable = false)
	protected U createdBy;

	@CreatedDate
	@Column(columnDefinition = NOW, updatable = false)
	protected LocalDateTime createdDate;

	@LastModifiedBy
	@Column(columnDefinition = "bigint default 1")
	protected U lastModifiedBy;

	@LastModifiedDate
	@Column(columnDefinition = TIME_DEFAULT_NOW)
	protected LocalDateTime lastModifiedDate;

}
