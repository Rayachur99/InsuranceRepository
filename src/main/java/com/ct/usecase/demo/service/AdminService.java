package com.ct.usecase.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ct.usecase.demo.entity.OrganizationEntity;
import com.ct.usecase.demo.entity.UserEntity;
import com.ct.usecase.demo.repository.OrganizationRepository;
import com.ct.usecase.demo.repository.UserRepository;
import com.ct.usecase.demo.util.OrganizationType;
import com.ct.usecase.demo.util.UserRole;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
                        OrganizationRepository organizationRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public OrganizationEntity createOrganization(String name, OrganizationType type) {
        OrganizationEntity org = new OrganizationEntity();
        org.setName(name);
        org.setType(type);
        return organizationRepository.save(org);
    }

    public UserEntity createUser(String username, String password,
                                 UserRole role, Long organizationId) {

        OrganizationEntity org = organizationRepository.findById(organizationId)
                .orElseThrow();

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        user.setOrganization(org);

        return userRepository.save(user);
    }
}
