package learn_user.demo.service;

import learn_user.demo.domain.sysLog;

import java.util.List;

public interface sysLogService {

    void addSysLog(sysLog sysLog );
    List<sysLog> findBySysLogList();
}
