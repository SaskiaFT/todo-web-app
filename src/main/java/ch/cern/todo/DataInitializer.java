package ch.cern.todo;

import ch.cern.todo.entity.Category;
import ch.cern.todo.entity.User;
import ch.cern.todo.repository.CategoryRepository;
import ch.cern.todo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public DataInitializer(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category category1 = new Category();
            category1.setCategoryName("Groceries");
            category1.setCategoryDescription("Tasks for doing groceries");
            categoryRepository.save(category1);

            Category category2 = new Category();
            category2.setCategoryName("Work");
            category2.setCategoryDescription("Work related tasks");
            categoryRepository.save(category2);
        }

        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("Betsy");
            user1.setPassword("password"); // This should not be commited to gitHub
            user1.setRole(User.Role.USER);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("admin");
            user2.setPassword("password"); // This should not be commited to gitHub
            user2.setRole(User.Role.ADMIN);
            userRepository.save(user2);
        }

        System.out.println("Data has been added to the database!");
    }
}
