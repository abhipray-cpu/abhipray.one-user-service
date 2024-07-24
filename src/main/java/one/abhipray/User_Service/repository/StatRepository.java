package one.abhipray.User_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import one.abhipray.User_Service.model.Stats;
import java.util.Optional;
@Repository
public interface StatRepository extends JpaRepository<Stats, Long> {

}
