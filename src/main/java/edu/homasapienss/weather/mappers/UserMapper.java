package edu.homasapienss.weather.mappers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto dto);
    List<UserDto> toUserDtoList(List<User> users);
}
