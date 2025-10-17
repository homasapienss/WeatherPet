package edu.homasapienss.weather.mappers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
    @Mapping(target = "id", ignore = true)
    User toUser(UserDto dto);
    List<UserDto> toUserDtoList(List<User> users);
}
