package com.autowise.demo.mapper;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "hashPassword", target = "password")
    CustomerDto toDto(Customer entity);

    @Mapping(source = "password", target = "hashPassword")
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDto dto);

    // DTO -> existing Entity (for update, only non-null fields)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "hashPassword")
    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer entity);

}

