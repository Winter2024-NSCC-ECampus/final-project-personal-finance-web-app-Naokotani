package com.web.finance.mapper;

import com.web.finance.dto.session.UserResponse;
import com.web.finance.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User UserDtoToUser(UserResponse user);
    UserResponse UserToUserDto(User user);
}
