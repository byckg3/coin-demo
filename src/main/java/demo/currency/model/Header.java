package demo.currency.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode( callSuper = true )
public class Header  extends BaseEntity
{
    private static final long serialVersionUID = 1L;

	private Long id;
	private String description;
	private String url;
	private Integer priority;
	private String status = "0";
	private String openMethod = "0";
	private String creator;
	private String modifier;
}
