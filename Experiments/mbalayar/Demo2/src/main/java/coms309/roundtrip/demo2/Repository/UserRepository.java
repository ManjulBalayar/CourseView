package coms309.roundtrip.demo2.Repository;
import coms309.roundtrip.demo2.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
