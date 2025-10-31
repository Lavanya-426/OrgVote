package com.orgvoteapp.service;

import com.orgvoteapp.model.Admin;
import com.orgvoteapp.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin register(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
