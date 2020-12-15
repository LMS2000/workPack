package learn_user.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class baseUrlConfig {

    public CorsConfiguration buildConfig(){
     CorsConfiguration corsConfiguration  =new CorsConfiguration();
     corsConfiguration.addAllowedHeader("*");
     corsConfiguration.addAllowedOrigin("*");
     corsConfiguration.addAllowedMethod("*");
     return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter (){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource() ;
        source.registerCorsConfiguration("/**",buildConfig());
        return  new CorsFilter(source);
    }

}
