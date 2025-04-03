package com.web.store.mapper;

import com.web.store.dto.session.UserResponse;
import com.web.store.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User UserDtoToUser(UserResponse user);
    UserResponse UserToUserDto(User user);
}
