const Result = {
    Xwon : 0,
    Owon : 1,
    Draw : 2,
    StillGoing : 3,
    Illegal : 4,
}

class AIPlayer {
    constructor(moves)
    {
        this.moves = moves
    }

    getMove(board_code)
    {
        return this.moves[board_code];
    }
}

class Board {

    constructor(x, y, c_size)
    {
        // starting as an empty board, X:1 and O:-1
        this.board = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        this.still_going = true;
        this.c_size = c_size
        this.x = x
        this.y = y
    }

    
    playAsX(move)
    {
        // check if its an illegal move
        if (this.board[move] != 0)
            return false;
        this.board[move] = 1;
        return true;
    }

    playAsO(move)
    {
        // check if its an illegal move
        if (this.board[move] != 0)
            return false;
        this.board[move] = -1;
        return true;
    }

    // returns an integer representing the board
    board2int()
    {
        let code = 0;
        for(let i = 0; i < 9; i++)
            code += (this.board[i] + 1) * Math.pow(3, i)
        return code;
    }

    // rests the game state
    reset()
    {
        this.board = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        this.still_going = true;
    }

    // returns the state of the game
    checkWinner()
    {
        // check diagonals
        let vertical = this.board[0] + this.board[4] + this.board[8];
        let horizontal = this.board[2] + this.board[4] + this.board[6];
        let draw = true;

        if (vertical == 3 || horizontal == 3) {
            this.still_going = false;
            return Result.Xwon;
        }
        if (vertical == -3 || horizontal == -3) {
            this.still_going = false;
            return Result.Owon;
        }
        // check vertically and horizontally
        for (let i = 0; i < 3; i++) {
            vertical = horizontal = 0;
            for (let j = 0; j < 3; j++) {
                vertical += this.board[i + 3 * j];
                horizontal += this.board[j + 3 * i];

                if (this.board[i + 3 * j] == 0) {
                    draw = false;
                }
            }

            if (vertical == 3 || horizontal == 3) {
                this.still_going = false;
                return Result.Xwon;
            }

            if (vertical == -3 || horizontal == -3) {
                this.still_going = false;
                return Result.Owon;
            }
        }
        if (!draw)
            return Result.StillGoing;

        this.still_going = false;
        return Result.Draw;
    }

    show()
    {
        // drawing the board
        strokeWeight(10);
        fill(10);
        line(this.x + this.c_size, this.y, this.x + this.c_size, this.y + 3 * this.c_size );
        line(this.x + 2 * this.c_size, this.y, this.x + 2 * this.c_size, this.y + 3 * this.c_size);
        line(this.x , this.y + this.c_size, this.x + 3 * this.c_size, this.y + this.c_size);
        line(this.x, this.y + 2 * this.c_size, this.x + 3 * this.c_size, this.y + 2 * this.c_size);
        for(let i = 0; i < 3; i++)
        {
            for(let j = 0; j < 3; j++)
            {
                let ele = this.board[i + 3 * j];
                let char = ' ';
                if (ele == 1)
                    char = 'x';
                else if (ele == -1)
                    char = 'o';
                textSize(100);
                textAlign(CENTER);
                text(char , i * this.c_size + this.c_size / 2, j * this.c_size + this.c_size / 1.5);
            }
        }
    }

}

function getHumanMove(c_size) {
    let cx = int(Math.floor(mouseX/c_size));
    let cy = int(Math.floor(mouseY/c_size));
    return cx + cy*3;
}