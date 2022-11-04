/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/11/4 16:49
 */
public class CardMain {
    public static void main(String[] args) {
        String cards = "3307022021110515364793841883384\n" +
                "3307022021102917263771820935379\n" +
                "3307022021110515203633537323456\n" +
                "3307022021041312482152986611022\n" +
                "3307022021110108551270067704224\n" +
                "3305222021110808241014527254475";

        String result = cards.replaceAll("\n","','");
        StringBuilder sb = new StringBuilder(result);
        sb.insert(0, "('");
        sb.append("')");
        System.out.println(sb);
    }
}
