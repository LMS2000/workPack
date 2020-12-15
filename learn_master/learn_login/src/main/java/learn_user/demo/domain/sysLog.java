package learn_user.demo.domain;

import java.util.Date;

public class sysLog {
    private String url;
    private String logType;
    private String logMethod;
    private String params;
    private String sessionToken;
    private Long clientIp;
    private Long logTime;
    private String location;
    private Date createTime;

    @Override
    public String toString() {
        return "sysLog{" +
                "url='" + url + '\'' +
                ", logType='" + logType + '\'' +
                ", logMethod='" + logMethod + '\'' +
                ", params='" + params + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", clientIp=" + clientIp +
                ", logTime=" + logTime +
                ", location='" + location + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Long getClientIp() {
        return clientIp;
    }

    public void setClientIp(Long clientIp) {
        this.clientIp = clientIp;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
