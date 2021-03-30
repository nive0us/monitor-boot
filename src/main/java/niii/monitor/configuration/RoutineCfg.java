package niii.monitor.configuration;

import niii.monitor.handler.RoutineJob;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class RoutineCfg {
    @Resource
    private Environment env;

    @Bean
    public RoutineJob Job() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(new File(env.getProperty("config.path")).toURI())),
                "UTF-8");
        JSONObject config = new JSONObject(content);


        boolean localEnabled = config.has("LocalEnabled") ? config.getBoolean("LocalEnabled") : false ;
        if (localEnabled) {
            // 發布本機資料
            JSONArray localTargetDisk = config.getJSONArray("TargetDisk");
            String localAddress = config.getString("LocalAddress");
            String localPort = config.getString("LocalPort");
            int interval = config.getInt("Interval");
            RoutineJob job = new RoutineJob( localAddress, localPort, interval, localTargetDisk);

            new Thread(job).start();
            // new Thread(new LocalPublisher("self", Integer.parseInt(localPort), job)).start();
            return job ;
        }
        return null;
    }
}
