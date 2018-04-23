import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class ChessBoard extends Screen
{
    public int player;
    public String blockName;
    private boolean isPieceSelected = false;
    private final Color white = Color.WHITE;
    private final Color black = Color.BLACK;
    
    public PlayerWhite pWhite;
    public PlayerBlack pBlack;
    
    private final JFrame frame = createFrame("Chess");
    private final JPanel gui = new JPanel(new BorderLayout(3,3));
    private final JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel theChessBoard;
    private final JLabel message = new JLabel("Chess Champ is ready to play!");
    private static final String wCOLS = "ABCDEFGH";
    private static final String bCOLS = "HGFEDCBA";
    private static final String ROWS = "12345678";
    
    private static ChessBlock[][] board = new ChessBlock[8][8];
    
    private final Piece[] whitePawns = new Pawn[8];
    private final Piece[] whiteRooks = new Rook[2];
    private final Piece[] whiteKnights = new Knight[2];
    private final Piece[] whiteBishops = new Bishop[2];
    private Piece whiteKing;
    private Piece whiteQueen;
    
    private final Piece[] blackPawns = new Pawn[8];
    private final Piece[] blackRooks = new Rook[2];
    private final Piece[] blackKnights = new Knight[2];
    private final Piece[] blackBishops = new Bishop[2];
    private Piece blackKing;
    private Piece blackQueen;
    
    public ChessBoard(int player) 
    {
        this.player = player;
        createGUI(player);
        frame.add(gui);
        frame.setVisible(true);
    }
    
    public final void createGUI(int player)
    {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton newButton;
        JButton saveButton;
        JButton restoreButton;
        JButton resignButton;
        tools.add(newButton = new JButton("New")); // TODO - add functionality!
        newButton.addActionListener(new toolsListener());
        newButton.setActionCommand("New");
        tools.add(saveButton = new JButton("Save")); // TODO - add functionality!
        saveButton.addActionListener(new toolsListener());
        saveButton.setActionCommand("Save");
        tools.add(restoreButton = new JButton("Restore")); // TODO - add functionality!
        restoreButton.addActionListener(new toolsListener());
        restoreButton.setActionCommand("Restore");
        tools.addSeparator();
        tools.add(resignButton = new JButton("Resign")); // TODO - add functionality!
        resignButton.addActionListener(new toolsListener());
        resignButton.setActionCommand("Resign");
        tools.addSeparator();
        tools.add(message);
        

        theChessBoard = new JPanel(new GridLayout(0, 9));
        theChessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(theChessBoard);
        
        setUpBoard(player);
        setUpPieces(player);
    }
    
    public void setUpBoard(int player)
    {    
        if(player == 1)
        {
            Insets buttonMargin = new Insets(0,0,0,0);
            for (int row = 7, actRow = 0; row >= 0; row--, actRow++)
            {
                for (int col = 0; col < chessBoardSquares[row].length; col++)
                {
                    JButton button = new JButton();
                    button.setMargin(buttonMargin);             
                    button.addActionListener(new MovePieceListener());
                    button.setActionCommand("" + Integer.toString(actRow) + 
                                                    Integer.toString(col));
                    // our chess pieces are 64x64 px in size, so we'll
                    // 'fill this in' using a transparent icon..
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                    button.setIcon(icon);
                    if ((col % 2 == 0 && row % 2 == 0) || (col % 2 == 1 && row % 2 == 1))
                    {
                        button.setBackground(Color.WHITE);
                    }
                    else
                    {
                        button.setBackground(Color.GREEN);
                    }
                    chessBoardSquares[col][row] = button;
                }
            }

            for (int row = 0, actRow = 8; row < 8; row++, actRow--)
            {
                for (int col = 0; col < 8; col++)
                {
                    switch (col)
                    {
                        case 0:
                            theChessBoard.add(new JLabel("" + (actRow),
                                    SwingConstants.CENTER));
                        default:
                            theChessBoard.add(chessBoardSquares[col][row]);
                    }
                }
            }

            theChessBoard.add(new JLabel(""));

            for (int letter = 0; letter < 8; letter++)
            {
                theChessBoard.add(
                    new JLabel(wCOLS.substring(letter, letter + 1),
                    SwingConstants.CENTER));
            }
            
        }
        else if(player == 2)
        {    
            Insets buttonMargin = new Insets(0,0,0,0);
            for (int row = 7, actRow = 0; row >= 0; row--, actRow++)
            {
                for (int col = 0; col < chessBoardSquares[row].length; col++)
                {
                    JButton button = new JButton();
                    button.setMargin(buttonMargin);             
                    button.addActionListener(new MovePieceListener());
                    button.setActionCommand("" + Integer.toString(actRow) + 
                                                    Integer.toString(col));
                    // our chess pieces are 64x64 px in size, so we'll
                    // 'fill this in' using a transparent icon..
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                    button.setIcon(icon);
                    if ((col % 2 == 0 && row % 2 == 0) || (col % 2 == 1 && row % 2 == 1))
                    {
                        button.setBackground(Color.WHITE);
                    }
                    else
                    {
                        button.setBackground(Color.GREEN);
                    }
                    chessBoardSquares[col][row] = button;
                }
            }

            for (int row = 0; row < 8; row++)
            {
                for (int col = 0; col < 8; col++)
                {
                    switch (col)
                    {
                        case 0:
                            theChessBoard.add(new JLabel("" + (row+1),
                                    SwingConstants.CENTER));
                        default:
                            theChessBoard.add(chessBoardSquares[col][row]);
                    }
                }
            }

            theChessBoard.add(new JLabel(""));

            for (int letter = 0; letter < 8; letter++)
            {
                theChessBoard.add(
                    new JLabel(bCOLS.substring(letter, letter+1),
                    SwingConstants.CENTER));
            }
        }
    }
    
    public void setUpPieces(int player){
        whitePawns[0] = new Pawn(white, board[0][1]);
        whitePawns[1] = new Pawn(white, board[1][1]);
        whitePawns[2] = new Pawn(white, board[2][1]);
        whitePawns[3] = new Pawn(white, board[3][1]);
        whitePawns[4] = new Pawn(white, board[4][1]);
        whitePawns[5] = new Pawn(white, board[5][1]);
        whitePawns[6] = new Pawn(white, board[6][1]);
        whitePawns[7] = new Pawn(white, board[7][1]);
        whiteRooks[0] = new Rook(white, board[0][0]); 
        whiteRooks[1] = new Rook(white, board[7][0]);
        whiteKnights[0] = new Knight(white, board[1][0]); 
        whiteKnights[1] = new Knight(white, board[6][0]);
        whiteBishops[0] = new Bishop(white, board[2][0]); 
        whiteBishops[1] = new Bishop(white, board[5][0]);
        whiteKing = new King(white, board[3][0]);
        whiteQueen = new Queen(white, board[4][0]);
        
        blackPawns[0] = new Pawn(black, board[0][6]);
        blackPawns[1] = new Pawn(black, board[1][6]);
        blackPawns[2] = new Pawn(black, board[2][6]);
        blackPawns[3] = new Pawn(black, board[3][6]);
        blackPawns[4] = new Pawn(black, board[4][6]);
        blackPawns[5] = new Pawn(black, board[5][6]);
        blackPawns[6] = new Pawn(black, board[6][6]);
        blackPawns[7] = new Pawn(black, board[7][6]);
        blackRooks[0] = new Rook(black, board[0][7]); 
        blackRooks[1] = new Rook(black, board[7][7]);
        blackKnights[0] = new Knight(black, board[1][7]); 
        blackKnights[1] = new Knight(black, board[6][7]);
        blackBishops[0] = new Bishop(black, board[2][7]); 
        blackBishops[1] = new Bishop(black, board[5][7]);
        blackKing = new King(black, board[3][7]);
        blackQueen = new Queen(black, board[4][7]);
        
        if(player == 1){
            board[0][0] = new ChessBlock("A1", whiteRooks[0], chessBoardSquares[0][0]);
            board[1][0] = new ChessBlock("B1", whiteKnights[0], chessBoardSquares[1][0]);
            board[2][0] = new ChessBlock("C1", whiteBishops[0], chessBoardSquares[2][0]);
            board[3][0] = new ChessBlock("D1", whiteKing, chessBoardSquares[3][0]);
            board[4][0] = new ChessBlock("E1", whiteQueen, chessBoardSquares[4][0]);
            board[5][0] = new ChessBlock("F1", whiteBishops[1], chessBoardSquares[5][0]);
            board[6][0] = new ChessBlock("G1", whiteKnights[1], chessBoardSquares[6][0]);
            board[7][0] = new ChessBlock("H1", whiteRooks[1], chessBoardSquares[7][0]);
            
            board[0][1] = new ChessBlock("A2", whitePawns[0], chessBoardSquares[0][1]);
            board[1][1] = new ChessBlock("B2", whitePawns[1], chessBoardSquares[1][1]);
            board[2][1] = new ChessBlock("C2", whitePawns[2], chessBoardSquares[2][1]);
            board[3][1] = new ChessBlock("D2", whitePawns[3], chessBoardSquares[3][1]);
            board[4][1] = new ChessBlock("E2", whitePawns[4], chessBoardSquares[4][1]);
            board[5][1] = new ChessBlock("F2", whitePawns[5], chessBoardSquares[5][1]);
            board[6][1] = new ChessBlock("G2", whitePawns[6], chessBoardSquares[6][1]);
            board[7][1] = new ChessBlock("H2", whitePawns[7], chessBoardSquares[7][1]);
            
            board[0][2] = new ChessBlock("A3", chessBoardSquares[0][2]);
            board[1][2] = new ChessBlock("B3", chessBoardSquares[1][2]);
            board[2][2] = new ChessBlock("C3", chessBoardSquares[2][2]);
            board[3][2] = new ChessBlock("D3", chessBoardSquares[3][2]);
            board[4][2] = new ChessBlock("E3", chessBoardSquares[4][2]);
            board[5][2] = new ChessBlock("F3", chessBoardSquares[5][2]);
            board[6][2] = new ChessBlock("G3", chessBoardSquares[6][2]);
            board[7][2] = new ChessBlock("H3", chessBoardSquares[7][2]);
            
            board[0][3] = new ChessBlock("A4", chessBoardSquares[0][3]);
            board[1][3] = new ChessBlock("B4", chessBoardSquares[1][3]);
            board[2][3] = new ChessBlock("C4", chessBoardSquares[2][3]);
            board[3][3] = new ChessBlock("D4", chessBoardSquares[3][3]);
            board[4][3] = new ChessBlock("E4", chessBoardSquares[4][3]);
            board[5][3] = new ChessBlock("F4", chessBoardSquares[5][3]);
            board[6][3] = new ChessBlock("G4", chessBoardSquares[6][3]);
            board[7][3] = new ChessBlock("H4", chessBoardSquares[7][3]);
            
            board[0][4] = new ChessBlock("A5", chessBoardSquares[0][4]);
            board[1][4] = new ChessBlock("B5", chessBoardSquares[1][4]);
            board[2][4] = new ChessBlock("C5", chessBoardSquares[2][4]);
            board[3][4] = new ChessBlock("D5", chessBoardSquares[3][4]);
            board[4][4] = new ChessBlock("E5", chessBoardSquares[4][4]);
            board[5][4] = new ChessBlock("F5", chessBoardSquares[5][4]);
            board[6][4] = new ChessBlock("G5", chessBoardSquares[6][4]);
            board[7][4] = new ChessBlock("H5", chessBoardSquares[7][4]);
            
            board[0][5] = new ChessBlock("A6", chessBoardSquares[0][5]);
            board[1][5] = new ChessBlock("B6", chessBoardSquares[1][5]);
            board[2][5] = new ChessBlock("C6", chessBoardSquares[2][5]);
            board[3][5] = new ChessBlock("D6", chessBoardSquares[3][5]);
            board[4][5] = new ChessBlock("E6", chessBoardSquares[4][5]);
            board[5][5] = new ChessBlock("F6", chessBoardSquares[5][5]);
            board[6][5] = new ChessBlock("G6", chessBoardSquares[6][5]);
            board[7][5] = new ChessBlock("H6", chessBoardSquares[7][5]);
            
            board[0][6] = new ChessBlock("A7", blackPawns[0], chessBoardSquares[0][6]);
            board[1][6] = new ChessBlock("B7", blackPawns[1], chessBoardSquares[1][6]);
            board[2][6] = new ChessBlock("C7", blackPawns[2], chessBoardSquares[2][6]);
            board[3][6] = new ChessBlock("D7", blackPawns[3], chessBoardSquares[3][6]);
            board[4][6] = new ChessBlock("E7", blackPawns[4], chessBoardSquares[4][6]);
            board[5][6] = new ChessBlock("F7", blackPawns[5], chessBoardSquares[5][6]);
            board[6][6] = new ChessBlock("G7", blackPawns[6], chessBoardSquares[6][6]);
            board[7][6] = new ChessBlock("H7", blackPawns[7], chessBoardSquares[7][6]);
            
            board[0][7] = new ChessBlock("A8", blackRooks[0], chessBoardSquares[0][7]);
            board[1][7] = new ChessBlock("B8", blackKnights[0], chessBoardSquares[1][7]);
            board[2][7] = new ChessBlock("C8", blackBishops[0], chessBoardSquares[2][7]);
            board[3][7] = new ChessBlock("D8", blackKing, chessBoardSquares[3][7]);
            board[4][7] = new ChessBlock("E8", blackQueen, chessBoardSquares[4][7]);
            board[5][7] = new ChessBlock("F8", blackBishops[1], chessBoardSquares[5][7]);
            board[6][7] = new ChessBlock("G8", blackKnights[1], chessBoardSquares[6][7]);
            board[7][7] = new ChessBlock("H8", blackRooks[1], chessBoardSquares[7][7]);
            
        }else if(player == 2){
            
            board[0][0] = new ChessBlock("A1", whiteRooks[0], chessBoardSquares[7][7]);
            board[1][0] = new ChessBlock("B1", whiteKnights[0], chessBoardSquares[6][7]);
            board[2][0] = new ChessBlock("C1", whiteBishops[0], chessBoardSquares[5][7]);
            board[3][0] = new ChessBlock("D1", whiteKing, chessBoardSquares[4][7]);
            board[4][0] = new ChessBlock("E1", whiteQueen, chessBoardSquares[3][7]);
            board[5][0] = new ChessBlock("F1", whiteBishops[1], chessBoardSquares[2][7]);
            board[6][0] = new ChessBlock("G1", whiteKnights[1], chessBoardSquares[1][7]);
            board[7][0] = new ChessBlock("H1", whiteRooks[1], chessBoardSquares[0][7]);
            
            board[0][1] = new ChessBlock("A2", whitePawns[0], chessBoardSquares[7][6]);
            board[1][1] = new ChessBlock("B2", whitePawns[1], chessBoardSquares[6][6]);
            board[2][1] = new ChessBlock("C2", whitePawns[2], chessBoardSquares[5][6]);
            board[3][1] = new ChessBlock("D2", whitePawns[3], chessBoardSquares[4][6]);
            board[4][1] = new ChessBlock("E2", whitePawns[4], chessBoardSquares[3][6]);
            board[5][1] = new ChessBlock("F2", whitePawns[5], chessBoardSquares[2][6]);
            board[6][1] = new ChessBlock("G2", whitePawns[6], chessBoardSquares[1][6]);
            board[7][1] = new ChessBlock("H2", whitePawns[7], chessBoardSquares[0][6]);
            
            board[0][2] = new ChessBlock("A3", chessBoardSquares[7][2]);
            board[1][2] = new ChessBlock("B3", chessBoardSquares[6][2]);
            board[2][2] = new ChessBlock("C3", chessBoardSquares[5][2]);
            board[3][2] = new ChessBlock("D3", chessBoardSquares[4][2]);
            board[4][2] = new ChessBlock("E3", chessBoardSquares[3][2]);
            board[5][2] = new ChessBlock("F3", chessBoardSquares[2][2]);
            board[6][2] = new ChessBlock("G3", chessBoardSquares[1][2]);
            board[7][2] = new ChessBlock("H3", chessBoardSquares[0][2]);
            
            board[0][3] = new ChessBlock("A4", chessBoardSquares[7][3]);
            board[1][3] = new ChessBlock("B4", chessBoardSquares[6][3]);
            board[2][3] = new ChessBlock("C4", chessBoardSquares[5][3]);
            board[3][3] = new ChessBlock("D4", chessBoardSquares[4][3]);
            board[4][3] = new ChessBlock("E4", chessBoardSquares[3][3]);
            board[5][3] = new ChessBlock("F4", chessBoardSquares[2][3]);
            board[6][3] = new ChessBlock("G4", chessBoardSquares[1][3]);
            board[7][3] = new ChessBlock("H4", chessBoardSquares[0][3]);
            
            board[0][4] = new ChessBlock("A5", chessBoardSquares[7][4]);
            board[1][4] = new ChessBlock("B5", chessBoardSquares[6][4]);
            board[2][4] = new ChessBlock("C5", chessBoardSquares[5][4]);
            board[3][4] = new ChessBlock("D5", chessBoardSquares[4][4]);
            board[4][4] = new ChessBlock("E5", chessBoardSquares[3][4]);
            board[5][4] = new ChessBlock("F5", chessBoardSquares[2][4]);
            board[6][4] = new ChessBlock("G5", chessBoardSquares[1][4]);
            board[7][4] = new ChessBlock("H5", chessBoardSquares[0][4]);
            
            board[0][5] = new ChessBlock("A6", chessBoardSquares[7][5]);
            board[1][5] = new ChessBlock("B6", chessBoardSquares[6][5]);
            board[2][5] = new ChessBlock("C6", chessBoardSquares[5][5]);
            board[3][5] = new ChessBlock("D6", chessBoardSquares[4][5]);
            board[4][5] = new ChessBlock("E6", chessBoardSquares[3][5]);
            board[5][5] = new ChessBlock("F6", chessBoardSquares[2][5]);
            board[6][5] = new ChessBlock("G6", chessBoardSquares[1][5]);
            board[7][5] = new ChessBlock("H6", chessBoardSquares[0][5]);

            board[0][6] = new ChessBlock("A7", blackPawns[0], chessBoardSquares[7][1]);
            board[1][6] = new ChessBlock("B7", blackPawns[1], chessBoardSquares[6][1]);
            board[2][6] = new ChessBlock("C7", blackPawns[2], chessBoardSquares[5][1]);
            board[3][6] = new ChessBlock("D7", blackPawns[3], chessBoardSquares[4][1]);
            board[4][6] = new ChessBlock("E7", blackPawns[4], chessBoardSquares[3][1]);
            board[5][6] = new ChessBlock("F7", blackPawns[5], chessBoardSquares[2][1]);
            board[6][6] = new ChessBlock("G7", blackPawns[6], chessBoardSquares[1][1]);
            board[7][6] = new ChessBlock("H7", blackPawns[7], chessBoardSquares[0][1]);
            
            board[0][7] = new ChessBlock("A8", blackRooks[0], chessBoardSquares[7][0]);
            board[1][7] = new ChessBlock("B8", blackKnights[0], chessBoardSquares[6][0]);
            board[2][7] = new ChessBlock("C8", blackBishops[0], chessBoardSquares[5][0]);
            board[3][7] = new ChessBlock("D8", blackKing, chessBoardSquares[4][0]);
            board[4][7] = new ChessBlock("E8", blackQueen, chessBoardSquares[3][0]);
            board[5][7] = new ChessBlock("F8", blackBishops[1], chessBoardSquares[2][0]);
            board[6][7] = new ChessBlock("G8", blackKnights[1], chessBoardSquares[1][0]);
            board[7][7] = new ChessBlock("H8", blackRooks[0], chessBoardSquares[0][0]);
        }
    }
    
    public class toolsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e){
            //new save restore resign
            if("new".equals(e.getActionCommand())){
                Game.newGame();
                frame.setVisible(false);
            }else if("save".equals(e.getActionCommand())){
                
            }else if("restore".equals(e.getActionCommand())){
                
            }else if("resign".equals(e.getActionCommand())){
                
            }
        }
        
    }
    
    private void pieceClicked(ChessBlock chessBlock)
    {
        if (chessBlock.hasPiece)
        {
                System.out.println(chessBlock.getBlockDescription() + " has a " + chessBlock.getPiece().getColor() + " " + chessBlock.getPiece().getPieceName());
        }
        else
        {
            System.out.println(chessBlock.getBlockDescription() + " is empty");
        }
    }
    
    public class MovePieceListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (player == 1){
                if("00".equals(e.getActionCommand())){
                    pieceClicked(board[0][0]);
                }else if("01".equals(e.getActionCommand())){
                    pieceClicked(board[1][0]);
                }else if("02".equals(e.getActionCommand())){
                    pieceClicked(board[2][0]);
                }else if("03".equals(e.getActionCommand())){
                    pieceClicked(board[3][0]);
                }else if("04".equals(e.getActionCommand())){
                    pieceClicked(board[4][0]);
                }else if("05".equals(e.getActionCommand())){
                    pieceClicked(board[5][0]);
                }else if("06".equals(e.getActionCommand())){
                    pieceClicked(board[6][0]);
                }else if("07".equals(e.getActionCommand())){
                    pieceClicked(board[7][0]);
                }else if("10".equals(e.getActionCommand())){
                    pieceClicked(board[0][1]);
                }else if("11".equals(e.getActionCommand())){
                    pieceClicked(board[1][1]);
                }else if("12".equals(e.getActionCommand())){
                    pieceClicked(board[2][1]);
                }else if("13".equals(e.getActionCommand())){
                    pieceClicked(board[3][1]);
                }else if("14".equals(e.getActionCommand())){
                    pieceClicked(board[4][1]);
                }else if("15".equals(e.getActionCommand())){
                    pieceClicked(board[5][1]);
                }else if("16".equals(e.getActionCommand())){
                    pieceClicked(board[6][1]);
                }else if("17".equals(e.getActionCommand())){
                    pieceClicked(board[7][1]);
                }else if("20".equals(e.getActionCommand())){
                    pieceClicked(board[0][2]);
                }else if("21".equals(e.getActionCommand())){
                    pieceClicked(board[1][2]);
                }else if("22".equals(e.getActionCommand())){
                    pieceClicked(board[2][2]);
                }else if("23".equals(e.getActionCommand())){
                    pieceClicked(board[3][2]);
                }else if("24".equals(e.getActionCommand())){
                    pieceClicked(board[4][2]);
                }else if("25".equals(e.getActionCommand())){
                    pieceClicked(board[5][2]);
                }else if("26".equals(e.getActionCommand())){
                    pieceClicked(board[6][2]);
                }else if("27".equals(e.getActionCommand())){
                    pieceClicked(board[7][2]);
                }else if("30".equals(e.getActionCommand())){
                    pieceClicked(board[0][3]);
                }else if("31".equals(e.getActionCommand())){
                    pieceClicked(board[1][3]);
                }else if("32".equals(e.getActionCommand())){
                    pieceClicked(board[2][3]);
                }else if("33".equals(e.getActionCommand())){
                    pieceClicked(board[3][3]);
                }else if("34".equals(e.getActionCommand())){
                    pieceClicked(board[4][3]);
                }else if("35".equals(e.getActionCommand())){
                    pieceClicked(board[5][3]);
                }else if("36".equals(e.getActionCommand())){
                    pieceClicked(board[6][3]);
                }else if("37".equals(e.getActionCommand())){
                    pieceClicked(board[7][3]);
                }else if("40".equals(e.getActionCommand())){
                    pieceClicked(board[0][4]);
                }else if("41".equals(e.getActionCommand())){
                    pieceClicked(board[1][4]);
                }else if("42".equals(e.getActionCommand())){
                    pieceClicked(board[2][4]);
                }else if("43".equals(e.getActionCommand())){
                    pieceClicked(board[3][4]);
                }else if("44".equals(e.getActionCommand())){
                    pieceClicked(board[4][4]);
                }else if("45".equals(e.getActionCommand())){
                    pieceClicked(board[5][4]);
                }else if("46".equals(e.getActionCommand())){
                    pieceClicked(board[6][4]);
                }else if("47".equals(e.getActionCommand())){
                    pieceClicked(board[7][4]);
                }else if("50".equals(e.getActionCommand())){
                    pieceClicked(board[0][5]);
                }else if("51".equals(e.getActionCommand())){
                    pieceClicked(board[1][5]);
                }else if("52".equals(e.getActionCommand())){
                    pieceClicked(board[2][5]);
                }else if("53".equals(e.getActionCommand())){
                    pieceClicked(board[3][5]);
                }else if("54".equals(e.getActionCommand())){
                    pieceClicked(board[4][5]);
                }else if("55".equals(e.getActionCommand())){
                    pieceClicked(board[5][5]);
                }else if("56".equals(e.getActionCommand())){
                    pieceClicked(board[6][5]);
                }else if("57".equals(e.getActionCommand())){
                    pieceClicked(board[7][5]);
                }else if("60".equals(e.getActionCommand())){
                    pieceClicked(board[0][6]);
                }else if("61".equals(e.getActionCommand())){
                    pieceClicked(board[1][6]);
                }else if("62".equals(e.getActionCommand())){
                    pieceClicked(board[2][6]);
                }else if("63".equals(e.getActionCommand())){
                    pieceClicked(board[3][6]);
                }else if("64".equals(e.getActionCommand())){
                    pieceClicked(board[4][6]);
                }else if("65".equals(e.getActionCommand())){
                    pieceClicked(board[5][6]);
                }else if("66".equals(e.getActionCommand())){
                    pieceClicked(board[6][6]);
                }else if("67".equals(e.getActionCommand())){
                    pieceClicked(board[7][6]);
                }else if("70".equals(e.getActionCommand())){
                    pieceClicked(board[0][7]);
                }else if("71".equals(e.getActionCommand())){
                    pieceClicked(board[1][7]);
                }else if("72".equals(e.getActionCommand())){
                    pieceClicked(board[2][7]);
                }else if("73".equals(e.getActionCommand())){
                    pieceClicked(board[3][7]);
                }else if("74".equals(e.getActionCommand())){
                    pieceClicked(board[4][7]);
                }else if("75".equals(e.getActionCommand())){
                    pieceClicked(board[5][7]);
                }else if("76".equals(e.getActionCommand())){
                    pieceClicked(board[6][7]);
                }else if("77".equals(e.getActionCommand())){
                    pieceClicked(board[7][7]);
                }
            }else if (player == 2){
                
                if("77".equals(e.getActionCommand())){
                    pieceClicked(board[0][0]);
                }else if("76".equals(e.getActionCommand())){
                    pieceClicked(board[1][0]);
                }else if("75".equals(e.getActionCommand())){
                    pieceClicked(board[2][0]);
                }else if("74".equals(e.getActionCommand())){
                    pieceClicked(board[3][0]);
                }else if("73".equals(e.getActionCommand())){
                    pieceClicked(board[4][0]);
                }else if("72".equals(e.getActionCommand())){
                    pieceClicked(board[5][0]);
                }else if("71".equals(e.getActionCommand())){
                    pieceClicked(board[6][0]);
                }else if("70".equals(e.getActionCommand())){
                    pieceClicked(board[7][0]);
                }else if("67".equals(e.getActionCommand())){
                    pieceClicked(board[0][1]);
                }else if("66".equals(e.getActionCommand())){
                    pieceClicked(board[1][1]);
                }else if("65".equals(e.getActionCommand())){
                    pieceClicked(board[2][1]);
                }else if("64".equals(e.getActionCommand())){
                    pieceClicked(board[3][1]);
                }else if("63".equals(e.getActionCommand())){
                    pieceClicked(board[4][1]);
                }else if("62".equals(e.getActionCommand())){
                    pieceClicked(board[5][1]);
                }else if("61".equals(e.getActionCommand())){
                    pieceClicked(board[6][1]);
                }else if("60".equals(e.getActionCommand())){
                    pieceClicked(board[7][1]);
                }else if("57".equals(e.getActionCommand())){
                    pieceClicked(board[0][2]);
                }else if("56".equals(e.getActionCommand())){
                    pieceClicked(board[1][2]);
                }else if("55".equals(e.getActionCommand())){
                    pieceClicked(board[2][2]);
                }else if("54".equals(e.getActionCommand())){
                    pieceClicked(board[3][2]);
                }else if("53".equals(e.getActionCommand())){
                    pieceClicked(board[4][2]);
                }else if("52".equals(e.getActionCommand())){
                    pieceClicked(board[5][2]);
                }else if("51".equals(e.getActionCommand())){
                    pieceClicked(board[6][2]);
                }else if("50".equals(e.getActionCommand())){
                    pieceClicked(board[7][2]);
                }else if("47".equals(e.getActionCommand())){
                    pieceClicked(board[0][3]);
                }else if("46".equals(e.getActionCommand())){
                    pieceClicked(board[1][3]);
                }else if("45".equals(e.getActionCommand())){
                    pieceClicked(board[2][3]);
                }else if("44".equals(e.getActionCommand())){
                    pieceClicked(board[3][3]);
                }else if("43".equals(e.getActionCommand())){
                    pieceClicked(board[4][3]);
                }else if("42".equals(e.getActionCommand())){
                    pieceClicked(board[5][3]);
                }else if("41".equals(e.getActionCommand())){
                    pieceClicked(board[6][3]);
                }else if("40".equals(e.getActionCommand())){
                    pieceClicked(board[7][3]);
                }else if("37".equals(e.getActionCommand())){
                    pieceClicked(board[0][4]);
                }else if("36".equals(e.getActionCommand())){
                    pieceClicked(board[1][4]);
                }else if("35".equals(e.getActionCommand())){
                    pieceClicked(board[2][4]);
                }else if("34".equals(e.getActionCommand())){
                    pieceClicked(board[3][4]);
                }else if("33".equals(e.getActionCommand())){
                    pieceClicked(board[4][4]);
                }else if("32".equals(e.getActionCommand())){
                    pieceClicked(board[5][4]);
                }else if("31".equals(e.getActionCommand())){
                    pieceClicked(board[6][4]);
                }else if("30".equals(e.getActionCommand())){
                    pieceClicked(board[7][4]);
                }else if("27".equals(e.getActionCommand())){
                    pieceClicked(board[0][5]);
                }else if("26".equals(e.getActionCommand())){
                    pieceClicked(board[1][5]);
                }else if("25".equals(e.getActionCommand())){
                    pieceClicked(board[2][5]);
                }else if("24".equals(e.getActionCommand())){
                    pieceClicked(board[3][5]);
                }else if("23".equals(e.getActionCommand())){
                    pieceClicked(board[4][5]);
                }else if("22".equals(e.getActionCommand())){
                    pieceClicked(board[5][5]);
                }else if("21".equals(e.getActionCommand())){
                    pieceClicked(board[6][5]);
                }else if("20".equals(e.getActionCommand())){
                    pieceClicked(board[7][5]);
                }else if("17".equals(e.getActionCommand())){
                    pieceClicked(board[0][6]);
                }else if("16".equals(e.getActionCommand())){
                    pieceClicked(board[1][6]);
                }else if("15".equals(e.getActionCommand())){
                    pieceClicked(board[2][6]);
                }else if("14".equals(e.getActionCommand())){
                    pieceClicked(board[3][6]);
                }else if("13".equals(e.getActionCommand())){
                    pieceClicked(board[4][6]);
                }else if("12".equals(e.getActionCommand())){
                    pieceClicked(board[5][6]);
                }else if("11".equals(e.getActionCommand())){
                    pieceClicked(board[6][6]);
                }else if("10".equals(e.getActionCommand())){
                    pieceClicked(board[7][6]);
                }else if("07".equals(e.getActionCommand())){
                    pieceClicked(board[0][7]);
                }else if("06".equals(e.getActionCommand())){
                    pieceClicked(board[1][7]);
                }else if("05".equals(e.getActionCommand())){
                    pieceClicked(board[2][7]);
                }else if("04".equals(e.getActionCommand())){
                    pieceClicked(board[3][7]);
                }else if("03".equals(e.getActionCommand())){
                    pieceClicked(board[4][7]);
                }else if("02".equals(e.getActionCommand())){
                    pieceClicked(board[5][7]);
                }else if("01".equals(e.getActionCommand())){
                    pieceClicked(board[6][7]);
                }else if("00".equals(e.getActionCommand())){
                    pieceClicked(board[7][7]);
                }
            }
        }
    }
}
