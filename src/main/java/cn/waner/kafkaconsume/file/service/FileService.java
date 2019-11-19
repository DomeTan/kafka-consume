package cn.waner.kafkaconsume.file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private static final String path = "/var/kafka1/";

    public void save(String ipAddress, String content) {
        LocalDateTime datetime = LocalDateTime.now();
        String currentDate = datetime.getYear() + "-"
                + datetime.getMonth().getValue() + "-"
                + datetime.getDayOfMonth();
        String currentHour = String.valueOf(datetime.getHour());
        String minuteHour = String.valueOf(datetime.getMinute());

        String firstDirPath = getDirectoryPath(path, ipAddress);
        String secondDirPath = getDirectoryPath(firstDirPath, currentDate);

        File file = new File(secondDirPath + currentHour + ".txt");
        if (!Files.exists(Paths.get(secondDirPath))){
            try {
                Files.createDirectories(Paths.get(secondDirPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(content);
        } catch (IOException e) {
            LOGGER.error("写入日志到" + secondDirPath + currentHour + ".txt失败！", e);
//            LOGGER.error("写入日志到" + secondDirPath + currentHour+"-"+minuteHour + ".txt失败！", e);
            throw new RuntimeException(e);
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

        File file = new File(prefixPath + separator + suffixPath);

        if (file.isFile()) {
            LOGGER.info("不是一个目录",file.getAbsolutePath());
            return "";
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
