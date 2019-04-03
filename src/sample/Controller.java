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

    final int[][] TSpielfigur = {{0,1,0}, {1,1,1}, {0,0,0},{0,0,0}};
    final int[][] ISpielfigur = {{0,1,0}, {0,1,0}, {0,1,0},{0,1,0}};
    final int[][] OSpielfigur = {{1,1,0}, {1,1,0}, {0,0,0},{0,0,0}};
    final int[][] ZSpielfigur = {{1,1,0}, {0,1,1}, {0,0,0},{0,0,0}};
    final int[][] SSpielfigur = {{0,1,1}, {1,1,0}, {0,0,0},{0,0,0}};
    final int[][] LSpielfigur = {{1,0,0}, {1,0,0}, {1,1,0},{0,0,0}};
    final int[][] JSpielfigur = {{0,0,1}, {0,0,1}, {0,1,1},{0,0,0}};

    boolean newFig = false;


    protected int posY = 0;
    protected int posX = 3;

    protected int[][]field=new int[21][10];
    protected Node[][] playfield = new Node[21][10];

    final static int maxR = 20;
    final static int maxC = 9;
    protected int[][]figur = new int[4][3];
    protected Rectangle[][] figArray = new Rectangle[4][3];

    protected  int rotation = 0;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        figArray = randomizedFig();

        for (int y = 0; y < figur.length; y++) {
            for (int x = 0; x < figur[y].length; x++) {
                if (figur[y][x] == 1) {
                    figArray[y][x] = new Rectangle(33, 33, Color.BLACK);
                }
            }
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if(figArray[y][x]!=null) {
                    Rectangle rectangle = new Rectangle(33,33,Color.BLACK);
                    GridPane.setRowIndex(rectangle, posY + y);
                    GridPane.setColumnIndex(rectangle, posX + x);
                    gamePane.getChildren().addAll(rectangle);
                    playfield[posY+y][posX+x] = rectangle;
                }
            }
        }

        registerKeyboardHandler(gamePane);
    }

    private void registerKeyboardHandler(Pane pane) {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean consumed = false;
                newFig = false;

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
                if(!changePosition(posY+1,posX)){
                    posY++;
                }
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"UP":
                if(!changePosition(posY-1,posX)){
                    posY--;
                }
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"LEFT":
                if(!changePosition(posY,posX-1)){
                    posX--;
                }
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case"RIGHT":
                if(!changePosition(posY,posX+1)){
                    posX++;
                }
                if (newFig) {
                    createNewFigurine();
                }
                break;

            case "ENTER":
                break;

            case "Q":
                if(rotation==360) rotation=0;
                else rotation=rotation+90;
                changePosition(posY,posX);
                break;

            case "E":
                break;

        }
        return endOfField;
    }

    private void placeNode(int newPosY,int newPosX){
        Rectangle rectangle = new Rectangle(33,33,Color.BLACK);
        GridPane.setRowIndex(rectangle, newPosY );
        GridPane.setColumnIndex(rectangle, newPosX);
        gamePane.getChildren().addAll(rectangle);
        playfield[newPosY][newPosX] = rectangle;
    }

    private void clearPane(){
        Node node = gamePane.getChildren().get(0);
        gamePane.getChildren().clear();
        gamePane.getChildren().add(0,node);
    }

    private void rewritePane(){
        for(int y=0;y<playfield.length;y++){
            for(int x=0;x<playfield[0].length;x++){
                if(playfield[y][x]!=null) placeNode(y,x);
            }
        }

    }

    private boolean changePosition(int newPosY,int newPosX){
        boolean collision = false;
        clearPane();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if (figArray[y][x] != null) {
                    collision = rotate(y,x);
                }
            }
        }
        rewritePane();
        return collision;
    }



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

    protected  boolean checkForCollision(int newY, int newX){
        boolean wallCollision = false;
        boolean oCollision = false;
        boolean eofCollision = false;
        boolean collision = false;

        Node[][]buffer = playfield;


        buffer[posY][posX] = null;


        if (wallCollision(newX)) {
            wallCollision = true;
        }else if(objectCollisionHor(buffer,posY,newX)) {
            oCollision = true;
        }else if(endOfField(newY)){
            newFig = true;
            eofCollision = true;
        }else if (objectCollisionDown(buffer,newY,posX)) {
            newFig = true;
            oCollision = true;
        }
        else collision = false;

        if(eofCollision || oCollision || wallCollision){
            collision = true;
        }
        else {
            playfield[posY][posX] = null;
            placeNode(newY, newX);
        }
        return collision;
    }

    protected boolean wallCollision(int _newCol){
        if(_newCol>maxC || _newCol<0){
            return true;
        }
        return false;
    }

    protected boolean objectCollisionDown(Node[][] buffer,int _newY,int _x){
            if(buffer[_newY][_x] != null){
                return true;
            }
            else return false;
    }

    protected boolean objectCollisionHor(Node[][] buffer,int _Y,int _newX){
        if(buffer[_Y][_newX] != null){
            return true;
        }
        else return false;
    }

    protected  boolean endOfField(int _newRow){
        if(_newRow<=maxR){
            return false;
        }
        else return true;
    }

    protected void createNewFigurine(){
        for (int y = 0; y < figArray.length; y++) {
            for (int x = 0; x < figArray[y].length; x++) {
                if(figArray[y][x]!=null) {
                    Rectangle rectangle = new Rectangle(33,33,Color.BLACK);
                    playfield[posY+y][posX+x] = rectangle;
                    figArray[y][x]=null;
                }
            }
        }
        figArray=randomizedFig();
        posY = 0;
        posX = 3;
        for (int y = 0; y < figArray.length; y++) {
            for (int x = 0; x < figArray[y].length; x++) {
                if(figArray[y][x]!=null) {
                    placeNode(posY+y,posX+x);
                }
            }
        }
    }

    public Rectangle[][] randomizedFig(){
        int[][] _figur;
        Rectangle[][]rectFigur = new Rectangle[4][3];
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

        for (int y = 0; y < _figur.length; y++) {
            for (int x = 0; x < _figur[0].length; x++) {
                if (_figur[y][x] == 1) {
                    rectFigur[y][x] = new Rectangle(33, 33, Color.BLACK);
                }
            }
        }
        return rectFigur;
    }

    protected boolean rotate(int _y,int _x){
        boolean collision = false;

        int newY = posY+_y;
        int newX = posX+_x;

        // Fallunterscheidung nach den 4 erlaubten Winkel-Werten
        // für die Berechnung der gedrehten Koordinaten zeileSpielfigur und spalteSpielfigur
        switch (rotation) {
            case 0: // abc
                // def -> Original-Array, keine Veränderung
                // Zeilen und Spalten unverändert übernehmen
                newY = _y;
                newX = _x;
                break;

            case 90: 	// da
                // eb
                // fc
                // Zeilen der Spielfigur invertieren
                // Zeilen und Spalten vertauschen
                newY = _x;
                newX = 3 - _y;
                break;

            case 180: 	// fed
                // cba
                // Zeilen und Spalten der Spielfigur invertieren
                newY = 3 - _y;
                newX = 2 - _x;
                break;

            case 270: 	// cf
                // be
                // ad
                // Spalten der Spielfigur invertieren
                // Zeilen und Spalten vertauschen
                newX = _y;
                newY = 2 - _x;
                break;
        }

        return checkForCollision(newY+_y,newX+_x);

    }

}
