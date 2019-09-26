let human;
let cnv;
let board;
let humanTurn;
let player;
let globalResult;
let players;

function preload() {
  // TODO:load the json files for the latest generation
  players = loadJSON("players/latest.json");
}

function setup() {
  //centering the canvas
  let side = Math.min(windowWidth, windowHeight - 50);
  cnv = createCanvas(side, side);
  cnv.mousePressed(mouseClick);
  cnv.center("horizontal");
  board = new Board(0, 0, Math.floor(width/3));
  newGame();
}

function windowResized() {
  cnv.center("horizontal");
}

function draw() {
  background("#10af9f");
  board.show();
  if(globalResult != Result.StillGoing)
  {
    endGame(globalResult);
  }
  if(board.still_going && !humanTurn)
  {
    let board_code = board.board2int();
    let move = player.getMove(board_code);
    let legal;
    if(human == 'X')
      legal = board.playAsO(move);
    else
      legal = board.playAsX(move);
    //finish here
    if(!legal)
    {
      globalResult = Result.Illegal;
    }
    else
    {
      globalResult = board.checkWinner();
    }
    humanTurn = true;
  }
  
}

function mouseClick() {
  // play as the human
  if(board.still_going)
  {
    if(humanTurn)
    {
    let move = getHumanMove(board.c_size);
    let legal = true;
    if(human == 'X')
      legal = board.playAsX(move);
    else
      legal = board.playAsO(move);
    
    if(legal)
      humanTurn = !humanTurn
    }
    globalResult = board.checkWinner();

  }
  else
  {
    newGame();
  }
  
}

function newGame()
{
    player_index = int(random(0, players[0].length))
    player = new AIPlayer(players[0][player_index]);
    let element = document.getElementById("option");
    human = element.options[element.selectedIndex].value;
    board.reset()
    humanTurn = (human == 'X');
    globalResult = Result.StillGoing;
    loop();
}


function endGame(result)
{
  board.still_going = false;
  let endString = "";

  switch(result)
  {
    case Result.Illegal: endString = "The AI made an illegal move"; break;
    case Result.Xwon: endString = "X won the match"; break;
    case Result.Owon: endString = "O won the match"; break;
    case Result.Draw: endString = "Neither of the players won the match, Its a Draw"; break;
  }
  textSize(35);
  fill("#abcdef")
  text(endString, width / 2, height/2 - 30);
  text("click to start a new game", width/2, height/2);
  noLoop();


}