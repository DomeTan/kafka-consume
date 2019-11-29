package cn.waner.kafkaconsume.file.service;

import cn.waner.kafkaconsume.common.cahce.UploadTaskCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TanChong
 * create date 2019\11\9 0009
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private String logSrcStoragePath;
    // 换行字节
    private byte[] LINE_FEED;
    // 换行字节长度
    private int LEN_LINE_FEED;
    // 分割字节
    private byte[] SEGMENTATION;
    // 分割字节长度
    private int LEN_SEGMENTATION;
    // 源IP字节长度 LINUX
    private final static int LEN_SRC_ADDR=12;
    // 源IP字节长度 WINDOWS
//    private final static int LEN_SRC_ADDR=15;
    private UploadTaskCacheManager uploadTaskCacheManager;


    @Autowired
    public FileService(@Value("${first_floor.log.src.storage.path}") String logSrcStoragePath,
                       UploadTaskCacheManager uploadTaskCacheManager) {
        this.logSrcStoragePath = logSrcStoragePath;
        LINE_FEED = "\n".getBytes();
        LEN_LINE_FEED = LINE_FEED.length;
        SEGMENTATION = "\t".getBytes();
        LEN_SEGMENTATION = SEGMENTATION.length;
        this.uploadTaskCacheManager = uploadTaskCacheManager;
    }

    /**
     * 存储日志原文:
     *  判断
     * @param ipAddress 日志来源ip地址
     * @param content 日志内容
     */
    public synchronized void save(String ipAddress,byte[] content){
        content = new String(content).trim().getBytes();
        byte[] addressBytes = ipAddress.getBytes();
        byte[] timeBytes = timeToBytes();

         //ip 日志内容 接收时间 拼接
        byte[] data = new byte[(addressBytes.length + LEN_SEGMENTATION + content.length + LEN_SEGMENTATION + LEN_LINE_FEED+timeBytes.length)];
        // 将ip合并到data
        System.arraycopy(addressBytes,0,data,0,addressBytes.length);
        // 将\t合并到data
        System.arraycopy(SEGMENTATION,0,data,addressBytes.length,LEN_SEGMENTATION);
        // 将时间合并到data
        System.arraycopy(timeBytes,0,data,(addressBytes.length + LEN_SEGMENTATION),timeBytes.length);
        // 将\t合并到data
        System.arraycopy(SEGMENTATION,0,data,(addressBytes.length + LEN_SEGMENTATION+ timeBytes.length),LEN_SEGMENTATION);
        // 将日志原文合并到data
        System.arraycopy(content,0,data,(addressBytes.length+LEN_SEGMENTATION+LEN_SEGMENTATION+timeBytes.length),content.length);
        // 将换行字节合并到data
        System.arraycopy(LINE_FEED,0, data,(addressBytes.length + LEN_SEGMENTATION+ timeBytes.length + LEN_SEGMENTATION+ content.length),LEN_LINE_FEED);

        // 文件名称以及文件夹创建
        LocalDateTime datetime = LocalDateTime.now();
        String currentDate = coverage(String.valueOf(datetime.getYear())) + "-"
                + coverage(String.valueOf(datetime.getMonth().getValue())) + "-"
                + coverage(String.valueOf(datetime.getDayOfMonth()));
        String currentHour = String.valueOf(datetime.getHour());

        String fileNameForLinux = appendPath(datetime);

        // 返回资产主目录 \downFile\original\14.13.10.134\
        String firstDirPath = getDirectoryPath(logSrcStoragePath,"");
        // 返回资产以日期区分的目录 \downFile\original\14.13.10.134\2019-8-21\
        String secondDirPath = getDirectoryPath(firstDirPath, currentDate);
        String sqlPath = new StringBuilder(secondDirPath).append(fileNameForLinux).append(".log").toString();
        if (!Files.exists(Paths.get(secondDirPath))){
            try {
                Files.createDirectories(Paths.get(secondDirPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path path = Paths.get(sqlPath);
        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);
        SeekableByteChannel seekableByteChannel = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            seekableByteChannel = Files.newByteChannel(path, options);
            seekableByteChannel.write(byteBuffer);

        } catch (IOException e) {
            LOGGER.error("写入日志到" + secondDirPath + currentHour + ".txt失败！", e);
            e.printStackTrace();
        }finally {
            try {
                seekableByteChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储日志原文:
     *  判断
     * @param content 日志内容
     */
    public void save(byte[] content){
        byte[] ipAddrBytes = new byte[LEN_SRC_ADDR];
        // 获取源IP
        System.arraycopy(content,0,ipAddrBytes,0,LEN_SRC_ADDR);
        String suffixPath = new String(ipAddrBytes).trim();
        // 拼接换行
        byte[] data = new byte[(content.length + LEN_LINE_FEED)];
        System.arraycopy(content,0,data,0,content.length);
        System.arraycopy(LINE_FEED,0,data,content.length,LEN_LINE_FEED);

        // 文件名称以及文件夹创建
        LocalDateTime datetime = LocalDateTime.now();
        String currentDate = coverage(String.valueOf(datetime.getYear())) + "-"
                + coverage(String.valueOf(datetime.getMonth().getValue())) + "-"
                + coverage(String.valueOf(datetime.getDayOfMonth()));
        String currentHour = String.valueOf(datetime.getHour());

        String fileNameForLinux = appendPath(datetime);

        // 返回资产主目录 \downFile\original\14.13.10.134\
        String firstDirPath = getDirectoryPath(logSrcStoragePath,suffixPath);
        // 返回资产以日期区分的目录 \downFile\original\14.13.10.134\2019-8-21\
        String secondDirPath = getDirectoryPath(firstDirPath, currentDate);
        String sqlPath = new StringBuilder(secondDirPath).append(fileNameForLinux).append(".log").toString();

        if (!Files.exists(Paths.get(secondDirPath))){
            try {
                Files.createDirectories(Paths.get(secondDirPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path path = Paths.get(sqlPath);
        submitTask(sqlPath);
        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);
        SeekableByteChannel seekableByteChannel = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            seekableByteChannel = Files.newByteChannel(path, options);
            seekableByteChannel.write(byteBuffer);
            byteBuffer.clear();
        } catch (IOException e) {
            LOGGER.error("写入日志到" + secondDirPath + currentHour + ".txt失败！", e);
            e.printStackTrace();
        } finally {
            try {
                seekableByteChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 返回prefixPath参数和suffixPath参数相加的绝对路径。
     * 如果该路径不是一个目录，则抛出NotDirectoryPathException异常。
     * 如果不存在，则创建这个目录，并返回这个目录路径。
     * 如果存在，则返回这个目录路径。
     *
     * 注意：返回的目录路径会以当前系统的文件路径分隔符结尾。
     * 如在Linux中，会以“/”结尾。
     */
    private String getDirectoryPath(String prefixPath, String suffixPath) {
        String separator = "";

        if (!prefixPath.endsWith(File.separator)) {
            separator = File.separator;
        }
        File file;
        if(suffixPath != null){
            file = new File(prefixPath + separator + suffixPath);
        }else {
            file = new File(prefixPath + separator);
        }

        if (file.isFile()) {
            try {
                throw new Exception(file.getAbsolutePath() + "不是一个目录!");
            } catch (Exception e) {
                LOGGER.error(file.getAbsolutePath() + "不是一个目录!", e);
                throw new RuntimeException(e);
            }
        }

        if (!file.exists()) {
            file.mkdir();
        }
        String result = file.getAbsolutePath();
        if (result.endsWith(File.separator)) {
            return result;
        } else {
            return result + File.separator;
        }
    }

    /**
     * 拼接数据
     * @param centent
     * @param ipAddr
     * @return
     */
    private byte[] appendContentAndAssetIDAndDate(String centent,String ipAddr,Long timestamp){

        return new StringBuilder(ipAddr).append("\t").append(centent).append("\t").append(timestamp).append("\t\n").toString().getBytes();
    }

    private String appendPath(LocalDateTime dateTime){
        return new StringBuilder()
                .append(dateTime.getYear())
                .append("-")
                .append(coverage(String.valueOf(dateTime.getMonth().getValue())))
                .append("-")
                .append(coverage(String.valueOf(dateTime.getDayOfMonth())))
                .append("-")
                .append(coverage(String.valueOf(dateTime.getHour())))
                .append("-")
                .append(coverage(String.valueOf(dateTime.getMinute())))
                .append("-")
                .append(coverage(String.valueOf(dateTime.getSecond()))).toString();
    }

    /**
     * 补位 1 => 01
     * @param content
     * @return
     */
    private static String coverage(String content){

        return Integer.valueOf(content) >= 10 ? content : new StringBuilder().append("0").append(content).toString();
    }

    /**
     *
     * @return
     */
    private static byte[] timeToBytes(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date()).getBytes();
    }

    private void submitTask(String sqlPath){
        if(uploadTaskCacheManager.exists(sqlPath)){
            return;
        }else {
            uploadTaskCacheManager.createCacheBean(sqlPath,0L);
        }

    }


}
