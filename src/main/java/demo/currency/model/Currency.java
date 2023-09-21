package demo.currency.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

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

	public Currency( String code, String name ) {
		this.code = code;
		this.name = name;
	}

    @JsonIgnore
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
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
}
