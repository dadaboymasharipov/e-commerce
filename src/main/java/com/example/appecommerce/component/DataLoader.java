package com.example.appecommerce.component;

import com.example.appecommerce.entity.Address;
import com.example.appecommerce.entity.Category;
import com.example.appecommerce.entity.Role;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.entity.template.Permissions;
import com.example.appecommerce.repository.CategoryRepository;
import com.example.appecommerce.repository.RoleRepository;
import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.example.appecommerce.entity.template.Permissions.*;

/**
 * This class' methods run when program is executed for the first time
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    /**
     * This method helps to enter initial values to the system
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        //If initMode is Always then it executes statements
        if (initMode.equals("always")) {

            //We create owner role and give all permissions
            Role savedRoleOwner = roleRepository.save(new Role(
                    "Owner",
                    Arrays.asList(Permissions.values()),
                    "Owner of this system"
            ));

            //Create owner and give a role and properties
            User owner = new User(
                    "Owner",
                    "someEmail@gmail.com",
                    passwordEncoder.encode("owner"),
                    savedRoleOwner,
                    "+9119291121",
                    new Address("Toshkent", "Toshkent", "")
            );
            //We should set its enabled to true
            owner.setEnabled(true);

            //Saving the owner
            User savedOwner = userRepository.save(owner);


            //List of permissions the new user have
            List<Permissions> userPermissions = Arrays.asList(ADD_PRODUCT, EDIT_MY_USER, EDIT_PRODUCT, DELETE_MY_PRODUCT);

            //We create user role and give its permissions
            Role roleUser = new Role(
                    "User",
                    userPermissions,
                    "Newly registered to the system"
            );

            //roleUser is created by owner so we set its createdBy to owner id
            roleUser.setCreatedBy(savedOwner.getId());

            //Save roleUser to DB
            roleRepository.save(roleUser);

            //Creating some categories to begin with
            List<Category> categories = Arrays.asList(
                    new Category("Food and fruit"),
                    new Category("Technology"),
                    new Category("Clothes"),
                    new Category("Daily products"),
                    new Category("Drinks"),
                    new Category("Furniture")
            );

            //Saving categories to DB
            categoryRepository.saveAll(categories);

        }
    }
}
