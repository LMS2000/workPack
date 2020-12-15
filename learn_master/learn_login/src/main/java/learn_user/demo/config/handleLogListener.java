package learn_user.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import learn_user.demo.domain.sysLog;
import learn_user.demo.service.Impl.sysLogServiceImpl;
import learn_user.demo.service.sysLogService;
import learn_user.demo.util.tokenWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;
import java.util.HashMap;
import java.util.Map;

public class handleLogListener implements HandlerInterceptor {
    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";
    private static sysLog sysLog =new sysLog();
    private  sysLogService sysLogService ;
    public  handleLogListener(sysLogService sysLogService )
    {
        this.sysLogService=sysLogService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> stringObjectMap= new HashMap<>();
        //获取请求参数信息
        String param = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);
        String token =request.getHeader("token");
        if(token!=null)
        {
            stringObjectMap = tokenWebUtil.parserWebToken(token);
        }
        //设置请求参数
        sysLog.setParams(param);

        Integer userNo =(Integer)stringObjectMap.get("userNo");
        // 设置IP地址
        if(userNo!=null)
        {
            sysLog.setClientIp(userNo.longValue());

        }

        sysLog.setLocation(request.getLocalAddr());

        //设置请求方法,GET,POST...
        sysLog.setLogMethod(request.getMethod());

        //设置请求路径
        sysLog.setUrl(request.getRequestURI());

        //设置请求开始时间
        request.setAttribute(LOGGER_SEND_TIME,System.currentTimeMillis());

        //设置请求实体到request内，方便afterCompletion方法调用
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //获取请求错误码，根据需求存入数据库，这里不保存
        int status = response.getStatus();

        //当前时间
        long currentTime = System.currentTimeMillis();

        //请求开始时间
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());

        //获取本次请求日志实体


        //设置请求时间差
        sysLog.setLogTime(Integer.valueOf((currentTime - time)+"").longValue());

        //执行将日志写入数据库，可以根据实际需求进行保存

        sysLogService.addSysLog(sysLog );

    }
}
