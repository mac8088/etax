package net.atos.etax.repository;

import net.atos.etax.domain.Uiapp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Uiapp entity.
 */
@Repository
public interface UiappRepository extends JpaRepository<Uiapp, Long>, JpaSpecificationExecutor<Uiapp> {

    @Query(value = "select distinct uiapp from Uiapp uiapp left join fetch uiapp.resources",
        countQuery = "select count(distinct uiapp) from Uiapp uiapp")
    Page<Uiapp> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct uiapp from Uiapp uiapp left join fetch uiapp.resources")
    List<Uiapp> findAllWithEagerRelationships();

    @Query("select uiapp from Uiapp uiapp left join fetch uiapp.resources where uiapp.id =:id")
    Optional<Uiapp> findOneWithEagerRelationships(@Param("id") Long id);

}
