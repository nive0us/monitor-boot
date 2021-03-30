package niii.monitor.controller;

import niii.monitor.handler.RoutineJob;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/self")
public class LocalPublisherController {

    private RoutineJob job;

    @Autowired
    public LocalPublisherController(RoutineJob job) {
        this.job = job;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> list() {
        JSONArray arr = new JSONArray();
        arr = job.getRequestData();
        return new ResponseEntity<String>(arr.toString(), HttpStatus.OK);
    }

}
