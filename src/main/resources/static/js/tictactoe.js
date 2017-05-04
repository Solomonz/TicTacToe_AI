function play(letter, row, col) {
    if(row < 0 || row > 2 || col < 0 || col > 3) {
        return;
    }
    document.getElementById(row + "" + col).innerText = letter;
}


// Waits for DOM to load before running
$(document).ready(() => {
    const $dropdownS1 = $("#suggestionsS1");
    const $dropdownC1 = $("#suggestionsC1");
    const $dropdownS2 = $("#suggestionsS2");
    const $dropdownC2 = $("#suggestionsC2");
    const $suggestions1 = $("#suggestions1");
    const $suggestions2 = $("#suggestions2");
    const $suggest1 = $("#showSuggestions1");
    const $suggest2 = $("#showSuggestions2");
    const $S1 = $("#s1");
    const $C1 = $("#c1");
    const $S2 = $("#s2");
    const $C2 = $("#c2");

    $suggest1.on('change', event => {
      if($suggest1.is(":checked")) {
        $suggestions1.css("display", "table-row");
      } else {
        $suggestions1.css("display", "none");
      }
      $S1.keyup();
      $C1.keyup();
    });

    $suggest2.on('change', event => {
      if($suggest2.is(":checked")) {
        $suggestions2.css("display", "table");
      } else {
        $suggestions2.css("display", "none");
      }
      $S2.keyup();
      $C2.keyup();
    });

function changeButtonState() {
  if ($('#s1')[0].value == '' || $('#c1')[0].value == '') {
    $('#startIntersect').attr('disabled', '');
  } else {
    $('#startIntersect').removeAttr('disabled');
  }
  if ($('#s2')[0].value == '' || $('#c2')[0].value == '') {
    $('#endIntersect').attr('disabled', '');
  } else {
    $('#endIntersect').removeAttr('disabled');
  }
}

    $S1.keyup(event => {
        if ($suggest1.is(":checked")) {
            if($S1.val() == "") {
                $dropdownS1.html("");
            } else {
                const postParameters = { input: $S1.val(), which: "s1", table: "suggestionsS1" };

                $.post("/maps/correct", postParameters, responseJSON => {
                    const responseObject = JSON.parse(responseJSON);

                    $dropdownS1.html(responseObject.suggestions);
                    $dropdownS1.css("display", "table");
                });
            }
        }
        changeButtonState();
    });

    $C1.keyup(event => {
        if ($suggest1.is(":checked")) {
            if($C1.val() == "") {
                $dropdownC1.html("");
            } else {
                const postParameters = { input: $C1.val(), which: "c1", table: "suggestionsC1" };

                $.post("/maps/correct", postParameters, responseJSON => {
                    const responseObject = JSON.parse(responseJSON);

                    $dropdownC1.html(responseObject.suggestions);
                    $dropdownC1.css("display", "table");
                });
            }
        }
        changeButtonState();
    });

    $S2.keyup(event => {
        if ($suggest2.is(":checked")) {
            if($S2.val() == "") {
                $dropdownS2.html("");
            } else {
                const postParameters = { input: $S2.val(), which: "s2", table: "suggestionsS2" };

                $.post("/maps/correct", postParameters, responseJSON => {
                    const responseObject = JSON.parse(responseJSON);

                    $dropdownS2.html(responseObject.suggestions);
                    $dropdownS2.css("display", "table");
                });
            }
        }
        changeButtonState();
    });

    $C2.keyup(event => {
        if ($suggest2.is(":checked")) {
            if($C2.val() == "") {
                $dropdownC2.html("");
            } else {
                const postParameters = { input: $C2.val(), which: "c2", table: "suggestionsC2" };

                $.post("/maps/correct", postParameters, responseJSON => {
                    const responseObject = JSON.parse(responseJSON);

                    $dropdownC2.html(responseObject.suggestions);
                    $dropdownC2.css("display", "table");
                });
            }
        }
        changeButtonState();
    });
});
