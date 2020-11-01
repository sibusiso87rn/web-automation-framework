package starter.testing.core.util.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sibusiso Radebe
 */

public class FilesUtil {

    private static final Logger logger = LogManager.getLogger(FilesUtil.class);

    //Gets a file in resources path
    public static File getFile(String filePath) throws Exception{
        File file          = ResourceUtils.getFile(filePath);
        if(!file.exists()){
            logger.error(filePath + " Does not exist " );
            throw new Exception(filePath + " Does not exist ");
        }
        return file;
    }

    public static String  getFileAbsolutePath(String filePath) throws Exception{
        return getFile(filePath).getAbsolutePath();
    }

    public static String  getFileParent(String filePath) throws Exception{
        return getFile(filePath).getParent();
    }

    public static String getStringContent(String filePath){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(getFile(filePath))))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine);
            }
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return contentBuilder.toString();
    }

    public static String getStringContent(File file){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null){
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return contentBuilder.toString();
    }

    public static String getStringContent(String filePath,boolean flatten){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(getFile(filePath))))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                if(flatten){
                    contentBuilder.append(sCurrentLine);
                }else{
                    contentBuilder.append(sCurrentLine).append("\n");
                }
            }
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return contentBuilder.toString().replace("\t","").replace(" ","");
    }

    public static ArrayList<String> getFileNamesInDir(String filePath,String fileTypes) throws Exception {

        File file  = getFile(filePath);

        ArrayList<String> list = new ArrayList<String>();

        String[] filesInDir = file.list();

        for(String fileName: filesInDir){
            if(fileName.contains(fileTypes)){
                list.add(fileName);
            }
        }

        return list;
    }

    public static void createFile(String filename) throws IOException {
       if (new File(filename).createNewFile()){
           logger.info("File is created!");
        } else {
           logger.info("File already exists.");
        }
    }
}
