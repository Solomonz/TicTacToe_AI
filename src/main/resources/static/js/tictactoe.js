function play(letter, row, col) {
    if(row < 0 || row > 2 || col < 0 || col > 3) {
        return;
    }
    document.getElementById(row + "" + col).innerText = letter;
}

function clicked(where) {
  console.log("clicked: " + where);
  const postParameters = { type: "move", row: where[0], col: where[1] };
  
  $.post("/game/backend", postParameters, responseJSON => {
      const responseObject = JSON.parse(responseJSON);
      const newBoard = responseObject.boardAfterwards;
      drawBoard(newBoard);
      if(responseObject.statusOfGame == "draw") {
        document.getElementById("reset").innerText = "It's a draw! Play again?";
      } else if(responseObject.statusOfGame == "won") {
        if(responseObject.playerOnesTurnAfterwards) {
          document.getElementById("reset").innerText = "You lost! Play again?";
        } else {
          document.getElementById("reset").innerText = "You won! Play again?";
        }
      }
  });
}

function drawBoard(board) {
  for(let r = 0; r < 3; r++) {
    for(let c = 0; c < 3; c++) {
      let toPlay = '';
      if(board[r][c] == 1) {
        toPlay = 'X';
      } else if(board[r][c] == 2) {
        toPlay = 'O';
      }
      play(toPlay, r, c);
    }
  }
}

function reset() {
  $.post("/game/backend", { type: "reset" }, responseJSON => {
      drawBoard(JSON.parse(responseJSON).boardAfterwards);
      document.getElementById("reset").innerText = "Start a new game";
  });
}