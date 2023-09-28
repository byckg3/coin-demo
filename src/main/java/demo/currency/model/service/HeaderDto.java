package demo.currency.model.service;

import java.time.OffsetDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HeaderDto
{
    private static final long serialVersionUID = 1L;

	private Long id;
	private Long[] ids;

	@NotBlank
	private String description;

	@URL
	private String url;

	@Positive
	private Integer priority;
	private String status;
	private String openMethod;
	private String creator;
	private OffsetDateTime createdDate;
	private String modifier;
	private OffsetDateTime lastModified;
}