package service;

import entity.Log;
import entity.Type;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class LogManager {
    private static List<Log> excludeLogList = Collections.synchronizedList(new ArrayList<Log>());

    public static void addOperation(String appName, String fileLink, String folderLink) throws IOException {
        Properties prop = getLogProperties();
        checkFolderAndFileLink(folderLink, fileLink);
        checkFileLink(fileLink);
        checkFolderLink(folderLink);
        if (!folderLink.isEmpty()) {
            saveLink(prop, appName, folderLink);
        } else {
            saveLink(prop, appName, fileLink);
        }
    }

    private static void saveLink(Properties prop, String appName, String link) {
        int nextKey = getNextKeyValue(prop);
        try {
            URL resourceUrl = LogManager.class.getClassLoader().getResource("logs.properties");
            File file = new File(resourceUrl.toURI());
            PropertiesConfiguration config = new PropertiesConfiguration(file);
            config.setProperty(String.valueOf(nextKey), appName + ";" + link);
            config.save();
        } catch (ConfigurationException io) {
            io.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static int getNextKeyValue(Properties prop) {
        Object[] keys = prop.keySet().toArray();
        int nextKey = 0;
        if (keys.length > 0) {
            nextKey = Integer.parseInt((String) keys[0]) + 1;
        }
        return nextKey;
    }

    private static void checkFolderAndFileLink(String folderLink, String fileLink) {
        if ((folderLink.isEmpty() && fileLink.isEmpty()) || (!folderLink.isEmpty() && !fileLink.isEmpty())) {
            throw new UnsupportedOperationException("Please enter log file or folder location");
        }
    }

    private static void checkFolderLink(String folderLink) {
        if (!folderLink.isEmpty()) {
            if (!new File(folderLink).isDirectory()) {
                throw new UnsupportedOperationException("Folder link: " + folderLink + " is not a directory. Please check folder location");
            }
            File folder = new File(folderLink);
            File[] files = folder.listFiles();
            if (files.length == 0) {
                throw new UnsupportedOperationException("Folder " + folderLink + " doesn't have any files. Please check folder");
            }
        }
    }

    private static void checkFileLink(String fileLink) {
        if (!fileLink.isEmpty() && !new File(fileLink).isFile()) {
            throw new UnsupportedOperationException("Log link: " + fileLink + " is not a file. Please check log location");
        }
    }

    public static void deleteOperation(String id, String appName, String link) throws IOException {
        excludeLogList.add(new Log(id, appName, link));
    }

    public static String getLogFileLocation(String id, String name) {
        Properties prop = getLogProperties();
        String fileLocation = prop.getProperty(id).split(";")[1];
        File file = new File(fileLocation);
        if (file.isDirectory()) {
            return getFileLocationByName(file, name);
        } else {
            return file.getAbsolutePath();
        }
    }

    public static List<Log> getLogsFromProperties() {
        List<Log> logList = new ArrayList<>();
        Properties prop = getLogProperties();
        try {
            Enumeration e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String id = (String) e.nextElement();
                String props[] = prop.getProperty(id).split(";");
                String appName = props[0];
                String link = props[1];

                File file = new File(link);
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isFile() && !isExcluded(id, appName, files[i].getAbsolutePath())) {
                            Log log = new Log(id, appName, getLogType(files[i].getAbsolutePath()), files[i].getName(), getDate(files[i]), files[i].getAbsolutePath());
                            logList.add(log);
                        }
                    }
                } else if (!isExcluded(id, appName, link)) {
                    Log log = new Log(id, appName, getLogType(link), file.getName(), getDate(file), link);
                    logList.add(log);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return logList;
    }

    private static String getDate(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dt = new Date(file.lastModified());
        return sdf.format(dt);
    }

    private static boolean isExcluded(String id, String appName, String link) throws IOException {
        return excludeLogList.contains(new Log(id, appName, link));
    }

    public static Type getLogType(String link) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(link));
        while (sc.hasNextLine()) {
            if (sc.nextLine().contains("Exception: ")) {
                return Type.DANGER;
            }
        }
        return Type.SUCCESS;
    }

    public static String getFileLocationByName(File file, String name) {
        String fileLocation = null;
        List files = Arrays.asList(file.listFiles());
        Iterator<File> iterator = files.iterator();
        while (iterator.hasNext()) {
            File item = iterator.next();
            if (item.getName().equalsIgnoreCase(name)) {
                fileLocation = item.getAbsolutePath();
                break;
            }
        }
        return fileLocation;
    }

    public static Properties getLogProperties() {
        Properties prop = new Properties();
        URL resourceUrl = LogManager.class.getClassLoader().getResource("logs.properties");
        try {
            File file = new File(resourceUrl.toURI());
            prop.load(new FileReader(file));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
