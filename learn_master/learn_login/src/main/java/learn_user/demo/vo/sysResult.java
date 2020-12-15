package learn_user.demo.vo;

public class sysResult {
    private Integer status;
    private String msg;
    private  Object data;
     public sysResult(Integer status,String msg,Object data)
     {
         this.status=status;
         this.msg=msg;
         this.data=data;
     }
     public sysResult(){}
     public  static sysResult build(Integer status,String msg,Object data)
     {
         return new sysResult(status ,msg , data );

     }
     public static sysResult ok()
     {
         return new sysResult(200,"ok",null);
     }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
