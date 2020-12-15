package learn_user.demo.util;

public class typeHelper {
    private  static  final String TYPE_ZERO_OF ="选修课";
    private  static  final String TYPE_ONE_OF ="公共限定课";
    private  static  final String TYPE_TWE_OF="必修课";
    private  static  final String TYPE_THREE_OF="校际课";

    public static Integer getType(String type){
        if(TYPE_ZERO_OF.equals(type.trim()))
        {
            return 0;
        }
        else if(TYPE_ONE_OF.equals(type.trim()))
        {
            return 1;
        }
        else if(TYPE_TWE_OF.equals(type.trim()))
        {
            return 2;
        }else if(TYPE_THREE_OF.equals(type.trim()))
        {
            return 3;
        }
        return null;

    }


}
