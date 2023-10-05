package demo.currency.model.service;

import java.time.OffsetDateTime;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// @JsonInclude( Include.NON_NULL )
@JsonIgnoreProperties( ignoreUnknown = true )
public class HeaderDTO
{
    private static final long serialVersionUID = 1L;

	private Long id;

	@JsonProperty( access = Access.WRITE_ONLY )
	private Long[] ids;

	@NotBlank
	@Size( max = 10 )
	private String description;

	@URL
	@NotBlank
	private String url;

	@Positive
	@NotNull
	private Integer priority;

	@NotBlank
	@Pattern( regexp = "^[01]$" )
	private String status;

	@NotBlank
	@Pattern( regexp = "^[01]$" )
	private String openMethod;

	private String creator;
	private OffsetDateTime createdDate;
	private String modifier;
	private OffsetDateTime lastModified;
}