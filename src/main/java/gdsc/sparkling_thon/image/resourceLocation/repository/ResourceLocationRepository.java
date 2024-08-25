package gdsc.sparkling_thon.image.resourceLocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.sparkling_thon.image.resourceLocation.entity.ResourceLocationEntity;

public interface ResourceLocationRepository extends JpaRepository<ResourceLocationEntity, Long> {}
