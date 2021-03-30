package niii.monitor.client;

import lombok.Getter;
import lombok.Setter;
import niii.monitor.service.HttpClientService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;

//import java.net.http.HttpRequest;
//import utcs.core.common.http.HttpRequester;
//import utcs.core.common.http.HttpResponse;
@Getter
@Setter
public class InfoGetter implements Runnable {

    // private final static int CYCLE = 60;
    private int CYCLE = 60;
    private String url;
    private String port;
//    private HttpRequest requester;
//    private HttpRequester requester;
    private JSONArray requestAry;
    private String suffix;
    private JSONObject requestObj;
    private boolean enabled;

    private boolean launched;

    //@Autowired
    private HttpClientService requester;

    public InfoGetter(ClientCfg config) {
        this.url = config.getSource();
        this.port = Integer.toString(config.getPort());
        this.suffix = config.getSuffix();
        this.CYCLE = config.getInterval();
        //this.requester = new HttpClientService ;
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.requester = new HttpClientService(restTemplateBuilder);
        this.enabled = config.getEnable();
        System.out.println("InfoGetter init : " + url + ":" + port + "/" + suffix);
        Logger.getRootLogger().info("InfoGetter init : " + url + ":" + port + "/" + suffix);
        this.requestObj = new JSONObject();

        this.launched = false;
    }

    public JSONObject getRequestObj() {
        try {
            if (!enabled)
                return new JSONObject();
            return requestObj;

        } catch (Exception e) {
            return new JSONObject();
        }

    }

    public JSONArray getRequestAry() {
        try {
            if (!enabled)
                return new JSONArray();
            return requestAry;

        } catch (Exception e) {
            return new JSONArray();
        }

    }

    public boolean isLaunched() {
        return launched;
    }

    @Override
    public void run() {

        while (true) {
            try {
                launched = true;
                this.requestObj = new JSONObject();

                String urlStr = "http://" + url + ":" + port + "/" + suffix;

//                HttpResponse res = requester.sendGet(urlStr);
//                JSONObject obj = new JSONObject(res.getContent());
//                JSONArray arry = new JSONArray(res.getContent());
//                Logger.getRootLogger().info(urlStr + " >>> " + res.getContent());

                JSONArray arry = new JSONArray(requester.get(urlStr));
                if (arry.length()==0) {
                    requestAry = arry;
                } else {
                    requestAry = new JSONArray();
                }

//                requestObj.put("address", urlStr);
//                requestObj.put("disks", requestAry);
                 Logger.getRootLogger().info( urlStr + " ,infoGetter: " + requestAry);

                Thread.sleep(CYCLE * 1000);
            } catch (Exception ex) {
                launched = false;
                ex.printStackTrace();
                try {
                    Thread.sleep(CYCLE * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

