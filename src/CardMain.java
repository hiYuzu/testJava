/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/11/4 16:49
 */
public class CardMain {
    public static void main(String[] args) {
        String cards = "3703022021112410491132502999315\n" +
                "3703022021112314010386094015340\n" +
                "3703022021112323462666814745389\n" +
                "3703022021112215472913033637071\n" +
                "3703022021112215494991261791036\n" +
                "3703022021112215534502341378648\n" +
                "3703022021112517431383300241791\n" +
                "3703022021112215151770090532381\n" +
                "3703022021112215223758591500846\n" +
                "3703022021112215052749640668063";

        String result = cards.replaceAll("\n","','");
        StringBuilder sb = new StringBuilder(result);
        sb.insert(0, "'");
        sb.append("'");
        System.out.println(sb);
    }
}
