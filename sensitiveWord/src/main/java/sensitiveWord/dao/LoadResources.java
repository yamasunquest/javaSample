package sensitiveWord.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 加载敏感词资源
 * 按照项目实际情况，可以通过配置文件、磁盘、数据库（未实现），来加载敏感词
 * <p>
 * 之前有做过一个敏感词维护的程序，因此建议使用来自磁盘的方式获取敏感词资源
 * 这样之前做的敏感词维护的功能可以继续使用
 */
@Component
public class LoadResources {
    @Value("${sensitiveWord.path}")
    private String filePath;

    /**
     * 加载类路径下资源
     *
     * @return set
     * @throws Exception
     */
    public Set<String> fromClassPath() throws Exception {
        File file;
        try {
            file = ResourceUtils.getFile("classpath:filter.txt");
        } catch (FileNotFoundException e) {
            System.out.println("resource from class path not file found");
            throw e;
        }

        return readSensitiveWordFile(file);
    }

    /**
     * 加载磁盘路径下的资源
     *
     * @return set
     * @throws Exception
     */
    public Set<String> fromDiskPath()throws Exception {
        File file;
        file = new File(filePath);

        return readSensitiveWordFile(file);
    }

    /**
     * 加载数据库中的资源
     * 未实现
     *
     * @return set
     * @throws Exception
     */
    public Set<String> fromDB()throws Exception {
        return null;
    }

    /**
     * 读取敏感词文件的内容添加到set集合
     *
     * @return Set
     * @throws Exception Exception
     */
    private Set<String> readSensitiveWordFile(File file) throws Exception {

        Set<String> set = new HashSet<>();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
        try {
            BufferedReader bufferedReader = new BufferedReader(read);
            String sensitiveWord;
            while ((sensitiveWord = bufferedReader.readLine()) != null) {
                set.add(sensitiveWord);
            }
            return set;
        } finally {
            read.close();
        }
    }

}
