package entity;

import java.util.Date;

public class Log {

    private String id;
    private String appName;
    private Type status;
    private String fileName;
    private String href;
    private String date;

    public Log(String id, String appName, String href){
        this.id = id;
        this.appName = appName;
        this.href = href;
    }

    public Log(String id, String appName, Type type, String fileName, String date, String href) {
        this.id = id;
        this.appName = appName;
        this.status = type;
        this.date = date;
        this.href = href;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Type getStatus() {
        return status;
    }

    public void setStatus(Type status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", appName='" + appName + '\'' +
                ", status=" + status +
                ", fileName='" + fileName + '\'' +
                ", href='" + href + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (!id.equals(log.id)) return false;
        if (!appName.equals(log.appName)) return false;
        return href.equals(log.href);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + appName.hashCode();
        result = 31 * result + href.hashCode();
        return result;
    }
}
