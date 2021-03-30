package niii.monitor.handler;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;


public class RoutineJob implements Runnable {

    private int CYCLE = 60;
    private String address;
    private String port;
    private JSONArray requestData;
    private JSONArray targetDisk;

    public RoutineJob(String address, String port, int interval, JSONArray targetDisk) {
        this.address = address;
        this.port = port;
        this.CYCLE = interval;
        this.targetDisk = targetDisk;
        this.requestData = new JSONArray();
    }

    @Override
    public void run() {
        while (true) {
            try {

                JSONArray publishAry = new JSONArray();
                for (int i = 0; i < targetDisk.length(); i++) {
                    String diskName = targetDisk.get(i).toString();

                    File file = new File(diskName+":\\");
                    JSONObject obj = new JSONObject();
                    obj.put("DiskName", diskName );
                    obj.put("Total", getTotalSpace(file));
                    obj.put("Usable", getUsableSpace(file));

                    publishAry.put(obj);
                }
                JSONObject obj = new JSONObject();
                obj.put("disks", publishAry );
                obj.put("address", address );


                //requestData = publishAry;
                requestData = new JSONArray();
                requestData.put(obj);

                File file = new File("C:\\");

                double size = file.getUsableSpace() / (1024.0 * 1024 * 1024);
                double total = file.getTotalSpace() / (1024.0 * 1024 * 1024);
                System.out.printf("%d GB/%d GB, %.2f%%\n", (int) size, (int) total, size / total * 100);
//                Logger.getRootLogger().info( (int) size + "GB/ " + (int) total + ", " + size / total * 100 );

//                HttpResponse res = requester.sendGet(url + ":" + port );
////                JSONObject obj = new JSONObject(res.getContent());
//                JSONArray arry = new JSONArray(res.getContent());
//                if (!arry.isEmpty()) {
//                    requestData = arry;
//                }
//                else {
//                    requestData = new JSONArray() ;
//                }

                Thread.sleep(CYCLE * 1000);
            } catch (Exception ex) {
                try {
                    Thread.sleep(CYCLE * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private float getUsableSpace( File file )  {
        try {
            return (float)(file.getUsableSpace() / (1024.0 * 1024 * 1024));
        } catch( Exception ex ) {
            return 0;
        }

    }
    private float getTotalSpace( File file ) {
        try {
            return (float)(file.getTotalSpace() / (1024.0 * 1024 * 1024));
        } catch( Exception ex ) {
            return 1;
        }
    }

    public JSONArray getRequestData() {
        return this.requestData;
    }
}
