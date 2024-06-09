package JogoDaVelha;

 public class CasaTab // cada casa do tabuleiro
{
    protected char val = ' '; // define o valor X ou O
    
    protected char getVal() {
        return val;
    }

    protected void setVal(char v) {
        val = v;
    }
}
