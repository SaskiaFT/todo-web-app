package ch.cern.todo.repository;

import ch.cern.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Username(String username);

    //  Search for user, name, description, deadline and category in any combination
    @Query("SELECT t FROM Task t WHERE " +
            "(:userId IS NULL OR t.user.userId = :userId) AND " +
            "(:name IS NULL OR LOWER(t.taskName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:description IS NULL OR LOWER(t.taskDescription) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
            "(:deadline IS NULL OR t.deadline = :deadline) AND " +
            "(:categoryId IS NULL OR t.category.categoryId = :categoryId)")
    List<Task> findByFilters(
            @Param("userId") Long userId,
            @Param("name") String name,
            @Param("description") String description,
            @Param("deadline") LocalDateTime deadline,
            @Param("categoryId") Long categoryId
    );


}
