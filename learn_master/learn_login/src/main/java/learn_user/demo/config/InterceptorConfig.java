package learn_user.demo.config;

import learn_user.demo.service.sysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
 @Autowired
    private sysLogService  sysLogService ;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new handleConfig()).excludePathPatterns("/user/token");
        registry.addInterceptor(new handleLogListener(sysLogService)).addPathPatterns("/**");
    }

}