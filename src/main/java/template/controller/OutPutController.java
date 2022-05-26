package template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import template.model.CarmaModel;
import template.service.CarmaModelService;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
public class OutPutController {

    @Autowired
    CarmaModelService carmaModelService;

//    @GetMapping("/stores")
//    public Map<Object, Object> stores() {
//        return Collections.singletonMap("sample", CarmaModel.builder()
//                .carbonOffsetCo2(1)
//                .goldStandardCo2Purchased(2)
//                .plantedTreesOffShore(3)
//                .plantedTreesUK(5)
//                .workdaysCreated(33)
//                .timeUpdated(System.currentTimeMillis())
//                .build());
//    }

    @PostMapping(path = "/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object stores(@RequestBody CarmaModel carmaModel, @RequestParam(value = "token") String token) {
        carmaModel.setToken(token);
        carmaModelService.insertNewData(carmaModel);
        return Collections.singletonMap("Success", "added");
    }

    @GetMapping(path = "/data")
    public Object stores(@RequestParam(value = "token") String token) {
        CarmaModel carmaModelByToken = carmaModelService.getCarmaModelByToken(token);
        double carbonOffset = carmaModelByToken.getPlantedTreesUK() * 0.7 + carmaModelByToken.getPlantedTreesOffShore() * 0.3;
        carmaModelByToken.setCarbonOffsetCo2(carbonOffset);
        double workDays = (carmaModelByToken.getPlantedTreesUK() + carmaModelByToken.getPlantedTreesOffShore()) / 100;
        carmaModelByToken.setWorkdaysCreated(workDays);
        return carmaModelByToken;
    }

    @GetMapping(path = "/datahtml")
    public Object html(@RequestParam(value = "token") String token) {
        CarmaModel carmaModelByToken = carmaModelService.getCarmaModelByToken(token);

        return carmaModelByToken;
    }


}