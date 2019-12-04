package cn.waner.kafkaconsume.handler.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;


/**
 * @author TanChong
 * create date 2019\11\9 0009
 */
@Component
public class HdfsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsService.class);
    private static String hdfs;
    private static String hdfsPath;

    private String logSrcStoragePath;


    @Autowired
    public HdfsService(@Value("${first_floor.hdfs}") String hdfs,
                       @Value("${first_floor.hdfs.path}") String hdfsPath,
                       @Value("${first_floor.log.src.storage.path}") String logSrcStoragePath) {
        this.hdfs = hdfs;
        this.logSrcStoragePath = logSrcStoragePath;
        this.hdfsPath = hdfsPath;
    }

    /**
     * 获取hadoop配置信息
     * @return
     */
    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfs);
        return configuration;
    }

    /**
     * 获取文件系统
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static FileSystem getFileSystem() throws URISyntaxException, IOException {

        return FileSystem.get(new URI(hdfs), getConfiguration());
    }

    public void upload(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime now = localDateTime.minusSeconds(1);

        String currentDate = coverage(String.valueOf(now.getYear())) + "-"
                + coverage(String.valueOf(now.getMonth().getValue())) + "-"
                + coverage(String.valueOf(now.getDayOfMonth()));
        String fileNameForLinux = appendPath(now);
        // 返回资产主目录 \downFile\original\14.13.10.134\
        String firstDirPath = getDirectoryPath(logSrcStoragePath,"");
        // 返回资产以日期区分的目录 \downFile\original\14.13.10.134\2019-8-21\
        String secondDirPath = getDirectoryPath(firstDirPath, currentDate);
        String logPath = new StringBuilder(secondDirPath).append(fileNameForLinux).append(".log").toString();
//        hdfsPath = new StringBuilder(hdfsPath).append(secondDirPath).toString();
        File file = new File(logPath);
        if(file.exists()){
            try {
                boolean result = uploadFile(logPath, hdfsPath);
                if(result){
//                    LOGGER.info("上传成功");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
          LOGGER.warn("No data, waiting...");
        }

    }
//    @Transactional
    public void upload(String logPath){
        String hdfsPath = getHdfsPath(logPath);
        if(hdfsPath == null) return;
        exists(hdfsPath);
        File file = new File(logPath);
        if(file.exists()){
            try {
                boolean result = uploadFile(logPath, hdfsPath);
                if(result){
//                    LOGGER.info("上传成功");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
            LOGGER.warn("No data, waiting...");
        }

    }

    /**
     *  从本地文件路径中计算出hdfs文件存储路径
     * @param logSrcStoragePath
     * @return
     */
    private static String getHdfsPath(String logSrcStoragePath){
        String[] split = logSrcStoragePath.split("\\\\");
        if(split.length != 6) return null;
        return new StringBuilder("/")
                .append(split[1]).append("/")
                .append(split[2]).append("/")
                .append(split[3]).append("/")
                .append(split[4]).append("/")
                .toString();
    }

    /**
     * 判断文件上传路径是否存在,false 则创建
     * @param hdfsPath
     * @return
     */
    private static void exists(String hdfsPath){

        Path path = new Path(hdfsPath);
        FileSystem fileSystem = null;
        boolean exists = false;
        try {
            fileSystem = getFileSystem();
            exists = fileSystem.exists(path);

        } catch (URISyntaxException e) {
            LOGGER.error("fileSystem 对象获取失败...{}",e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("fileSystem 对象获取失败,hdfs路径可能不存咋...{}",e);
            e.printStackTrace();
        }
        if(!exists){
            try {
                fileSystem.mkdirs(path);
            } catch (IOException e) {
                LOGGER.error("文件上传路径创建失败...{}",e);
                e.printStackTrace();

            }
        }
    }
    /**
     *  上传文件到hdfs
     * @param path 本地路径
     * @param uploadPath 上传路径
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private static boolean uploadFile(String path,String uploadPath) throws IOException, URISyntaxException {
        if(StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)){
            return false;
        }
        FileSystem fileSystem = getFileSystem();
        Path clientPath  = new Path(path);
        Path serverPath = new Path(uploadPath);

        fileSystem.copyFromLocalFile(true,clientPath,serverPath);
//        fileSystem.close();
        return true;
    }

    /**
     *  自己看
     * @param dateTime
     * @return
     */
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
     * 补位 1 -> 01
     * @param content
     * @return
     */
    private static String coverage(String content){

        return Integer.valueOf(content) >= 10 ? content : new StringBuilder().append("0").append(content).toString();
    }

    /**
     *  创建文件夹
     * @param prefixPath
     * @param suffixPath
     * @return
     */
    private String getDirectoryPath(String prefixPath, String suffixPath) {
        String separator = "";

        if (!prefixPath.endsWith(File.separator)) {
            separator = File.separator;
        }
        File file = new File(prefixPath + separator + suffixPath);

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

}
