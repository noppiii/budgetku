package com.budgetku.backend.mapper;

import com.budgetku.backend.model.User;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    @Mapping(target = "password", ignore = true)
    UserCredentialRequest toDTO(User user);

    @Mapping(target = "password", expression = "java(encodePassword(userCredentialRequest.getPassword(), passwordEncoder))")
    User toEntity(UserCredentialRequest userCredentialRequest, @Context PasswordEncoder passwordEncoder);

    AuthenticationResponse toDTO(String token, String refreshToken, UUID id);

    @Mapping(target = "password", expression = "java(encodePassword(userCredentialRequest.getNewPassword(), passwordEncoder))")
    void updateFromDTO(UserCredentialRequest userCredentialRequest, @MappingTarget User entity, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "password", ignore = true)
    void updateFromDTO(UserCredentialRequest userCredentialRequest, @MappingTarget User entity);

    default String encodePassword(String password, PasswordEncoder passwordEncoder) {
        return (password != null && !password.isEmpty()) ? passwordEncoder.encode(password) : null;
    }
}
