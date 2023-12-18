package com.dev.backend.controller;


import com.dev.backend.dto.ErrorDto;
import com.dev.backend.dto.UserDto;
import com.dev.backend.service.UserService;
import com.dev.backend.utils.Constants;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Constants.ApiConstant.USER_URL)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_200, description = Constants.ApiConstant.OK, content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_404, description = Constants.ApiConstant.NOT_FOUND, content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<UserDto> getUser(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_201, description = Constants.ApiConstant.CREATED, content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_400, description = Constants.ApiConstant.BAD_REQUEST, content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody final UserDto userDto,
            final UriComponentsBuilder uriComponentsBuilder) {
        final UserDto result = userService.create(userDto);
        return ResponseEntity.created(
                        uriComponentsBuilder
                                .replacePath("/users/{id}")
                                .buildAndExpand(result.id())
                                .toUri())
                .body(result);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_204, description = Constants.ApiConstant.NO_CONTENT, content = @Content()),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_404, description = Constants.ApiConstant.NOT_FOUND, content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_400, description = Constants.ApiConstant.BAD_REQUEST, content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<Void> updateUser(@PathVariable final Long id,
                                           @Valid @RequestBody final UserDto dto) {
        userService.update(dto, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_204, description = Constants.ApiConstant.NO_CONTENT, content = @Content()),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_404, description = Constants.ApiConstant.NOT_FOUND, content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = Constants.ApiConstant.CODE_400, description = Constants.ApiConstant.BAD_REQUEST, content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
