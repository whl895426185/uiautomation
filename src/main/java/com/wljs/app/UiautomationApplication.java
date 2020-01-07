package com.wljs.app;


import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import com.wljs.pojo.ResponseData;
import com.wljs.pojo.StfDevicesFields;
import com.wljs.service.StfDevicesService;
import com.wljs.util.GetFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;

public class UiautomationApplication {

    public static void main(String[] args) {
        //查看当前设备是否已连接且闲余
        List<String> devicesList = getDevicesList();

        //读取xml信息
        GetFiles files = new GetFiles();
        List<String> fileList = files.getAllFileName("/Volumes/SoureCode/testng/");

        if (null == devicesList || devicesList.size() < 1) {
            return;
        }

        if (null == fileList || fileList.size() < 1) {
            return;
        }

        //放入安全队列里面
        List<String> stfDevices = new ArrayList<>();
        ArrayBlockingQueue queueList = new ArrayBlockingQueue(fileList.size());
        for (String filename : fileList) {
            String key = filename.split("testng_")[1];
            key = key.split(".xml")[0];
            for (String serial : devicesList) {
                if (key.equals(serial)) {
                    queueList.add(filename);
                    stfDevices.add(serial);
                    break;
                }
            }

        }

        //STF占用设备
//        occupancyResources(stfDevices);

        if (null == queueList || queueList.size() < 1) {
            return;
        }

        //定义线程池
        ExecutorService pool = Executors.newFixedThreadPool(queueList.size());
        List<Future<ResponseData>> futureList = new ArrayList<Future<ResponseData>>();

        for (int i = 1; i <= queueList.size(); i++) {
            ExecuteTestng worker = new ExecuteTestng(queueList);
            Future<ResponseData> future = pool.submit(worker);
            futureList.add(future);
        }
    }

    /**
     * 占用设备资源
     *
     * @param stfDevices
     */
    private static void occupancyResources(List<String> stfDevices) {
        StfDevicesService devicesService = new StfDevicesService();
        devicesService.occupancyResources(stfDevices);
    }

    /**
     * 获取已连接且闲余的设备信息
     *
     * @return
     */
    private static List<String> getDevicesList() {
        RethinkDB r = RethinkDB.r;

        //开启rethinkdb连接
        Connection conn = r.connection().hostname("192.168.88.233").port(28015).connect();

        //获取已连接且闲余的设备信息（present为true表示已连接，owner为空表示设备闲余）
        List<StfDevicesFields> dbList = null;

        Cursor<StfDevicesFields> cursor = r.db("stf")
                .table("devices")
                .filter(r.hashMap("present", true).with("owner", null).with("supportAutomation", 1))
                .filter(devices -> devices.g("platform").eq("Android").or(devices.g("platform").eq("iOS")))
                .withFields("manufacturer", "model", "serial", "version", "platform")
                .run(conn, Cursor.class);

        dbList = cursor.bufferedItems();

        //关闭Rethinkdb连接
        conn.close();


        if (null == dbList || dbList.size() < 1) {
            return null;
        }

        List<String> devicelist = new ArrayList<>();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject object = JSONObject.fromObject(dbList.get(i));
            StfDevicesFields fields = (StfDevicesFields) JSONObject.toBean(object, StfDevicesFields.class);

            devicelist.add(fields.getSerial());
        }
        return devicelist;
    }

}
