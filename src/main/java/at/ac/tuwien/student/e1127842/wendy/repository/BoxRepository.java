package at.ac.tuwien.student.e1127842.wendy.repository;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface BoxRepository extends JpaRepository<Box, UUID>, JpaSpecificationExecutor {
	@Query("SELECT DISTINCT box from Box box " +
		"WHERE box.price > :minPrice " +
		"AND box.price < :maxPrice " +
		"AND box.deleted = false")
	List<Box> findAllByPrice(@Param("minPrice") BigDecimal minPrice,
				 @Param("maxPrice") BigDecimal maxPrice);

	@Query("SELECT DISTINCT box from Box box " +
		"WHERE box.price > :minPrice " +
		"AND box.price < :maxPrice " +
		"AND box.name LIKE %:name% " +
		"AND box.deleted = false")
	List<Box> findAllByPriceAndName(@Param("minPrice") BigDecimal minPrice,
					@Param("maxPrice") BigDecimal maxPrice,
					@Param("name") String name);

	@Query("SELECT DISTINCT box from Box box " +
		"WHERE box.price > :minPrice " +
		"AND box.price < :maxPrice " +
		"AND box.window = :window " +
		"AND box.deleted = false")
	List<Box> findAllByPriceAndWindow(@Param("minPrice") BigDecimal minPrice,
					  @Param("maxPrice") BigDecimal maxPrice,
					  @Param("window") boolean window);

	@Query("SELECT DISTINCT box from Box box " +
		"WHERE box.price > :minPrice " +
		"AND box.price < :maxPrice " +
		"AND box.window = :window " +
		"AND box.name LIKE %:name% " +
		"AND box.deleted = false")
	List<Box> findAllByPriceAndWindowAndName(@Param("minPrice") BigDecimal minPrice,
						 @Param("maxPrice") BigDecimal maxPrice,
						 @Param("window") boolean window,
						 @Param("name") String name);
}