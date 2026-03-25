package com.demo.SpendAI.mapper;

import com.demo.SpendAI.dto.RegisterRequest;
import com.demo.SpendAI.dto.UserDto;
import com.demo.SpendAI.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // Id'yi veritabanı otomatik oluşturacak
    @Mapping(target = "role", ignore = true) // Rolü Service katmanında set et
    User toEntity(RegisterRequest request);

    @Mapping(source = "id", target = "id")
    UserDto toDto(User user);


}
