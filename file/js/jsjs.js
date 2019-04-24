/**
 * @author hiYuzu
 * @description
 * @version V1.0
 * @date 2019/4/22 9:43
 */

function clickFun() {
    window.opener.document.getElementById("pp").innerText = "ooo";
    $("#goBack")[0].click();

}