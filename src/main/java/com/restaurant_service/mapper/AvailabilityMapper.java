package com.restaurant_service.mapper;

import com.restaurant_service.bo.Availability;
import com.restaurant_service.dto.AvailabilityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    AvailabilityDto toAvailabilityDto(Availability availability);

    List<AvailabilityDto> toAvailabilityDtos(List<Availability> availabilities);
}
