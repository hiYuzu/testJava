/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description
 * @date 2019/2/15 14:44
 */
public class YZDEL {
    public static void main(String[] args) {
        java.util.Random Shiji = new java.util.Random();
        java.util.Scanner Shuru = new java.util.Scanner(System.in);
        java.util.Scanner Nandu = new java.util.Scanner(System.in);
        int YingxHP = 20;
        int YingxMP = 0;
        int MogHP = 20;
        int MogMP = 0;
        int MogDo = 0;
        int fangyu = 0;
        System.out.println("┏━━━━╮┏━━┣┣┓　╮　┓　　┏━┳━┳┓　　┓━┓　");
        System.out.println("┏┣━━━┓┏━━┣┣┓　　　┃　　┏　┃　┃┓　　┃　╯　");
        System.out.println("┣┣━━━┫╭━━┻╯　　╮　┃　　╰┓┃　┃╯┏━┣━━┓");
        System.out.println("┣┣━━━┫┏━━━━┓╭━━┣━┛┗━┻━┻┛　　┃┃　　");
        System.out.println("┏┣━━━┓┣━━━━┫　　　┃　　┃┃┗╮╮┃　　┃┣━╯");
        System.out.println("　┛　　┗╯┗━━━━╯　　┗╯　　╰╰━━┛┛┗━╯╰━┛");
        System.out.println();
        System.out.println(" 初版作者:天狐空幻");
        System.out.println(" 改进:hiYuzu");
        System.out.println();
        System.out.println("说明:勇者的操作方式为以下所示:");
        System.out.println(" 使用攻击需消耗1MP 伤害1HP");
        System.out.println(" 使用蓄力可增加1MP 伤害0HP");
        System.out.println(" 使用躲闪需消耗0MP 伤害0HP 躲避攻击");
        System.out.println(" 使用重击需消耗3MP 伤害2HP 防御无效");
        System.out.println(" 恶龙攻击力高， 注意防御是取胜的关键");
        System.out.println(" 恶龙MP达到4时可能会放出火焰无法躲避");
        System.out.println(" 准备说明完毕,那么让我们来挑战恶龙吧！");
        System.out.println("==================================");
//难度选择
        System.out.println("请选择难度");
        System.out.println("1.娱乐 2.挑战 3.噩梦");
        int ND = Nandu.nextInt();
        if (ND == 1) {
            System.out.println("您已选择娱乐难度,初始HP 40,MP 10");
        } else if (ND == 2) {
            System.out.println("您已选择挑战难度,初始HP 30,MP 5");
        } else if (ND == 3) {
            System.out.println("您已选择噩梦难度,初始HP 20,MP 0");
        }
        System.out.println("==================================");
        while (true) {
//HP，MP的显示
            System.out.print("勇者: ");
            System.out.print(" HP ");
            for (int x = YingxHP; x > 0; x--) System.out.print("*");
            System.out.print(" " + YingxHP);
            System.out.println();
            System.out.print(" ");
            System.out.print(" MP ");
            for (int x = YingxMP; x > 0; x--) System.out.print("*");
            System.out.print(" " + YingxMP);
            System.out.println();
            System.out.print("恶龙: ");
            System.out.print(" HP ");
            for (int y = MogHP; y > 0; y--) System.out.print("*");
            System.out.print(" " + MogHP);
            System.out.println();
            System.out.print(" ");
            System.out.print(" MP ");
            for (int y = MogMP; y > 0; y--) System.out.print("*");
            System.out.print(" " + MogMP);
            System.out.println();
            System.out.println("==================================");
//胜利判定
            if (YingxHP < 1) {
                System.out.println();
                System.out.println("勇者HP为0! 不..不可能..我怎么会..勇者倒下了。再接再厉吧！~");
                System.out.println();
                break;
            }
            if (MogHP < 1) {
                System.out.println();
                System.out.println("恶龙HP为0! 恶龙绝望的哀鸣中倒了下去。勇者胜利了。恭喜你挑战成功！！");
                System.out.println();
                break;
            }
//角色输入判定
            System.out.println("你要做什么：");
            System.out.println("1.攻击 2.蓄力");
            System.out.println("3.躲闪 4.重击");
            System.out.println("____________________");
            int Do = Shuru.nextInt();
//敌人输入判定
//娱乐难度
            if (ND == 1) {
                for (int i = 1; i < 3; i++) MogDo = Shiji.nextInt(4);
            }
//挑战难度
            if (ND == 2) {
                if (YingxMP == 0 && MogMP == 0) {
                    MogDo = 0;
                } else if (MogMP == 0) {
                    for (int i = 1; i < 3; i++) MogDo = Shiji.nextInt(2);
                } else if (YingxMP > 2 && MogMP > 2) MogDo = 2;
                else if (MogMP < 4) {
                    for (int i = 1; i < 4; i++) MogDo = Shiji.nextInt(3);
                } else MogDo = 3;
            }
//噩梦难度
            if (ND == 3) {
                if (Do == 1) MogDo = 1;
                if (Do == 2 && MogMP > 0) MogDo = 2;
                if (Do == 2 && MogMP == 0) MogDo = 0;
                if (Do == 3 && MogMP < 4) MogDo = 0;
                //变态判定。。
                if (Do == 3 && MogMP >= 4) MogDo = 3;
            }
//战斗分析
//防御
            if (Do == 3) {
                fangyu = 1;
                System.out.println("你灵巧的躲避攻击！");
            }
            if (MogDo == 1) {
                fangyu = 1;
                System.out.println("恶龙进行防御！");
            }
//角色判定
            if (Do == 1 && YingxMP == 0) {
                System.out.println("MP不足！");
            }
            if (Do == 1 && YingxMP > 0) {
                if (fangyu == 0) {
                    MogHP = MogHP - 1;
                    YingxMP = YingxMP - 1;
                    System.out.println("你发动攻击！");
                }
                if (fangyu == 1) {
                    YingxMP = YingxMP - 1;
                    System.out.println("你的攻击被格挡！");
                }
            }
            if (Do == 2) {
                YingxMP = YingxMP + 1;
                System.out.println("你进行蓄力！");
            }
            if (Do == 4 && YingxMP < 3) {
                System.out.println("MP不足！");
            }
            if (Do == 4 && YingxMP > 2) {
                MogHP = MogHP - 2;
                YingxMP = YingxMP - 3;
                System.out.println("你发动重击！");
            }
            if (Do > 4) System.out.println("你不知所措...");
//敌人判定
            if (MogDo == 2 && MogMP == 0) {
                System.out.println("恶龙在发呆！");
            }
            if (MogDo == 2 && MogMP > 0) {
                if (fangyu == 0) {
                    YingxHP = YingxHP - 2;
                    MogMP = MogMP - 1;
                    System.out.println("恶龙发动攻击！");
                }
                if (fangyu == 1) {
                    MogMP = MogMP - 1;
                    System.out.println("恶龙的攻击被躲开了！");
                }
            }
            if (MogDo == 0) {
                MogMP = MogMP + 1;
                System.out.println("恶龙进行蓄力！");
            }
            if (MogDo == 3 && MogMP < 4) {
                System.out.println("恶龙在发呆！");
            }
            if (MogDo == 3 && MogMP > 3) {
                YingxHP = YingxHP - 4;
                MogMP = MogMP - 4;
                System.out.println("恶龙发动火焰吐吸！躲避不能！");
            }
//结束
            fangyu = 0;
            MogDo = 0;
            System.out.println("____________________");
            System.out.println();
            System.out.println("==================================");
        }
    }
}
