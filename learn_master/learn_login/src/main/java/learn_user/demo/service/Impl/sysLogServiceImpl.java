package learn_user.demo.service.Impl;

import learn_user.demo.domain.sysLog;
import learn_user.demo.mapper.sysLogMapper;
import learn_user.demo.service.sysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class sysLogServiceImpl implements sysLogService
{
    @Autowired
    private sysLogMapper sysLogMapper ;
    @Override
    public void addSysLog(sysLog sysLog) {
     sysLogMapper.addSysLog( sysLog );
    }

    @Override
    public List<sysLog> findBySysLogList() {
        return sysLogMapper.findSysLogList();
    }
}
