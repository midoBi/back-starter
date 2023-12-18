package com.dev.backend.service;


import com.dev.backend.dto.UserDto;
import com.dev.backend.exception.DuplicatedException;
import com.dev.backend.exception.NotFoundException;
import com.dev.backend.mapper.UserMapper;
import com.dev.backend.model.User;
import com.dev.backend.repository.UserRepository;
import com.dev.backend.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findById(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, id));
        return userMapper.toDto(user);
    }

    public UserDto create(final UserDto dto) {
        if (userRepository.existsByCinEqualsIgnoreCase(dto.cin())) {
            throw new DuplicatedException(Constants.ERROR_CODE.USER_ALREADY_EXITED, dto.cin());
        }
        return userMapper.toDto(userRepository.save(userMapper.toEntity(dto)));
    }

    public UserDto update(final UserDto dto, final Long id) {

        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, id));

        return userMapper.toDto(userRepository.save(userMapper.toEntity(dto)));

    }


    public void delete(final Long id) {
        final boolean isUserExisted = userRepository.existsById(id);

        if (!isUserExisted) {
            throw new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, id);
        }

        userRepository.deleteById(id);
    }

}
