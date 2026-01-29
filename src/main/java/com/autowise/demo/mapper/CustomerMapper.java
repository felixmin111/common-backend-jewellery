package com.autowise.demo.mapper;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "hashPassword", target = "password")
    CustomerDto toDto(Customer entity);

    @Mapping(source = "password", target = "hashPassword")
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "hashPassword")
    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer entity);

}

