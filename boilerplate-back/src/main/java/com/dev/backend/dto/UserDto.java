package com.dev.backend.dto;

import com.dev.backend.model.enumeration.Gender;
import jakarta.validation.constraints.NotNull;

public record UserDto(Long id,
                      @NotNull String firstName,
                      @NotNull String lastName,
                      @NotNull String cin,
                      @NotNull Gender gender) {
}
