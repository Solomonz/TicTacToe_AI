<script src="js/jquery-3.1.1.js"></script>
<#assign content>
<center id="table-container">
    <table class="board">
        <tr class="top">
            <td class="left"><button class="spot" onclick="clicked([0, 0])" id="00"></button></td>
            <td class="center"><button class="spot" onclick="clicked([0, 1])" id="01"></button></td>
            <td class="right"><button class="spot" onclick="clicked([0, 2])" id="02"></button></td>
        </tr>
        <tr class="middle">
            <td class="left"><button class="spot" onclick="clicked([1, 0])" id="10"></button></td>
            <td class="center"><button class="spot" onclick="clicked([1, 1])" id="11"></button></td>
            <td class="right"><button class="spot" onclick="clicked([1, 2])" id="12"></button></td>
        </tr>
        <tr class="bottom">
            <td class="left"><button class="spot" onclick="clicked([2, 0])" id="20"></button></td>
            <td class="center"><button class="spot" onclick="clicked([2, 1])" id="21"></button></td>
            <td class="right"><button class="spot" onclick="clicked([2, 2])" id="22"></button></td>
        </tr>
    </table>
</center>
<button id="reset" onclick="reset()">Start a new game</button>
</#assign>
<script src="js/tictactoe.js"></script>
<#include "main.ftl">
