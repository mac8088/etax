package net.atos.etax.repository;

import net.atos.etax.domain.StdCodes;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Spring Data  repository for the StdCodes entity.
 */
@Repository
public interface StdCodesRepository extends JpaRepository<StdCodes, Long>, JpaSpecificationExecutor<StdCodes> {

    @Query(value = "SELECT sc.internal_code,sc.parent_internal_code,scd.external_code,scd.code_desc " +
        "FROM STD_CODES sc left join STD_CODES_DESC scd on sc.id=scd.std_codes_id  WHERE " +
        "sc.group_code = scd.group_code and sc.internal_code = scd.internal_code and sc.start_date = scd.start_date " +
        "and sc.group_code = ?1 and scd.lang_code = ?2 and( ?3 IS NULL OR (?3 IS NOT NULL AND sc. parent_internal_code = ?3 )) " +
        "and ((sc.end_date IS NULL  AND sc.start_date <= ?4 ) OR( sc.end_date IS NOT NULL AND sc.start_date <= ?4 AND ?4 <= sc.end_date )) " +
        "order by CAST ( ?5 AS CHAR(40)) " ,nativeQuery=true)
    List<Map<String,String>> findStdCodesByParamsAndDate(String groupCode, String lang, String parent, Date startDate,String orderBy);
}
