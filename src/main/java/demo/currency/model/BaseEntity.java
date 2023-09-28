package demo.currency.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@EntityListeners( value = AuditingEntityListener.class )
@MappedSuperclass  // A mapped superclass has no separate table defined for it.
public abstract class BaseEntity  // base class for entity with common properties
{
	@JsonIgnore
    @CreatedDate
	@Column( name = "created_date", nullable = false, updatable = false )
	private OffsetDateTime createdDate;

	@JsonIgnore
	@LastModifiedDate
	@Column( name = "last_modified", nullable = false )
	private OffsetDateTime lastModified;

	@JsonIgnore
	@Version
	@Column
	private Integer version;
}
