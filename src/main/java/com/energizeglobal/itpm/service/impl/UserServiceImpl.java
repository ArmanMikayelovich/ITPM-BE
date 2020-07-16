package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.repository.UserRepository;
import com.energizeglobal.itpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    @Override
    public UserEntity toEntity(UserDto userDto) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setEmail(userDto.getEmail());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        userEntity.setIsActive(userDto.getIsActive());

        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        final UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setIsActive(userEntity.getIsActive());
        return userDto;
    }
}
