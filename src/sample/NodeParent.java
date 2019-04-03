package sample;

import javafx.scene.Node;

public class NodeParent {

        private int row;
        private int column;
        private Node node;

        public NodeParent(int row, int column, Node node) {
            this.row = row;
            this.column = column;
            this.node = node;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public Node getNode() {
            return node;
        }

        public void updateNode(int _row,int _column){
            this.column = _column;
            this.row = _row;
        }

        public boolean inBounds(int _row,int _column,int maxCol,int maxRow){
            if(_row >=0 && _row<=maxRow && _column>=0 && _column<=maxCol){
                return true;
            }
            else return false;
        }
}
