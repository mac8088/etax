package net.atos.etax.repository;

import net.atos.etax.domain.Authority;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
    @Query(value = "select ts08.* from jhi_user ts10, jhi_user_authority ts11, jhi_authority ts08 "
    		+ "where ts10.id = ts11.user_id and ts08.name = ts11.authority_name "
    		+ "and login= ?1 and (ts08.name != 'ROLE_USER' AND ts08.name != 'ROLE_ADMIN');", nativeQuery=true)
    List<Authority> findAuthorityByLoginName(String login);
}
