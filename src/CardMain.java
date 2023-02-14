/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/11/4 16:49
 */
public class CardMain {
    public static void main(String[] args) {
        String cards = "\"3101052022080103302308845906705\t\"\n" +
                "\"3101052022080103304505919365937\t\"\n" +
                "\"3101052022080103302962477990193\t\"\n" +
                "\"3101052022080103305345895203660\t\"\n" +
                "\"3101052022080103305016174580664\t\"\n" +
                "\"3101052022080103313028434202254\t\"\n" +
                "\"3101052022080103312166576679394\t\"\n" +
                "\"3101052022080103311079341562189\t\"\n" +
                "\"3101052022080103313312912139611";

        String result = cards.replaceAll("\t\"\n","','").replaceAll("\"", "");
        StringBuilder sb = new StringBuilder(result);
        sb.insert(0, "('");
        sb.append("')");
        System.out.println(sb);
    }
}
