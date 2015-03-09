package pl.edu.agh.cw;

import java.util.LinkedList;
import java.util.Random;

import pl.edu.agh.cw.board.Board;
import pl.edu.agh.cw.board.BoardCell;
import pl.edu.agh.cw.dictionary.Entry;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

/**
 * Implementacja z³o¿onej strategii krzy¿ówki - has³a pionowe i poziome przeplatane ze sob¹.
 * @author JakubSzczepankiewicz
 *
 */
public class MultiCrossStrategy extends Strategy{
	
	/**
     * Sprawdza czy ka¿de pole pomiêdzy pocz¹tkiem i koñcem mo¿e byæ œrodkiem s³owa 
     * i czy ostatnie pole mo¿e byæ koñcem s³owa w danym kierunku
     *
     * @param size - iloœæ sprawdzanych pól
     * @param board
     * @param dir - kierunek poziomo/pionowo
     * @param positionVer - pozycja pocz¹tkowego pola pionowo
     * @param positionHor - pozycja pocz¹tkowego pola poziomo
     * @return true jeœli wszystkie pola maj¹ odpowiednie atrybuty, false jeœli nie maj¹
     */
    private boolean checkAbilities(int size, Board board, int dir, int positionVer, int positionHor) {
        if (dir == BoardCell.ver) {
            for (int i = positionVer + 1; i < positionVer + size - 1; i++) {
                if (!board.getCell(positionHor, i).getAbility(BoardCell.in, dir)) {
                    return false;
                }
            }
            if (!board.getCell(positionHor, positionVer + size - 1).getAbility(BoardCell.end, dir)) {
                return false;
            }
        } else {
            for (int i = positionHor + 1; i < positionHor + size - 1; i++) {
                if (!board.getCell(i, positionVer).getAbility(BoardCell.in, dir)) {
                    return false;
                }
            }
            if (!board.getCell(positionHor + size - 1, positionVer).getAbility(BoardCell.end, dir)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sprawdza, czy któreœ pole pomiêdzy pocz¹tkiem a koñcem ma jak¹œ zawartoœæ
     *
     * @param size - iloœæ sprawdzanych pól
     * @param board
     * @param dir - kierunek poziomo/pionowo
     * @param positionVer - pozycja pocz¹tkowego pola pionowo
     * @param positionHor - pozycja pocz¹tkowego pola poziomo
     * @return true jeœli maj¹ jak¹œ zawartoœæ, false jeœli nie
     */
    private boolean checkIfHaveAnyLetter(int size, Board board, int dir, int positionVer, int positionHor) {
        if (dir == BoardCell.ver) {
            for (int i = positionVer; i < positionVer + size; i++) {
                if (board.getCell(positionHor, i).checkContent()) {
                    return true;
                }
            }
        } else {
            for (int i = positionHor; i < positionHor + size; i++) {
                if (board.getCell(i, positionVer).checkContent()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see Strategy#findEntry(Crossword)
     */
    @Override
    public CwEntry findEntry(Crossword crossword) throws FailedToGenerateCrosswordException{
        if (crossword.getDatabase().getSize() == 0) {
            throw new FailedToGenerateCrosswordException("Nie mo¿na wygenerowaæ krzy¿ówki. Baza s³ów jest pusta!");
        }
        Random rand = new Random();
        Board board = crossword.getBoardCopy();
        LinkedList<BoardCell> startingCells = board.getStartCells(); 
        boolean flag = false; // czy znaleziono pasuj¹ce has³o
        CwEntry toReturn = null;
        while (startingCells.size() > 0 && !flag) {
            BoardCell temp;
            if (crossword.getEntriesList().isEmpty()) { 
                if (rand.nextBoolean()) {
                    temp = board.getCell(0, rand.nextInt(board.getHeight()));
                } else {
                    temp = board.getCell(rand.nextInt(board.getWidth()), 0);
                }
            } else {
                temp = startingCells.get(rand.nextInt(startingCells.size())); // losowy pocz¹tek
            }
            if (temp.getAbility(BoardCell.beg, BoardCell.ver)) {
                int size = board.getHeight() - board.getVerPosition(temp);
                while (size > 1 && !flag) {
                    if (checkAbilities(size, board, BoardCell.ver, board.getVerPosition(temp), board.getHorPosition(temp)) && (checkIfHaveAnyLetter(size, board, BoardCell.ver, board.getVerPosition(temp), board.getHorPosition(temp)) || crossword.getEntriesList().isEmpty())) {
                        LinkedList<Entry> matched = crossword.getDatabase().findAll(board.createPattern(board.getHorPosition(temp), board.getVerPosition(temp), board.getHorPosition(temp), board.getVerPosition(temp) + size));
                        while (matched.size() > 0 && !flag) {
                            Entry found = matched.get(rand.nextInt(matched.size()));
                            if (!crossword.contains(found.getWord())) {
                                flag = true;
                                toReturn = new CwEntry(found, board.getHorPosition(temp), board.getVerPosition(temp), CwEntry.Direction.VERT);
                            }
                            matched.remove(found);
                        }
                    }
                    size--;
                }
            }
            if (!flag && temp.getAbility(BoardCell.beg, BoardCell.hor)) {
                int size = board.getWidth() - board.getHorPosition(temp);
                while (size > 1 && !flag) {
                    if (checkAbilities(size, board, BoardCell.hor, board.getVerPosition(temp), board.getHorPosition(temp)) && (checkIfHaveAnyLetter(size, board, BoardCell.hor, board.getVerPosition(temp), board.getHorPosition(temp)) || crossword.getEntriesList().isEmpty())) {
                        LinkedList<Entry> matched = crossword.getDatabase().findAll(board.createPattern(board.getHorPosition(temp), board.getVerPosition(temp), board.getHorPosition(temp) + size, board.getVerPosition(temp)));
                        while (matched.size() > 0 && !flag) {
                            Entry found = matched.get(rand.nextInt(matched.size()));
                            if (!crossword.contains(found.getWord())) {
                                flag = true;
                                toReturn = new CwEntry(found, board.getHorPosition(temp), board.getVerPosition(temp), CwEntry.Direction.HORIZ);
                            }
                            matched.remove(found);
                        }
                    }
                    size--;
                }
            }
            startingCells.remove(temp);
        }
        return toReturn;
    }
    
    /**
     * @see Strategy#updateBoard(Board, CwEntry)
     */
    @Override
    public void updateBoard(Board board, CwEntry entry) { 
        if (entry.getD() == CwEntry.Direction.VERT) {
            if (entry.getY() > 0 && entry.getX() < board.getWidth() - 1) {
                board.getCell(entry.getX() + 1, entry.getY() - 1).disableHorizStart();
            }
            if (entry.getY() > 0) {
                board.getCell(entry.getX(), entry.getY() - 1).disableAll();
            }
            if (entry.getY() + entry.getWord().length() < board.getHeight()) {
                board.getCell(entry.getX(), entry.getY() + entry.getWord().length()).disableAll();
            }
            for (int y = entry.getY(); y < entry.getY() + entry.getWord().length() && y < board.getHeight(); y++) {
                board.getCell(entry.getX(), y).setContent(entry.getWord().charAt(y - entry.getY()));
                board.getCell(entry.getX(), y).disableVert();
            }
            if (entry.getX() > 0) {
                board.getCell(entry.getX() - 1, entry.getY()).disableHorizEnd();
                if (entry.getY() + entry.getWord().length() - 1 < board.getHeight()) {
                    board.getCell(entry.getX() - 1, entry.getY() + entry.getWord().length() - 1).disableHorizEnd();
                }
                for (int y = entry.getY(); y < entry.getY() + entry.getWord().length() && y < board.getHeight(); y++) {
                    board.getCell(entry.getX() - 1, y).disableVert();
                    board.getCell(entry.getX() - 1, y).disableHorizEnd();
                }
            }
            if (entry.getX() + 1 < board.getWidth()) {
                board.getCell(entry.getX() + 1, entry.getY()).disableHorizStart();
                if (entry.getY() + entry.getWord().length() - 1 < board.getHeight()) {
                    board.getCell(entry.getX() + 1, entry.getY() + entry.getWord().length() - 1).disableHorizStart();
                }
                for (int y = entry.getY(); y < entry.getY() + entry.getWord().length() && y < board.getHeight(); y++) {
                    board.getCell(entry.getX() + 1, y).disableVert();
                    board.getCell(entry.getX() + 1, y).disableHorizStart();
                }
            }
        } else {
            if (entry.getY() < board.getHeight() - 1 && entry.getX() > 0) {
                board.getCell(entry.getX() - 1, entry.getY() + 1).disableVertStart();
            }
            if (entry.getX() > 0) {
                board.getCell(entry.getX() - 1, entry.getY()).disableAll();
            }
            if (entry.getX() + entry.getWord().length() < board.getWidth()) {
                board.getCell(entry.getX() + entry.getWord().length(), entry.getY()).disableAll();
            }
            for (int x = entry.getX(); x < entry.getX() + entry.getWord().length() && x < board.getWidth(); x++) {
                board.getCell(x, entry.getY()).setContent(entry.getWord().charAt(x - entry.getX()));
                board.getCell(x, entry.getY()).disableHoriz();
            }
            if (entry.getY() > 0) {
                board.getCell(entry.getX(), entry.getY() - 1).disableVertEnd();
                if (entry.getX() + entry.getWord().length() - 1 < board.getWidth()) {
                    board.getCell(entry.getX() + entry.getWord().length() - 1, entry.getY() - 1).disableVertEnd();
                }
                for (int x = entry.getX(); x < entry.getX() + entry.getWord().length() && x < board.getWidth(); x++) {
                    board.getCell(x, entry.getY() - 1).disableHoriz();
                    board.getCell(x, entry.getY() - 1).disableVertEnd();
                }
            }
            if (entry.getY() + 1 < board.getHeight()) {
                board.getCell(entry.getX(), entry.getY() + 1).disableVertStart();
                if (entry.getX() + entry.getWord().length() - 1 < board.getWidth()) {
                    board.getCell(entry.getX() + entry.getWord().length() - 1, entry.getY() + 1).disableVertStart();
                }
                for (int x = entry.getX(); x < entry.getX() + entry.getWord().length() && x < board.getWidth(); x++) {
                    board.getCell(x, entry.getY() + 1).disableHoriz();
                    board.getCell(x, entry.getY() + 1).disableVertStart();
                }
            }
        }
    }
}

