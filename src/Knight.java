
import java.awt.Color;

public class Knight extends Piece implements PiecesInterface 
{    
    public Knight(Color color, ChessBlock startingPosition)
    {
        super(color, startingPosition);
        pieceName = "knight";
    }

    @Override
    public boolean canAttack() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean inCheck() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
