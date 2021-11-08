package edu.awieclawski.postgresjpa.audits;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

/**
 * 
 * @author AWieclawski
 *
 */
@MappedSuperclass
public abstract class VersionAudit {

	@Transient
	private final String LONG_DEF_ZERO = "BIGINT DEFAULT 0";

	@Version
	@Column(name = "version", columnDefinition = LONG_DEF_ZERO, nullable = false)
	private long version;
}
