
public class GameBoard {
    private String[][] gameBoard;
    private String[][] gameBoardCloak;
  //  private String[][] mineLocations;
    private int gridSize;
    private int mineCount;

    public void createBoard(int gridSizeIn, int mineCountIn) {

        gridSize = gridSizeIn;
        mineCount = mineCountIn;
        if(mineCount >= gridSize*gridSize){
            mineCount = (gridSize*gridSize)-1;
        }

        gameBoard = new String[gridSize][gridSize];
        gameBoardCloak = new String[gridSize][gridSize];
        int randX;
        int randY;
        int temp;



        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gameBoard[i][j] = "0";
                gameBoardCloak[i][j] = "🍄";

            }
        }

        for (int i = 0; i < mineCount+1; i++) {
            randX = (int) (Math.random() * (double) gridSize);
            randY = (int) (Math.random() * (double) gridSize);

            //spawn rabbit last
            if(i == mineCount){
                if(gameBoard[randX][randY] == "\uD83D\uDC0D"){
                    i--;
                    continue;
                }else{
                    gameBoard[randX][randY] = "\uD83D\uDC07";
                    break;
                }
            }

            //spawn snakes
            if (gameBoard[randX][randY] == "\uD83D\uDC0D") {
                i--;
                continue;
            } else {
                gameBoard[randX][randY] = "\uD83D\uDC0D";
                if (randX + 1 < gridSize) {
                    if (gameBoard[randX + 1][randY] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX + 1][randY]) + 1;

                        gameBoard[randX + 1][randY] = ("" + (temp));
                    }
                }

                if (randX - 1 >= 0) {
                    if (gameBoard[randX - 1][randY] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX - 1][randY]) + 1;

                        gameBoard[randX - 1][randY] = ("" + (temp));
                    }
                }

                if (randY + 1 < gridSize) {
                    if (gameBoard[randX][randY+1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX][randY+1]) + 1;

                        gameBoard[randX][randY+1] = ("" + (temp));
                    }
                }

                if (randY - 1 >= 0) {
                    if (gameBoard[randX ][randY-1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX ][randY-1]) + 1;

                        gameBoard[randX][randY-1] = ("" + (temp));
                    }
                }

                if (randX + 1 < gridSize && randY +1 < gridSize) {
                    if (gameBoard[randX+1][randY+1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX+1 ][randY+1]) + 1;

                        gameBoard[randX+1][randY+1] = ("" + (temp));
                    }
                }

                if (randX - 1 >= 0 && randY -1 >= 0) {
                    if (gameBoard[randX-1][randY-1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX-1 ][randY-1]) + 1;

                        gameBoard[randX-1][randY-1] = ("" + (temp));
                    }
                }

                if (randX + 1 < gridSize && randY -1 >= 0) {
                    if (gameBoard[randX+1][randY-1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX+1 ][randY-1]) + 1;

                        gameBoard[randX+1][randY-1] = ("" + (temp));
                    }
                }

                if (randX -1 >= 0 && randY + 1 < gridSize) {
                    if (gameBoard[randX-1][randY+1] != "\uD83D\uDC0D") {

                        temp = Integer.parseInt(gameBoard[randX-1 ][randY+1]) + 1;

                        gameBoard[randX-1][randY+1] = ("" + (temp));
                    }
                }

            }
        }

        printBoard();
    }

    //if bool pass true = flag / false=click
    public int playerTurn(int row, int col, boolean flag){
        int returnValue;
        boolean winChecker;
        if (flag==true){
            returnValue = flagLocation(row,col);
        }else {
            returnValue = clickLocation(row,col);
        }

        winChecker=winCheck();

        if (winChecker==true){
            return 3;
        }

        if (returnValue == 2){
            printBoardFull();
        }else {
            printBoard();
        }
        return returnValue;
    }

    private int clickLocation(int row, int col) {
        //1 = playing, 2=lose, 3 = win 4= rabbit
        boolean winChecker =false;
        if (!((gameBoardCloak[row][col] == " ") || (gameBoardCloak[row][col] == "⚐"))) {
            if (gameBoard[row][col] == "\uD83D\uDC0D") {
                return 2;
            }

            else if (gameBoard[row][col] == "\uD83D\uDC0D") {
                gameBoardCloak[row][col]=" ";
                rabbitPower(row,col);
                return 4;
            }

            else if (gameBoard[row][col] == "0"){
                revealLocations(row,col);
                gameBoardCloak[row][col]=" ";
                return 1;
            }
            else {
                gameBoardCloak[row][col]=" ";
                return 1;
            }





        }

        return 1;

    }

    private boolean winCheck(){
        int boardRevealed = 0;
        int winBankCount = (gridSize*gridSize) - mineCount;
        int flagCount =0;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(gameBoardCloak[i][j] == " "){
                    boardRevealed++;
                }
                if (gameBoardCloak[i][j]=="⚐"){
                    flagCount++;
                }
            }
        }

        if (boardRevealed == winBankCount && flagCount == mineCount){
            return true;
        }else {
            return false;
        }
    }

    private void rabbitPower(int row, int col){
        boolean uncloak =false;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gameBoardCloak[i][j]=="🍄" && gameBoard[i][j]=="🐍" && uncloak==false){
                    gameBoardCloak[i][j]="⚐";
                    uncloak=true;

                }
            }
        }

        if (uncloak==false){
            System.out.println("Rabbit says your all clear!");
        }
    }
    private int flagLocation(int row, int col) {
        //flagged=5 cannot flag=6
        if (gameBoardCloak[row][col] == "🍄"){
            gameBoardCloak[row][col]="⚐";
            return 5;
        }else if(gameBoardCloak[row][col]=="⚐"){
            gameBoardCloak[row][col]="🍄";
            return 5;
        }

        return 6;
    }

    private void revealLocations(int row, int col) {
        for (int i = -1; i < 2; i++) {
            if (row+i>=0 && row+i<gridSize) {
                for (int j = -1; j < 2; j++) {
                    if (col+j>=0 && col+j<gridSize){
                        if (gameBoard[row+i][col+j] == "0"){
                            gameBoardCloak[row+i][col+j]=" ";
                        }
                    }

                }
            }
        }



    }



    private void printBoard() {
        String[] alaphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < gridSize; i++) {
            if (i == 0) {
                System.out.printf("\t");
            }
            System.out.print(alaphabet[i] + "\t");
        }
        System.out.println();

        for (int i = 0; i < gridSize;i++) {
            for (int j = 0; j < gridSize; j++) {

                if (j == 0) {
                    System.out.print(i+1 + "\t");
                }if (gameBoardCloak[i][j] == "🍄" || gameBoardCloak[i][j] == "⚐") {
                    System.out.print(gameBoardCloak[i][j] + "\t");
                }else{
                    System.out.print(gameBoard[i][j] + "\t");
                }

                if (j == gridSize - 1) {
                    System.out.print("\n");
                }
            }

        }
    }

    private void printBoardFull() {
        String[] alaphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < gridSize; i++) {
            if (i == 0) {
                System.out.printf("\t");
            }
            System.out.print(alaphabet[i] + "\t");
        }
        System.out.println();

        for (int i = 0; i < gridSize;i++) {
            for (int j = 0; j < gridSize; j++) {

                    if (j == 0) {
                        System.out.print(i+1 + "\t");
                    }

                    System.out.print(gameBoard[i][j] + "\t");
                    if (j== gridSize-1){
                        System.out.print("\n");
                    }

            }

        }
    }
}
