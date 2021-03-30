package niii.monitor.controller;

import niii.monitor.client.InfoGetter;
import niii.monitor.handler.SpaceHandler;
import niii.monitor.service.HttpClientService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/disk-monitor")
public class PublisherController {

    private final List<SpaceHandler> diskList;

    @Autowired
    public PublisherController( final List<SpaceHandler> ListDisks ) {
        this.diskList = ListDisks;
    }

    @Autowired
    private HttpClientService requester;

    @RequestMapping(value = "/boot", method = RequestMethod.GET)
    public ResponseEntity<String> listbyBoot() { // get data by @Service
        JSONArray arr = new JSONArray();
        List<SpaceHandler> diskInfo = new ArrayList<>();

        for (SpaceHandler iter : this.diskList) {
            System.out.printf( iter.getConfig().toString() );
            InfoGetter infoGetter = iter.getInfoGetter();
            String urlStr = infoGetter.getUrl() + ":" + infoGetter.getPort() + "/" + infoGetter.getSuffix();
            urlStr = urlStr.contains("http://") || urlStr.contains("https://") ? "" + urlStr : "http://" + urlStr ;

            JSONArray respArry = new JSONArray(requester.get(urlStr));

            if ( respArry.length() != 0) {
                JSONArray jary = respArry;

                Iterator<Object> it = jary.iterator();
                while (it.hasNext()) {
                    // System.out.println(it.next());
                    arr.put(it.next());
                }
            }
        }

        return new ResponseEntity<String>(arr.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/non-boot", method = RequestMethod.GET)
    public ResponseEntity<String> list() {
        JSONArray arr = new JSONArray();
        List<SpaceHandler> diskInfo = new ArrayList<>();


        for (SpaceHandler iter : this.diskList) {
            System.out.printf( iter.getConfig().toString() );
            if (iter.getRequestAry().length() != 0) {
                JSONArray jary = iter.getRequestAry();

                Iterator<Object> it = jary.iterator();
                while (it.hasNext()) {
                    // System.out.println(it.next());
                    arr.put(it.next());
                }
            }
        }

        return new ResponseEntity<String>(arr.toString(), HttpStatus.OK);
    }

}
