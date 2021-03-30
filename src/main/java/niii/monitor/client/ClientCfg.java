package niii.monitor.client;

//import com.ceci.smartmicro.enums.Model;
//import com.ceci.smartmicro.models.Smartmicro;
import org.json.JSONArray;
import org.json.JSONObject;
//import utcs.core.common.prop.ADVProperties;


public class ClientCfg {
    private String src;
    private int port;
    private String suffix;
    private String title;
    private float usableSpace;
    private float thresholdPercent;
    private String comment;
    private boolean enabled;
    private JSONArray targetDisk;
    private int interval;


    public ClientCfg( JSONObject cfg ) {
        this.src = cfg.getString("Src");
        this.port = cfg.getInt("Port");
        this.title = cfg.getString("Title");
        this.suffix = cfg.has("Suffix") ? cfg.getString("Suffix") : "self" ;
        this.usableSpace = cfg.getFloat("UsableSpace");
        this.thresholdPercent = cfg.getFloat("Threshold_percent");
        this.comment = cfg.getString("Comment");
        this.enabled = cfg.getBoolean("Enabled");
        this.targetDisk = cfg.getJSONArray("TargetDisk");
        this.interval = cfg.getInt("Interval");

    }

    public String getSource() {
        return src;
    }

    public int getPort() {
        return port;
    }
    public String getSuffix(){ return suffix; }

    public String getTitle() { return title;}
    public float getUsableSpace() { return usableSpace;}
    public float getThresholdPercent() { return thresholdPercent;}
    public String getComment() { return comment;}
    public JSONArray getTargetDisk() { return  targetDisk;}
    public int getInterval() {
        return interval;
    }
    public boolean getEnable() { return  enabled;}




    @Override
    public String toString() {
        return "SMConfig{" +
                "source='" + src + '\'' +
                ", port=" + port +
                ", interval=" + interval +
                ", title='" + title + '\'' +
                ", targetDisk=" + targetDisk.toString() +
                '}';
    }
}

