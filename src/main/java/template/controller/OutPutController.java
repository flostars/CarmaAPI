package template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import template.model.CarmaModel;
import template.service.CarmaModelService;
import template.service.FileTool;

import java.io.File;
import java.io.IOException;
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
    public Object html(@RequestParam(value = "token") String token, @RequestParam(value = "type") String type) {
        CarmaModel carmaModelByToken = carmaModelService.getCarmaModelByToken(token);
        if(carmaModelByToken==null){
            return "<no token data present>";
        }
        String path = System.getProperty("user.dir") + File.separator + "htmlTemplates" + File.separator + "page3.html";
        if (type == null) {

        } else if (type.equals("1")) {
            path = System.getProperty("user.dir") + File.separator + "htmlTemplates" + File.separator + "page3.html";
        } else if (type.equals("2")) {
            path = System.getProperty("user.dir") + File.separator + "htmlTemplates" + File.separator + "page3Black.html";
        } else if (type.equals("3")) {
            path = System.getProperty("user.dir") + File.separator + "htmlTemplates" + File.separator + "page5.html";
        } else if (type.equals("4")) {
            path = System.getProperty("user.dir") + File.separator + "htmlTemplates" + File.separator + "page5Black.html";
        }

        String s = "";
        try {
            s = FileTool.readFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s = s.replaceAll("<PlantedTreesUK>", String.valueOf(carmaModelByToken.getPlantedTreesUK()))
                .replaceAll("<WorkdaysCreated>", String.valueOf(carmaModelByToken.getWorkdaysCreated()))
                .replaceAll("<CarbonOffsetCo2>", String.valueOf(carmaModelByToken.getCarbonOffsetCo2()))
                .replaceAll("<GoldStandardCo2>", String.valueOf(carmaModelByToken.getGoldStandardCo2Purchased()))
                .replaceAll("<PlantedTreesOffShore>", String.valueOf(carmaModelByToken.getPlantedTreesOffShore()))
                .replaceAll("<Total>", String.valueOf((carmaModelByToken.getPlantedTreesOffShore()+carmaModelByToken.getPlantedTreesUK())));
        return s;
    }


}