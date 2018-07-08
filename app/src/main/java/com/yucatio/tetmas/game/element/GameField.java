package com.yucatio.tetmas.game.element;

import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.view.GameWorldLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class GameField implements Serializable {
    //    private static final String TAG = "GameField";
    private static final CellState[] playerCellState = {CellState.RED, CellState.BLUE};

    private static final int tetrominoNum = Tetromino.values().length;

    /** Point pool */
    private Point[][] pointPool;

    private int gameWidth;
    private int gameHeight;
    private int width;
    private int height;

    private CellState[][] field;
    private TetrominoPosition activeTetrominoPosition;

    /** EMPTY cellクラスタ */
    private Set<Set<Point>> emptyCellClusterSet = new HashSet<>();

    /** 置かれたテトロミノ */
    private List<List<TetrominoPosition>> placedTetrominoList = new ArrayList<>();
    /** 囲まれて陣地になったcell */
    private List<List<Point>> surroundedCell = new ArrayList<>();
    /** 死地cell */
    private List<Point> deadCell = new ArrayList<>();
    /** 新たに陣地になったセル(animation表示用) */
    private List<Point> newlySurroundedCell = new ArrayList<>();
    /** 新たに敵地になったセル(animation表示用) */
    private List<Point> newlySurroundedCellAnother = new ArrayList<>();
    /** 新たに死地になったセル(animation表示用) */
    private List<Point> newlyDeadCell = new ArrayList<>();
    /** 新たに死地になったセル(animation表示用) */
    private List<Point> newlyDeadCell2 = new ArrayList<>();

    /** スコア */
    private int[] scores = new int[2];
    /** スコア(表示用) */
    private float[] displayScore = new float[2];

    public GameField(FieldSize fieldSize) {
        gameWidth = fieldSize.getWidth();
        gameHeight = fieldSize.getHeight();
        width = gameWidth + 2;
        height = gameHeight + 2;

        field = new CellState[height][width];
        placedTetrominoList.add(new ArrayList<TetrominoPosition>());
        placedTetrominoList.add(new ArrayList<TetrominoPosition>());
        surroundedCell.add(new ArrayList<Point>());
        surroundedCell.add(new ArrayList<Point>());

        initField();

        pointPool = new Point[gameHeight][gameWidth];
        setUpPointPool();
        initEmptyCellClusterSet();
    }

    private void initField() {
        // WALL (TOP and BOTTOM)
        for (int i = 0; i < width; i++) {
            field[0][i] = CellState.WALL;
            field[height -1][i] = CellState.WALL;
        }

        // WALL (RIGHT and LEFT)
        for (int i = 1; i< height - 1; i++) {
            field[i][0] = CellState.WALL;
            field[i][width -1] = CellState.WALL;
        }

        // EMPTY
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j ++) {
                field[i][j] = CellState.EMPTY;
            }
        }

    }

    private void setUpPointPool() {
        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                pointPool[i][j] = new Point(j, i);
            }
        }
    }

    private void initEmptyCellClusterSet() {
        Set<Point> set = new HashSet<>();
        for (int i=0; i < gameHeight; i++) {
            set.addAll(Arrays.asList(pointPool[i]));
        }

        emptyCellClusterSet.add(set);
    }

    public boolean hasSpace(Tetromino tetromino) {
        boolean result = false;
        for (Set<Point> pointSet : emptyCellClusterSet) {
            if (hasSpace(pointSet, tetromino)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * pointSetにtetrominoListの少なくとも1つのテトロミノが置けるか返します
     * @param pointSet セルの連続した集合
     * @param tetrominoList 検査対象のテトロミノ
     * @return tetrominoListの中の少なくとも1つのテトロミノが置ける場合true。1つも置けない場合false
     */
    public boolean hasSpace(Set<Point> pointSet, List<Tetromino> tetrominoList) {
        if (pointSet.size() <= 3) {
            return false;
        }
        if (pointSet.size() >= 4 && tetrominoList.size() >= tetrominoNum) {
            return true;
        }

        boolean result = false;
        for (Tetromino tetromino : tetrominoList) {
            if (hasSpace(pointSet, tetromino)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean hasSpace(Set<Point> pointSet, Tetromino tetromino) {
        for (Point point : pointSet) {
            for (int i=0; i<tetromino.getSpinNum(); i++) {
                // spinぶん
                Point[] tetrominoPoints = tetromino.getTetrominoPoints(i);
                int offsetX = tetrominoPoints[0].x;
                int offsetY = tetrominoPoints[0].y;
                boolean result = true;
                // pointをtetrominoPoint[0]としたとき、ほかのtetrominoPointがpointSetに含まれるかどうか調べる
                for (int j = 1; j < tetrominoPoints.length; j++) {
                    Point tetrominoPoint = tetrominoPoints[j];
                    int pointX = point.x+tetrominoPoint.x-offsetX;
                    int pointY = point.y+tetrominoPoint.y-offsetY;
                    if (pointX < 0 || pointX >= gameWidth || pointY < 0 || pointY >= gameHeight) {
                        result = false;
                        break;
                    }
                    if (!pointSet.contains(pointPool[pointY][pointX])) {
                        result = false;
                        break;
                    }
                }

                if (result) {
                    return true;
                }
            }
        }

        return false;
    }


    public Tetromino changeActiveTetromino(int playerIndex, Tetromino tetromino) {
        Tetromino previousTetromino;
        if (activeTetrominoPosition != null) {
            previousTetromino = activeTetrominoPosition.getTetromino();
            activeTetrominoPosition.setTetromino(tetromino);
            activeTetrominoPosition.setSpin(0);
        } else {
            previousTetromino = null;
            activeTetrominoPosition = new TetrominoPosition(tetromino, GameWorldLayout.activeTetrominoOffsetX.get(tetromino), GameWorldLayout.activeTetrominoOffsetY.get(playerIndex), 0);
        }
        adjustTetromino();

        return previousTetromino;
    }

    public Tetromino cancelTetromino() {
        if (activeTetrominoPosition == null) {
            return null;
        }

        Tetromino previous = activeTetrominoPosition.getTetromino();
        activeTetrominoPosition = null;
        return previous;
    }

    public boolean moveTetromino(Direction direction) {
        int previousX = activeTetrominoPosition.getX();
        int previousY = activeTetrominoPosition.getY();
        switch (direction) {
            case TOP:
                activeTetrominoPosition.translateY(-1);
                break;
            case BOTTOM:
                activeTetrominoPosition.translateY(1);
                break;
            case RIGHT:
                activeTetrominoPosition.translateX(1);
                break;
            case LEFT:
                activeTetrominoPosition.translateX(-1);
        }

        adjustTetromino();

        return !(previousX == activeTetrominoPosition.getX()
                && previousY == activeTetrominoPosition.getY());
    }

    public boolean moveTetromino(int x, int y) {
        activeTetrominoPosition.setX(x - 2);
        activeTetrominoPosition.setY(y - 2);
        adjustTetromino();
        return true;
    }

    public boolean spinTetromino() {
        activeTetrominoPosition.spin();
        adjustTetromino();
        return true;
    }

    public boolean canPlaceTetromino(TetrominoPosition activeTetrominoPosition) {
        // 置けるか
        boolean result = true;
        for (Point point : activeTetrominoPosition.getTetromino().getTetrominoPoints(activeTetrominoPosition.getSpin())) {
            int x = activeTetrominoPosition.getX() + point.x;
            int y = activeTetrominoPosition.getY() + point.y;

            if (x < 0 || x >= gameWidth || y < 0 || y >= gameHeight) {
                result = false;
                break;
            }

            if (field[y + 1][x + 1] != CellState.EMPTY) {
                result = false;
                break;
            }
        }

        return result;
    }

    public boolean canPlaceTetromino() {
        return canPlaceTetromino(activeTetrominoPosition);
    }

    public boolean placeTetromino(int playerIndex, int playerAnotherIndex, List<Tetromino> playerTetromino, List<Tetromino> anotherPlayerTetromino, boolean checkAll) {
        if (! canPlaceTetromino()) {
            return false;
        }

        // 置く
        CellState state = playerCellState[playerIndex];
        for (Point point : activeTetrominoPosition.getTetromino().getTetrominoPoints(activeTetrominoPosition.getSpin())) {
            int x = activeTetrominoPosition.getX() + point.x;
            int y = activeTetrominoPosition.getY() + point.y;

            field[y + 1][x + 1] = state;
        }

        scores[playerIndex] += 4;

        placedTetrominoList.get(playerIndex).add(activeTetrominoPosition);

        // 陣地update
        updateTerritory(playerIndex, playerAnotherIndex, playerTetromino, anotherPlayerTetromino, checkAll);

        activeTetrominoPosition = null;

        return true;
    }

    private void updateTerritory(int playerIndex, int playerAnotherIndex, List<Tetromino> playerTetromino, List<Tetromino> anotherPlayerTetromino, boolean checkAll) {
        Point[] activeTetrominoPointList = activeTetrominoPosition.getTetromino().getTetrominoPoints(activeTetrominoPosition.getSpin());

        // empty cell cluster update
        Set<Point> target = null;
        int x0 = activeTetrominoPosition.getX() + activeTetrominoPointList[0].x;
        int y0 = activeTetrominoPosition.getY() + activeTetrominoPointList[0].y;
        for (Set<Point> set : emptyCellClusterSet) {
            if (set.contains(pointPool[y0][x0])) {
                target = set;
                break;
            }
        }
        assert target != null;

        emptyCellClusterSet.remove(target);

        for (Point point : activeTetrominoPointList) {
            int x = activeTetrominoPosition.getX() + point.x;
            int y = activeTetrominoPosition.getY() + point.y;

            target.remove(pointPool[y][x]);
        }

        while(!target.isEmpty()) {
            findTerritory(playerIndex, target, playerTetromino, anotherPlayerTetromino, emptyCellClusterSet, newlySurroundedCell, newlyDeadCell);
        }

        if (!checkAll) {
            return;
        }

        Set<Set<Point>> newEmptyCellClusterSet = new HashSet<>();
        for (Set<Point> pointSet : emptyCellClusterSet) {
            findTerritory(playerAnotherIndex, pointSet, anotherPlayerTetromino, playerTetromino, newEmptyCellClusterSet, newlySurroundedCellAnother, newlyDeadCell2);
        }

        emptyCellClusterSet = newEmptyCellClusterSet;

    }

    public void findTerritory(int playerIndex, Set<Point> pointSet, List<Tetromino> playerTetromino, List<Tetromino> anotherPlayerTetromino, Set<Set<Point>> emptyPointSetBuffer, List<Point> newTerritoryBuffer, List<Point> newDeadCellBuffer) {
        CellState state = playerCellState[playerIndex];
        Point point = pointSet.iterator().next();
        Set<Point> newCluster = new HashSet<>();

        if (findCellCluster(point, pointSet, newCluster, state)) {
            // 自テトロミノで囲まれている
            if (!hasSpace(newCluster, anotherPlayerTetromino)) {
                // 相手が置けない
                // 自陣に
                newTerritoryBuffer.addAll(newCluster);
                scores[playerIndex] += newCluster.size();
                for (Point point1 : newCluster) {
                    field[point1.y + 1][point1.x + 1] = state;
                }
            } else {
                // 相手が置ける
                // empty
                emptyPointSetBuffer.add(newCluster);
            }
        } else {
            // 自テトロミノで囲まれていない
            if (!hasSpace(newCluster, anotherPlayerTetromino) && !hasSpace(newCluster, playerTetromino)) {
                // 相手も自分も置けない
                // 死地
                newDeadCellBuffer.addAll(newCluster);
                for (Point point1 : newCluster) {
                    field[point1.y + 1][point1.x + 1] = CellState.DEAD;
                }
            } else {
                // 相手か自分は置ける余地がある
                // empty
                emptyPointSetBuffer.add(newCluster);

            }
        }

    }


    /**
     * startPointが含まれるfromに含まれる連続したempty cellをtoにまとめ、さらにstateおよびwallで囲まれているかどうか判定します
     * @param startPoint 基準点
     * @param from もとのempty set
     * @param to 新しいempty set
     * @param state 囲まれているかを判定すべきstate
     * @return stateおよびWALLで囲まれている場合true, そうでない場合false
     */
    public boolean findCellCluster(Point startPoint, Set<Point> from, Set<Point> to, CellState state) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(startPoint);

        boolean result = true;

        while (queue.peek() != null) {
            Point point = queue.poll();

            if(!from.contains(point)) {

                if (field[point.y + 1][point.x + 1] != CellState.EMPTY
                        && field[point.y + 1][point.x + 1] != CellState.WALL
                        && field[point.y + 1][point.x + 1] != state) {
                    result = false;
                }

                continue;
            }

            from.remove(point);
            to.add(point);

            if (point.x - 1  >= 0) {
                queue.offer(pointPool[point.y][point.x - 1]);
            }
            if (point.x + 1 < gameWidth) {
                queue.offer(pointPool[point.y][point.x + 1]);
            }
            if (point.y - 1 >= 0) {
                queue.offer(pointPool[point.y - 1][point.x]);
            }
            if (point.y + 1 < gameHeight) {
                queue.offer(pointPool[point.y + 1][point.x]);
            }

        }

        return result;

    }

    /**
     * テトロミノがフィールド内に収まるように調整します
     */
    private void adjustTetromino() {
        Point[] boundingBox = activeTetrominoPosition.getTetromino().getBoundingBox(activeTetrominoPosition.getSpin());

        // 左にはみ出しているか
        if (activeTetrominoPosition.getX() + boundingBox[0].x < 0) {
            activeTetrominoPosition.translateX(-(activeTetrominoPosition.getX() + boundingBox[0].x));
        }
        // 上にはみ出しているか
        if (activeTetrominoPosition.getY() + boundingBox[0].y < 0) {
            activeTetrominoPosition.translateY(-(activeTetrominoPosition.getY() + boundingBox[0].y));
        }

        // 右にはみ出しているか
        if (activeTetrominoPosition.getX() + boundingBox[1].x > gameWidth - 1) {
            activeTetrominoPosition.translateX(gameWidth - 1 - (activeTetrominoPosition.getX() + boundingBox[1].x));
        }

        // 下にはみ出しているか
        if (activeTetrominoPosition.getY() + boundingBox[1].y > gameHeight - 1) {
            activeTetrominoPosition.translateY(gameHeight - 1 - (activeTetrominoPosition.getY() + boundingBox[1].y));
        }

    }

    public CellState getPlayerCellState(int playerIndex) {
        return playerCellState[playerIndex];
    }

    public TetrominoPosition getActiveTetrominoPosition() {
        return activeTetrominoPosition;
    }

    public Point getPointPool(int x, int y) {
        return pointPool[y][x];
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public CellState getCellState(int x, int y) {
        return field[y+1][x+1];
    }

    public Set<Set<Point>> getEmptyCellClusterSet() {
        return emptyCellClusterSet;
    }

    public int getEmptyCellNum() {
        int total = 0;
        for (Set<Point> pointSet : emptyCellClusterSet) {
            total += pointSet.size();
        }
        return total;
    }

    public List<TetrominoPosition> getPlacedTetrominoPosition(int playerIndex) {
        return placedTetrominoList.get(playerIndex);
    }

    public List<Point> getSurroundedCell(int playerIndex) {
        return surroundedCell.get(playerIndex);
    }

    public List<Point> getNewlySurroundedCell() {
        return newlySurroundedCell;
    }

    public void clearNewlySurroundedCell(int playerIndex) {
        surroundedCell.get(playerIndex).addAll(newlySurroundedCell);
        newlySurroundedCell.clear();
    }

    public List<Point> getNewlySurroundedCellAnother() {
        return newlySurroundedCellAnother;
    }

    public void clearNewlySurroundedCellAnother(int playerIndex) {
        surroundedCell.get(playerIndex).addAll(newlySurroundedCellAnother);
        newlySurroundedCellAnother.clear();
    }


    public List<Point> getNewlyDeadCell() {
        return newlyDeadCell;
    }

    public void clearNewlyDeadCell() {
        deadCell.addAll(newlyDeadCell);
        newlyDeadCell.clear();
    }

    public List<Point> getNewlyDeadCell2() {
        return newlyDeadCell2;
    }

    public void clearNewlyDeadCell2() {
        deadCell.addAll(newlyDeadCell2);
        newlyDeadCell2.clear();
    }


    public List<Point> getDeadCell() {
        return deadCell;
    }

    public int getScore(int playerIndex) {
        return scores[playerIndex];
    }

    public int getDisplayScore(int playerIndex) {
        return (int)displayScore[playerIndex];
    }

    /**
     * 表示用スコアをupdateします
     * @return 表示用スコアが変化すればtrue, 既に目的のスコアになっていればfalse
     */
    public boolean updateDisplayScore(int playerIndex, float deltaScore) {
        displayScore[playerIndex] += deltaScore;

        displayScore[playerIndex] = Math.min(displayScore[playerIndex], scores[playerIndex]);

        if (Math.abs(displayScore[playerIndex] - scores[playerIndex]) < 0.001f) {
            displayScore[playerIndex] = scores[playerIndex];
            return false;
        }

        return true;

    }
}
