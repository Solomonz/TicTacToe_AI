<script src="js/jquery-3.1.1.js"></script>
<#assign content>
<center>
    <table>
        <tr class="top">
            <td class="left"><button class="spot" id="00"></button></td>
            <td class="center"><button class="spot" id="01"></button></td>
            <td class="right"><button class="spot" id="02"></button></td>
        </tr>
        <tr class="middle">
            <td class="left"><button class="spot" id="10"></button></td>
            <td class="center"><button class="spot" id="11"></button></td>
            <td class="right"><button class="spot" id="12"></button></td>
        </tr>
        <tr class="bottom">
            <td class="left"><button class="spot" id="20"></button></td>
            <td class="center"><button class="spot" id="21"></button></td>
            <td class="right"><button class="spot" id="22"></button></td>
        </tr>
    </table>
</center>
</#assign>
<script src="js/tictactoe.js"></script>
<#include "main.ftl">
