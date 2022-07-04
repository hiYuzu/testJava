import java.util.regex.Pattern;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/5/9 14:24
 */
public class ParamInjectUtil {

    public static final String CC_SQL_REGEX_KEYWORD = "or|union|exists|sql|if|like|drop|create|procedure|trigger|set|update|insert|truncate|delete|select|chr|intersect|minus|all|join|function|exec|backup|ASCII|dbms_pipe|dbms_pipe.receive_message";
    public static final String CC_SQL_REGEX_SIGN = "'|;|!|<|>|(--)|(/\\*)|(\\*/)|(//)|(?i)((\\?sql_debug=1)|(system.ini)|script|dbms|v\\$|pipe)";
    public static final String CC_SQL_Regex = ".*(('|;|!|<|>|(--)|(/\\*)|(\\*/)|(//)|(?i)((\\?sql_debug=1)|(system.ini)|script|dbms|v\\$|pipe))|((^|\\W)+\\s*|.*\\s+)(?i)(or|union|exists|sql|if|like|drop|create|procedure|trigger|set|update|insert|truncate|delete|select|chr|intersect|minus|all|join|function|exec|backup|ASCII|dbms_pipe|dbms_pipe.receive_message)(\\s+.*|(\\s*(\\W|\\(|$)))).*";
    public static final String N_A = "N/A";
    public static final String CC_SCRIPT1 = ".*(?i)(<[\\s\\S]*s\\s*c\\s*r\\s*i\\s*p\\s*t\\b[\\s\\S]*>|<[\\s\\S]*l\\s*i\\s*n\\s*k\\b[\\s\\S]*>|<[\\s\\S]*m\\s*e\\s*a\\s*t\\b[\\s\\S]*>).*";
    public static final String CC_SCRIPT2 = ".*((&|&#)[a-z0-9]{1,4}([^a-z0-9]|\\b)).*";
    public static final String CC_SCRIPT3 = ".*(?i)(\\w+\\s*\\(.*\\)).*";
    public static final String startTag = "<sup>&#";
    public static final String startTagReplace = "";
    public static final String endTag = "</sup>";
    public static final String endTagReplace = "";
    /**
     *
     *
     * check string on regex
     * @param str : string
     * @param regex : regex in used
     * @return checked [T/F]
     *
     * */
    private static boolean check(String str, String regex){
        if(str == null || "".equals(str)){
            return true;
        }else{
            return !Pattern.compile(regex).matcher(str).matches();
        }

    }

    /**
     *
     * check fragment of SQL used default regex
     * @param sql : SQL fragment
     * @return checked [T/F]
     *
     * */
//    public static boolean checkParameter(String para){
//        if(para == null || "".equals(para)){
//            return true;
//        }else{
//            para=para.replaceAll(startTag, startTagReplace);
//            para=para.replaceAll(endTag, endTagReplace);
//        }
//        boolean ret=false;
//        //校验通过，则为true，如果三个都通过，则为ture
//        if(check(para.replaceAll("\n|\r|\t","").trim(),CC_SQL_Regex)&&check(para.replaceAll("\n|\r|\t","").trim(),CC_SCRIPT1) &&
//                check(para.replaceAll("\n|\r|\t","").trim(),CC_SCRIPT2) && check(para.replaceAll("\n|\r|\t","").trim(),CC_SCRIPT3))
//        {
//            ret=true;
//        }
//        return ret;
//    }

    public static boolean checkParameter(String para) {
        if (para != null && !"".equals(para)) {
            para = para.replaceAll("<sup>&#", "");
            para = para.replaceAll("</sup>", "");
            boolean ret = false;
            if (check(para.replaceAll("\n|\r|\t", "").trim(), ".*(('|;|!|<|>|(--)|(/\\*)|(\\*/)|(//)|(?i)((\\?sql_debug=1)|(system.ini)|script|dbms|v\\$|pipe))|((^|\\W)+\\s*|.*\\s+)(?i)(or|union|exists|sql|if|like|drop|create|procedure|trigger|set|update|insert|truncate|delete|select|chr|intersect|minus|all|join|function|exec|backup|ASCII|dbms_pipe|dbms_pipe.receive_message)(\\s+.*|(\\s*(\\W|\\(|$)))).*") && check(para.replaceAll("\n|\r|\t", "").trim(), ".*(?i)(<[\\s\\S]*s\\s*c\\s*r\\s*i\\s*p\\s*t\\b[\\s\\S]*>|<[\\s\\S]*l\\s*i\\s*n\\s*k\\b[\\s\\S]*>|<[\\s\\S]*m\\s*e\\s*a\\s*t\\b[\\s\\S]*>).*") && check(para.replaceAll("\n|\r|\t", "").trim(), ".*((&|&#)[a-z0-9]{1,4}([^a-z0-9]|\\b)).*") && check(para.replaceAll("\n|\r|\t", "").trim(), ".*(?i)(\\w+\\s*\\(.*\\)).*")) {
                ret = true;
            }

            return ret;
        } else {
            return true;
        }
    }
}
