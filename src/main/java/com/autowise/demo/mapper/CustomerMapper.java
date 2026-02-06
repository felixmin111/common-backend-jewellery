package com.autowise.demo.mapper;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.model.Customer;
import org.mapstruct.*;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer entity);

    Customer toEntity(CustomerDto dto);

    // ✅ important: ignore null on update so it won't wipe fields accidentally
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer entity);
}