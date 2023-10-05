package demo.currency.model.service;

import java.time.OffsetDateTime;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties( { "ids" } )
public class HeaderDTO
{
    private static final long serialVersionUID = 1L;

	private Long id;
	private Long[] ids;

	@NotBlank
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