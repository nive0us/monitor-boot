package niii.monitor.configuration;

import niii.monitor.client.ClientCfg;
import niii.monitor.handler.SpaceHandler;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppCfg {

    @Resource
    private Environment env;

    @Bean
    public List<SpaceHandler> ListDisks() throws Exception {
        List<SpaceHandler> diskList = new ArrayList<>() ;
        String content = new String(Files.readAllBytes(Paths.get(new File(env.getProperty("config.path")).toURI())),
                "UTF-8");
        JSONObject config = new JSONObject(content);


        // 收自己/其他server丟的http json data
        JSONArray receives = config.getJSONArray("Receive");
        SpaceHandler[] handlers = new SpaceHandler[receives.length()];
        for (int j = 0; j < receives.length(); j++) {
            JSONObject receive = receives.getJSONObject(j);
            ClientCfg configObj = new ClientCfg(receive);
            Logger.getRootLogger().info("enable: " + configObj.toString());
            handlers[j] = new SpaceHandler(configObj);


            diskList.add(handlers[j]) ;
        }




        return diskList;
    }


}
