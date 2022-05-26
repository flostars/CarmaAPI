package template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import template.dao.CarmaDataRepository;
import template.model.CarmaModel;

@Service
public class CarmaModelService {

    @Autowired
    CarmaDataRepository carmaDataRepository;

    public void insertNewData(CarmaModel carmaModel) {
        carmaDataRepository.insert(carmaModel);
    }

    public CarmaModel getCarmaModelByToken(String token) {
        PageRequest price = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, CarmaModel.TIMESTAMP));
        return carmaDataRepository.findLatestByToken(token, price).stream().findFirst().get();
    }

}
