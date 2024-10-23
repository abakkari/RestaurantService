package com.restaurant_service.mapper;

import com.restaurant_service.bo.OpeningTime;
import com.restaurant_service.dto.OpeningTimeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpeningTimeMapper {

    OpeningTimeDto toOpeningTimeDto(OpeningTime openingTime);

}
