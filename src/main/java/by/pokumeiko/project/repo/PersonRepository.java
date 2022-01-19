package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.pokumeiko.project.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	public Person findByIdIs (Long id);
	
	@Modifying
	@Query(value = "UPDATE users SET  user_password = :user_password WHERE user_id = :id", nativeQuery = true)
	public void saveNewPassword(@Param("user_password") String user_password, @Param("id") Long id);
	
	@Modifying
	@Query(value = "UPDATE person SET email = :email, phone=:phone WHERE id = :id", nativeQuery = true)
	public void saveAccount(@Param("email") String email, @Param("phone") String phone, @Param("id") Long id);
	
	@Modifying
	@Query(value = "INSERT INTO users (username, user_password, enabled) VALUES (:username, :user_password, 1)", nativeQuery = true)
	public void saveNewUser(@Param("username") String username, @Param("user_password") String user_password);
	
	@Query(value = "SELECT p.* FROM person p WHERE not exists (SELECT id FROM users_roles WHERE user_id = p.id and (role_id=2 or role_id=3)) ORDER BY fname, iname, oname", nativeQuery = true)
	public Page<Person> getReaders(Pageable pageable);

}
