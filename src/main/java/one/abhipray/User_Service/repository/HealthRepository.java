package one.abhipray.User_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import one.abhipray.User_Service.model.Health;
import java.util.List;
@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {

}
