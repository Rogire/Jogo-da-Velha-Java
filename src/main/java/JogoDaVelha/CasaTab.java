package JogoDaVelha;

/**
 *
 * @author igort
 */
 public class CasaTab // cada casa do tabuleiro
{
    protected char val = ' '; // define o valor X ou O
    
    public char getVal() {
        return val;
    }

    public void setVal(char v) {
        val = v;
    }
}
