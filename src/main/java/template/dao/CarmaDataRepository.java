package template.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import template.model.CarmaModel;

import java.util.List;

@Repository
public interface CarmaDataRepository extends MongoRepository<CarmaModel, Long> {
    @Query(value = "{'_id': '?0'}")
    public List<CarmaModel> findByEan(Long EAN);

    @Query(value = "{'token': '?0'}")
    public List<CarmaModel> findLatestByToken(String token, Pageable pageable);

}
