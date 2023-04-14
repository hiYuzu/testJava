/**
 * <p>
 * 勇者斗恶龙
 * </p>
 * @since 2019/2/15 14:44
 * @author hiYuzu
 * @version V2.0
 */
public class HeroFightDragon {
    public static void main(String[] args) {
        java.util.Random operRandom = new java.util.Random();
        java.util.Scanner operInput = new java.util.Scanner(System.in);
        java.util.Scanner difficultyInput = new java.util.Scanner(System.in);
        int heroHp;
        int heroMp;
        int dragonHp = 20;
        int dragonMp = 0;
        // 0：蓄力 1：防御 2：攻击 3：重击
        int dragonDo = 0;
        int defense = 0;
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
        System.out.println(" 恶龙MP为0时只能勉强防御");
        System.out.println(" 恶龙MP达到4时可能会放出火焰无法躲避");
        System.out.println(" 准备说明完毕,那么让我们来挑战恶龙吧！");
        System.out.println("==================================");
//难度选择
        System.out.println("请选择难度");
        System.out.println("1.娱乐 2.挑战 3.噩梦");
        int difficulty = difficultyInput.nextInt();
        if (difficulty == 1) {
            heroHp = 100;
            heroMp = 100;
            System.out.println("您已选择娱乐难度,初始HP 100,MP 100");
        } else if (difficulty == 2) {
            heroHp = 50;
            heroMp = 50;
            System.out.println("您已选择挑战难度,初始HP 50,MP 50");
        } else if (difficulty == 3) {
            heroHp = 50;
            heroMp = 20;
            System.out.println("您已选择噩梦难度,初始HP 50,MP 20");
        } else {
            heroHp = 20;
            heroMp = 0;
            System.out.println("公平的战斗，初始HP、MP与恶龙相同");
        }
        System.out.println("==================================");
        while (true) {
//HP，MP的显示
            System.out.print("勇者: ");
            System.out.print(" HP ");
            for (int x = heroHp; x > 0; x--) {
                System.out.print("*");
            }
            System.out.print(" " + heroHp);
            System.out.println();
            System.out.print(" ");
            System.out.print(" MP ");
            for (int x = heroMp; x > 0; x--) {
                System.out.print("*");
            }
            System.out.print(" " + heroMp);
            System.out.println();
            System.out.print("恶龙: ");
            System.out.print(" HP ");
            for (int y = dragonHp; y > 0; y--) {
                System.out.print("*");
            }
            System.out.print(" " + dragonHp);
            System.out.println();
            System.out.print(" ");
            System.out.print(" MP ");
            for (int y = dragonMp; y > 0; y--) {
                System.out.print("*");
            }
            System.out.print(" " + dragonMp);
            System.out.println();
            System.out.println("==================================");
//胜利判定
            if (heroHp < 1) {
                System.out.println();
                System.out.println("勇者HP为0! 不..不可能..我怎么会..勇者倒下了。再接再厉吧！~");
                System.out.println();
                break;
            }
            if (dragonHp < 1) {
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
            int heroDo = operInput.nextInt();
//敌人输入判定
            // 0：蓄力 1：防御 2：攻击 3：重击
//娱乐难度
            if (difficulty == 1) {
                dragonDo = operRandom.nextInt(4);
            }
//挑战难度
            if (difficulty == 2) {
                if (heroMp == 0 && dragonMp == 0) {
                    dragonDo = 0;
                } else if (dragonMp == 0) {
                    dragonDo = operRandom.nextInt(2);
                } else if (heroMp > 2 && dragonMp > 2) {
                    dragonDo = 2;
                } else if (dragonMp < 4) {
                    dragonDo = operRandom.nextInt(3);
                } else {
                    dragonDo = 3;
                }
            }
//噩梦难度
            if (difficulty == 3) {
                if (heroDo == 1) {
                    dragonDo = 1;
                }
                if (heroDo == 2 && dragonMp > 0) {
                    dragonDo = 2;
                }
                if (heroDo == 2 && dragonMp == 0) {
                    dragonDo = 0;
                }
                if (heroDo == 3 && dragonMp < 4) {
                    dragonDo = 0;
                }
                if (heroDo == 3 && dragonMp >= 4) {
                    dragonDo = 3;
                }
            }
//战斗分析
//防御
            if (heroDo == 3) {
                defense = 1;
                System.out.println("你灵巧的躲避攻击！");
            }
            if (dragonDo == 1 && dragonMp > 0) {
                defense = 1;
                dragonMp--;
                System.out.println("恶龙进行防御！");
            } else if (dragonDo == 1) {
                defense = 2;
                System.out.println("恶龙尝试防御！");
            }
//角色判定
            if (heroDo == 1 && heroMp == 0) {
                System.out.println("MP不足！");
            }
            if (heroDo == 1 && heroMp > 0) {
                if (defense == 0) {
                    dragonHp--;
                    heroMp--;
                    System.out.println("你发动攻击！");
                }
                if (defense == 1) {
                    heroMp--;
                    System.out.println("你的攻击被格挡！");
                }
                if (defense == 2) {
                    if (operRandom.nextInt(2) > 0) {
                        dragonHp--;
                        heroMp--;
                        System.out.println("攻击有效！");
                    } else {
                        heroMp--;
                        System.out.println("你的攻击被格挡！");
                    }
                }
            }
            if (heroDo == 2) {
                heroMp++;
                System.out.println("你进行蓄力！");
            }
            if (heroDo == 4 && heroMp < 3) {
                System.out.println("MP不足！");
            }
            if (heroDo == 4 && heroMp > 2) {
                dragonHp -= 2;
                heroMp -= 3;
                System.out.println("你发动重击！");
            }
            if (heroDo > 4) {
                System.out.println("你不知所措...");
            }
//敌人判定
            if (dragonDo == 2 && dragonMp == 0) {
                System.out.println("恶龙在发呆！");
            }
            if (dragonDo == 2 && dragonMp > 0) {
                if (defense == 0) {
                    heroHp -= 2;
                    dragonMp--;
                    System.out.println("恶龙发动攻击！");
                }
                if (defense == 1) {
                    dragonMp--;
                    System.out.println("恶龙的攻击被躲开了！");
                }
            }
            if (dragonDo == 0) {
                dragonMp++;
                System.out.println("恶龙进行蓄力！");
            }
            if (dragonDo == 3 && dragonMp < 4) {
                System.out.println("恶龙在发呆！");
            }
            if (dragonDo == 3 && dragonMp > 3) {
                heroHp -= 4;
                dragonMp -= 4;
                System.out.println("恶龙发动火焰吐吸！躲避不能！");
            }
//结束
            defense = 0;
            dragonDo = 0;
            System.out.println("____________________");
            System.out.println();
            System.out.println("==================================");
        }
    }
}
