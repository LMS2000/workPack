package learn_user.demo.mapper;

import learn_user.demo.domain.sysLog;

import java.util.List;

public interface sysLogMapper {
    void addSysLog(sysLog sysLog );
    List<sysLog> findSysLogList();
}
