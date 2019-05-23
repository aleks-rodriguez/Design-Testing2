
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	@Query("select f from FixUpTask f where f.category.id = ?1")
	//	Collection<FixUpTask> findAllFixUpTasksByCategory(int id);

	//	@Query("select f from Finder f where f.category.id = ?1")
	//	Collection<Finder> findAllFinderByCategory(int id);
}
