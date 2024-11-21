package org.example.springbootrestapi.repository;

import org.example.springbootrestapi.entity.LocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends ListCrudRepository<LocationEntity, Integer> {
    List<LocationEntity> findByStatusTrueAndDeletedFalse();

    List<LocationEntity> findByStatusTrueAndCategoryNameAndDeletedFalse(String categoryName);

    List<LocationEntity> findByUserIdAndDeletedFalse(int userId);

    List<LocationEntity> findByDeletedTrue();

    @Query("SELECT l FROM LocationEntity l WHERE ST_Distance_Sphere(ST_GeomFromText(CONCAT('POINT(', :lon, ' ', :lat, ')'), 4326), l.coordinate) <= :radius")
    List<LocationEntity> findAllWithinRadius(@Param("lon") double lon, @Param("lat") double lat, @Param("radius") double radius);

    //<T> List<T> findByStatusTrueAndCategoryName(String categoryName, Class<T> type);
}
