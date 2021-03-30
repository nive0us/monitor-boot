package niii.monitor.handler;

import lombok.Getter;
import lombok.Setter;
import niii.monitor.client.ClientCfg;
import niii.monitor.client.InfoGetter;
import niii.monitor.client.InfoGetterBoot;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 存取Smart Micro 歷史資料與即時資料之類別
 *
 * @author 62963
 */
@Getter
@Setter
public class SpaceHandler {
    private String source;
    private int port;

    private ClientCfg config;
    private InfoGetter infoGetter;

    private InfoGetterBoot infoGetterBoot;

    public SpaceHandler(ClientCfg config) {
        this.config = config;
//        this.source = config.getSource();
//        this.port = config.getPort();
        this.infoGetter = new InfoGetter( config ) ;
        this.infoGetterBoot = new InfoGetterBoot(config);

        if ( config.getEnable() ) {
            System.out.println( "Getter start");
//            new Thread(this.infoGetter).start();
        }


    }

    public JSONObject getRequestObj() {
        return infoGetter.getRequestObj();
    }

    public JSONArray getRequestAry() {
        return infoGetter.getRequestAry();
    }

    /**
     * 與路側設備是否連線
     */
    public boolean isGettingHttpData() {
//        if (server != null) {
//            return server.isConnected();
//        }
//
//        if (client != null) {
//            return client.isConnected();
//        }
        if ( infoGetter != null) {
            return infoGetter.isLaunched();
        }

        return false;
    }

    public ClientCfg getConfig() {
        return this.config;
    }


}
