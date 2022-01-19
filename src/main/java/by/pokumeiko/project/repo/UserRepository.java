package by.pokumeiko.project.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import by.pokumeiko.project.entity.Role;
import by.pokumeiko.project.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findUserByUsername(@Param("username") String username);
	
	public List<User> findAll();
	
	public Page<User> findAllByOrderByUsername(Pageable pageable);
	
	@Query("SELECT u FROM Role u WHERE u.name<>'READER'")
	public List<Role> getRoleList();
	
	@Query("SELECT u FROM Role u WHERE u.id = :id")
	public Role getRoleById(@Param("id") Long id);
}