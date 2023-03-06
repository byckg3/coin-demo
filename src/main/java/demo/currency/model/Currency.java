package demo.currency.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners( value = AuditingEntityListener.class )
@Table( name = "currencies" )
public class Currency implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;

    @Column( unique = true, nullable = false )
	private String code;

    @Column( unique = true, nullable = false )
	private String name;

    @CreatedDate
	@Column( name = "created_date", nullable = false, updatable = false )
	private Date createdDate;

	@LastModifiedDate
	@Column( name = "last_modified", nullable = false )
	private Date lastModified;

	@Version
	@Column
	private Integer version;
}
