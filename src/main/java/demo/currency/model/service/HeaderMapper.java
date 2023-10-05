package demo.currency.model.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.BeanUtils;

import demo.currency.model.Header;

public class HeaderMapper
{
    public static HeaderDTO toHeaderDTO( Header header )
    {
        var dto = new HeaderDTO();
        BeanUtils.copyProperties( header, dto );
        
        return dto;
    }

    public static Header toHeader( HeaderDTO headerDTO )
    {
        var header = new Header();
        BeanUtils.copyProperties( headerDTO, header );
        
        return header;
    }

    public static List< HeaderDTO > toHeaderDTOs( List< Header > headers )
    {
        return headers.stream().map( HeaderMapper::toHeaderDTO )
                               .collect( toList() );
    }
}
