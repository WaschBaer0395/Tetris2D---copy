package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    @FXML
    private Button down;

    @FXML
    private Button up;

    @FXML
    private Button left;

    @FXML
    private Button right;


    @FXML
    protected GridPane gamePane;

    final char[][] TSpielfigur = {{' ','T',' '}, {'T','T','T'}, {' ',' ',' '},{' ',' ',' '}};
    final char[][] ISpielfigur = {{' ','I',' '}, {' ','I',' '}, {' ','I',' '},{' ','I',' '}};
    final char[][] OSpielfigur = {{'O','O',' '}, {'O','O',' '}, {' ',' ',' '},{' ',' ',' '}};
    final char[][] ZSpielfigur = {{'Z','Z',' '}, {' ','Z','Z'}, {' ',' ',' '},{' ',' ',' '}};
    final char[][] SSpielfigur = {{' ','S','S'}, {'S','S',' '}, {' ',' ',' '},{' ',' ',' '}};
    final char[][] LSpielfigur = {{'L',' ',' '}, {'L',' ',' '}, {'L','L',' '},{' ',' ',' '}};
    final char[][] JSpielfigur = {{' ',' ','J'}, {' ',' ','J'}, {' ','J','J'},{' ',' ',' '}};
    Color color;


    protected int posY = 0;
    protected int posX = 3;

    protected char[][]field=new char[21][10];

    final static int maxR = 20;
    final static int maxC = 9;

    protected char[][]figur = new char[3][2];
    boolean newFig = false;






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createNewFigurine();
        drawField(field);
        registerKeyboardHandler(gamePane);
    }

    private void registerKeyboardHandler(Pane pane) {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean consumed = false;

                switch (keyEvent.getCode()) {
                    case DOWN:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("DOWN");
                        consumed = true;
                        break;
                    case LEFT:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("LEFT");
                        consumed = true;
                        break;
                    case RIGHT:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("RIGHT");
                        consumed = true;
                        break;
                    case UP:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("UP");
                        consumed = true;
                        break;

                    case Q:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("Q");
                        consumed = true;
                        break;

                    case E:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("E");
                        consumed = true;
                        break;

                    case ENTER:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        evenT("ENTER");
                        consumed = true;
                        break;

                    default:
                        break;
                }
/*
                System.out.println("--------------------------------");
                for(int y=0;y<field.length;y++){
                    System.out.println();
                    for(int x=0;x<field[0].length;x++){
                        System.out.print(field[y][x]);
                    }
                }
*/

                if (consumed) {
                    keyEvent.consume();
                }
            }
        });
        pane.setFocusTraversable(true);
    }

    private boolean evenT(String event){

        boolean endOfField = false;

        switch(event){
            case "DOWN":
                    posY++;
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"UP":
                    posY--;
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"LEFT":
                    posX--;
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"RIGHT":
                    posX++;
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case "ENTER":
                break;

            case "Q":
                break;

            case "E":
                break;

        }
        return endOfField;
    }

    public void drawField(char[][] _field){
        for(int y=0;y<=maxR;y++){
            for(int x=0;x<=maxC;x++){
                getTypeOfBlock(_field[y][x],y,x);
            }
        }
    }

    private void getTypeOfBlock(char block,int _y,int _x){
        Rectangle rectangle;

        switch(block){
            case 'T':
                rectangle = new Rectangle(33,33,Color.PURPLE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'I':
                rectangle = new Rectangle(33,33,Color.SKYBLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'O':
                rectangle = new Rectangle(33,33,Color.YELLOW);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'S':
                rectangle = new Rectangle(33,33,Color.GREEN);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'Z':
                rectangle = new Rectangle(33,33,Color.RED);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'J':
                rectangle = new Rectangle(33,33,Color.BLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'L':
                rectangle = new Rectangle(33,33,Color.ORANGE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            default:
                rectangle = new Rectangle(33,33,Color.TRANSPARENT);
                break;
        }
            placeNode(rectangle,_y,_x);
    }

    public void placeNode(Rectangle _rectangle,int _y,int _x){
        GridPane.setRowIndex(_rectangle, _y );
        GridPane.setColumnIndex(_rectangle, _x);
        gamePane.getChildren().addAll(_rectangle);
    }

    private void clearField(){
        Node node = gamePane.getChildren().get(0);
        gamePane.getChildren().clear();
        gamePane.getChildren().add(0,node);
    }

    /*private void changePosition(int oldPosY,int oldPosX,int newPosY,int newPosX){
        clearPane();
        rewritePane();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if (figArray[y][x] != null) {
                    playfield[oldPosY + y][oldPosX + x] = null;
                    placeNode(newPosY + y, newPosX + x);
                }
            }
        }
    }
*/


    @FXML
    private void buttonAction(ActionEvent e) {

        if (e.getSource() == down) {
            evenT("DOWN");
        }

        if (e.getSource() == up) {
            evenT("UP");
        }

        if (e.getSource() == left) {
            evenT("LEFT");
        }

        if (e.getSource() == right) {
            evenT("RIGHT");
        }
    }

    /*protected  boolean checkForCollision(int newY, int newX){
        boolean collision = false;
        Node[][]buffer = playfield;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if(figArray[y][x]!=null) {
                    buffer[posY+y][posX+x] = null;
                }
            }
        }
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if(figArray[y][x]!=null) {
                    if (wallCollision(newX + x)) {
                        collision = true;
                        break;
                    } else if(objectCollisionHor(buffer,newX +x,posY+y)){
                        collision = true;
                        break;
                    } else if(endOfField(newY+y)){
                        newFig = true;
                        collision = true;
                        break;
                    } else if (objectCollisionVer(buffer,posX+x, newY + y)) {
                        newFig = true;
                        collision = true;
                        break;
                    }
                }
            }
        }
        return collision;
    }*/

    protected boolean wallCollision(int _newCol){
        if(_newCol>maxC || _newCol<0){
            return true;
        }
        return false;
    }

    protected boolean objectCollisionHor(Node[][] buffer,int _newCol,int _newRow){
        if(buffer[_newRow][_newCol] != null){
            return true;
        }
        else return false;
    }

    protected boolean objectCollisionVer(Node[][] buffer,int _newCol ,int _newRow){
        if(buffer[_newRow][_newCol] != null){
            return true;
        }
        else return false;
    }


    protected  boolean endOfField(int _newRow){
        if(_newRow>maxR){
            return true;
        }
        else return false;
    }

    protected void createNewFigurine(){
        posY = 0;
        posX = 3;

        figur = randomizedFig();


        for (int y = 0; y < figur.length; y++) {
            for (int x = 0; x < figur[y].length; x++) {
                if(figur[y][x]!=' ') {
                    field[posY+y][posX+x] = figur[y][x];
                }
            }
        }
    }

    public char[][] randomizedFig(){
        char[][] _figur;
        int zufall=(int)Math.round(Math.random()*7);

        switch (zufall){
            case 0:
                _figur = ISpielfigur;
                break;
            case 1:
                _figur = OSpielfigur;
                break;
            case 2:
                _figur = SSpielfigur;
                break;
            case 3:
                _figur = TSpielfigur;
                break;
            case 4:
                _figur = ZSpielfigur;
                break;
            case 5:
                _figur = LSpielfigur;
                break;
            case 6:
                _figur = JSpielfigur;
                break;
            default:
                _figur = OSpielfigur;
                break;
        }

        return _figur;
    }

}
