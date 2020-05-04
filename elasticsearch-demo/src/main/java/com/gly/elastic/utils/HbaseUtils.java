//package com.gly.elastic.utils;
//
//import com.gly.common.utils.DateUtil;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.CellUtil;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.filter.FilterList;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.*;
//
//public class HbaseUtils {
//    private static Logger logger = LoggerFactory.getLogger(HbaseUtils.class);
//
//    private String url = "";
//
//    private static Connection connection;
//
//    private static Admin admin;
//
//    private static HbaseUtils instance = null;
//
//    private HbaseUtils() {
//        getHbaseConnection();
//    }
//
//    public static synchronized HbaseUtils getInstance(){
//        if(instance == null){
//            instance = new HbaseUtils();
//        }
//        return instance;
//    }
//
//    private void getHbaseConnection() {
//        try {
////            ExecutorService pool = Executors.newFixedThreadPool(10);//建立一个固定大小的线程池
//            Configuration conf = HBaseConfiguration.create();
////            conf.set("hbase.zookeeper.quorum", "192.168.239.151");//zookeeper地址
//            conf.set("hbase.zookeeper.quorum", "hb-bp11406mdwp8cugp5-master1-001.hbase.rds.aliyuncs.com,hb-bp11406mdwp8cugp5-master2-001.hbase.rds.aliyuncs.com,hb-bp11406mdwp8cugp5-master3-001.hbase.rds.aliyuncs.com");//zookeeper地址
////            connection = ConnectionFactory.createConnection(conf,pool);
//            conf.setInt("hbase.rpc.timeout",2000000);
//            conf.setInt("hbase.client.operation.timeout",3000000);
//            conf.setInt("hbase.client.scanner.timeout.period",20000000);
//            connection = ConnectionFactory.createConnection(conf);
//            admin = connection.getAdmin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ResultScanner scan2(String tableName, String vin,long startTime,long endTime){
//        return scan2(tableName,vin,startTime,endTime,null,1);
//    }
//
//    public ResultScanner scanNonLimit(String tableName, String vin,long startTime,long endTime){
//        return scan2(tableName,vin,startTime,endTime,null,0);
//    }
//
//    public ResultScanner scanLimit(String tableName, String vin,long startTime,long endTime,int limit){
//        return scan2(tableName,vin,startTime,endTime,null,limit);
//    }
//
//    public ResultScanner scan2(String tableName, String vin,long startTime,long endTime,FilterList filterList){
//        return scan2(tableName,vin,startTime,endTime,filterList,1);
//    }
//
//    public ResultScanner scan2NoLimit(String tableName, String vin,long startTime,long endTime,FilterList filterList){
//        return scan2(tableName,vin,startTime,endTime,filterList,0);
//    }
//
//    public ResultScanner scan2(String tableName, String vin,long startTime,long endTime,FilterList filterList,int limit){
//        Table table = null;
//        ResultScanner resultScanner = null;
//        try {
//            table = connection.getTable(TableName.valueOf(tableName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Scan scan = new Scan();
//        String vinV = DigestUtils.md5Hex(vin).toLowerCase().substring(0, 4) + vin;
//        scan.withStartRow(Bytes.toBytes(vinV + startTime));
//        scan.withStopRow(Bytes.toBytes(vinV + (endTime + 1)));
//        if(null != filterList){
//            scan.setFilter(filterList);
//        }
//        if(limit > 0){
//            scan.setLimit(limit);
//        }
//        try {
//            resultScanner = table.getScanner(scan);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultScanner;
//    }
//
//
//    public ResultScanner scan4(String tableName, String vin,long startTime,long endTime,int limit,FilterList filterList){
//        Table table = null;
//        ResultScanner resultScanner = null;
//        try {
//            table = connection.getTable(TableName.valueOf(tableName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String vinV = DigestUtils.md5Hex(vin).toLowerCase().substring(0, 4) + vin;
//        Scan scan = new Scan();
//        scan.withStartRow(Bytes.toBytes(vinV + startTime));
//        scan.withStopRow(Bytes.toBytes(vinV + (endTime + 1)));
//        if(limit > 0){
//            scan.setMaxResultSize(limit);
//            scan.setLimit(limit);
//        }
//        if(null != filterList){
//            scan.setFilter(filterList);
//        }
//        try {
//            resultScanner = table.getScanner(scan);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultScanner;
//    }
//
//    public ResultScanner scan3(String tableName, String vin,long startTime,long endTime,FilterList filterList){
//        Table table = null;
//        ResultScanner resultScanner = null;
//        try {
//            table = connection.getTable(TableName.valueOf(tableName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Scan scan = new Scan();
//        String vinV = DigestUtils.md5Hex(vin).toLowerCase().substring(0, 4) + vin;
//        if(null != filterList){
//            scan.setFilter(filterList);
//        }
//        scan.withStartRow(Bytes.toBytes(vinV + endTime+1));
//        scan.withStopRow(Bytes.toBytes(vinV + startTime));
//        scan.setLimit(1);
//        scan.setReversed(true);
//        scan.setCaching(5);
////        scan.setBatch(20);
//        try {
//            resultScanner = table.getScanner(scan);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultScanner;
//    }
//
//    public static HashMap<String, String> genarateMapByField(Result result) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        for (Cell cell : result.listCells()) {
//            //取行健
//            String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
//            //取到时间戳
//            long timestamp = cell.getTimestamp();
//            //取到族列
//            String family = Bytes.toString(CellUtil.cloneFamily(cell));
//            //取到修饰名
//            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//            //取到值
//            String value = Bytes.toString(CellUtil.cloneValue(cell));
////                System.out.println(" ====> RowKey : " + rowKey + ",  Timestamp : " +
////                        timestamp + ", ColumnFamily : " + family + ", Key : " + qualifier
////                        + ", Value : " + value);
////                System.out.println("rowkey"+rowKey+",key:"+qualifier+",value:"+value+",");
//            map.put(qualifier,value);
//            map.put("rowKey",rowKey);
//            map.put("receiveTime", DateUtil.dateToString(new Date(timestamp),"yyyyMMddHHmmss"));
//        }
//        return map;
//    }
//
//    public static List<Map<String, String>> analysisResult(ResultScanner resultScanner) {
//        List<Map<String,String>> rtList = new ArrayList<>();
//        for(Result result:resultScanner){
//            HashMap<String, String> map = new HashMap<String, String>();
//            for (Cell cell : result.listCells()) {
//                //取行健
//                String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
//                //取到时间戳
//                long timestamp = cell.getTimestamp();
//                //取到族列
//                String family = Bytes.toString(CellUtil.cloneFamily(cell));
//                //取到修饰名
//                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
//                //取到值
//                String value = Bytes.toString(CellUtil.cloneValue(cell));
////                System.out.println(" ====> RowKey : " + rowKey + ",  Timestamp : " +
////                        timestamp + ", ColumnFamily : " + family + ", Key : " + qualifier
////                        + ", Value : " + value);
////                System.out.println("rowkey"+rowKey+",key:"+qualifier+",value:"+value+",");
//                map.put(qualifier,value);
//                map.put("rowKey",rowKey);
//                map.put("receiveTime", DateUtil.dateToString(new Date(timestamp),"yyyyMMddHHmmss"));
//            }
//            rtList.add(map);
//
//        }
//        return rtList;
//    }
//
//
//    public ResultScanner scan4(String table, String vin, long startTime, long endTime, FilterList filterList) {
//        return scan4(table,vin,startTime,endTime,0,filterList);
//    }
//
//    public ResultScanner scan4(String table, String vin, long startTime, long endTime,int limit) {
//        return scan4(table,vin,startTime,endTime,limit,null);
//    }
//}
