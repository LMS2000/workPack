package learn_user.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

public class tokenWebUtil {
    private  static Logger log = LoggerFactory.getLogger(tokenWebUtil.class);

     public  static   Key  getKeyInstance(){
      SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
      byte[] userInstanceBytes= DatatypeConverter.parseBase64Binary("binkg1");
      Key signingKey  =new SecretKeySpec(userInstanceBytes ,signatureAlgorithm.getJcaName());
      return  signingKey;
     }
      public  static  String createWebToken(Map<String ,Object> claims){
         return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();
      }
      public  static  Map<String ,Object> parserWebToken(String token)
      {
          try{
             Map<String ,Object> jwtClaims =Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token).getBody();
             return jwtClaims;
          }catch (Exception e)
          {
              e.getStackTrace();
          }
          return null;
      }


}
