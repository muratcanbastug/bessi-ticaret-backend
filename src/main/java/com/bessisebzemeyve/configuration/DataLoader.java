package com.bessisebzemeyve.configuration;

import com.bessisebzemeyve.controller.UserController;
import com.bessisebzemeyve.entity.Role;
import com.bessisebzemeyve.entity.Unit;
import com.bessisebzemeyve.repository.RoleRepository;
import com.bessisebzemeyve.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final RoleRepository roleRepository;
    private final UnitRepository unitRepository;

    // User Roles
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    // Product Units
    private static final String KILOGRAM = "kg";
    private static final String PACKAGE = "paket";
    private static final String NUMBER = "tane";

    public DataLoader(RoleRepository roleRepository, UnitRepository unitRepository) {
        this.roleRepository = roleRepository;
        this.unitRepository = unitRepository;
    }

    private void loadProductUnit(String name) {
        if (!unitRepository.existsByName(name)) {
            Unit unit = new Unit();
            unit.setName(name);
            unitRepository.save(unit);
        }
    }

    private void loadUserRole(String name) {
        if (!roleRepository.existsByName(name)) {
            Role userRole = new Role();
            userRole.setName(name);
            roleRepository.save(userRole);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Data loader is running...");
        loadUserRole(ROLE_ADMIN);
        loadUserRole(ROLE_CUSTOMER);

        loadProductUnit(KILOGRAM);
        loadProductUnit(PACKAGE);
        loadProductUnit(NUMBER);
    }
}
