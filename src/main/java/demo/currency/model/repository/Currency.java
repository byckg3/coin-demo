package demo.currency.model.repository;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EntityListeners( value = AuditingEntityListener.class )
@Table( name = "currencies" )
public class Currency implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@JsonIgnore
	private Long id;

    @Column( unique = true, nullable = false )
	private String code;

    @Column( nullable = false )
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

	public Currency( String code, String name ) {
		this.code = code;
		this.name = name;
	}
}
