package JogoDaVelha;

public class Tabuleiro extends CasaTab
{
    protected static CasaTab[] tabuleiro = new CasaTab[9]; // vetor linearizado das casas no tabuleiro
    protected static final int col = 3;
    protected static final int lin = 3;
    protected static int nj = 0;
    protected static int numJogos=0;
    
    
    protected static void iniciaVet()
    {
        for(int i=0; i<tabuleiro.length; i++)
            tabuleiro[i] = new CasaTab();
    }
    
    protected static int vef(int i, int j) 
    {
        if((i>=0 && i<lin) && (j>=0 && j<col)) //verifica se a posição passada está dentro dos limites
        {   
            if(tabuleiro[i*col+j].getVal() == ' ') // verifica se a casa já tem algum valor armazenado (X ou O)
            {
                return i*col+j; // retorna a posição no vetor linearizado
            } 
            else
            {
                return -1; // já tem valor armazenado
            }
        }
        else
            return -2; // fora do tabuleiro
    }
    
    protected static int vefLin(int pos)
    {
        System.out.println(pos);
        
        if(pos >=0 && pos <=8)
        {
            if(tabuleiro[pos].getVal() == ' ')
                return pos;
            else 
                return -1;
        }
        else 
            return -2;
    }
    protected static boolean VefWinner() //verifica se há vencedor em qualquer uma das linhas e colunas em O(1)
    {

        if((tabuleiro[0].getVal() == tabuleiro[1].getVal()) && (tabuleiro[0].getVal() ==  tabuleiro[2].getVal()) && tabuleiro[0].getVal() != ' ')
            return true;
        if((tabuleiro[3].getVal() == tabuleiro[4].getVal()) && (tabuleiro[3].getVal() ==  tabuleiro[5].getVal()) && tabuleiro[3].getVal() != ' ')
            return true;
        if((tabuleiro[6].getVal() == tabuleiro[7].getVal()) && (tabuleiro[6].getVal() ==  tabuleiro[8].getVal()) && tabuleiro[6].getVal() != ' ')
            return true;
            
        if(((tabuleiro[0].getVal() == tabuleiro[3].getVal()) && (tabuleiro[0].getVal() ==  tabuleiro[6].getVal())) && tabuleiro[0].getVal() != ' ')
            return true;
        if((tabuleiro[1].getVal() == tabuleiro[4].getVal()) && (tabuleiro[1].getVal() ==  tabuleiro[7].getVal()) && tabuleiro[1].getVal() != ' ')
            return true;
        if((tabuleiro[2].getVal() == tabuleiro[5].getVal()) && (tabuleiro[2].getVal() ==  tabuleiro[8].getVal()) && tabuleiro[2].getVal() != ' ')
            return true;
        
        if((tabuleiro[0].getVal() == tabuleiro[4].getVal()) && (tabuleiro[0].getVal() ==  tabuleiro[8].getVal()) && tabuleiro[0].getVal() != ' ')
            return true;
        if((tabuleiro[2].getVal() == tabuleiro[4].getVal()) && (tabuleiro[2].getVal() ==  tabuleiro[6].getVal()) && tabuleiro[2].getVal() != ' ')
            return true;
        
        
        
        return false;
    }
    
    protected void imprime() 
    {   
        String meio = "-----------";
        String l1 = tabuleiro[0].getVal() + " | " + tabuleiro[1].getVal() + " | " + tabuleiro[2].getVal();
        String l2 = tabuleiro[3].getVal() + " | " + tabuleiro[4].getVal() + " | " + tabuleiro[5].getVal();
        String l3 = tabuleiro[6].getVal() + " | " + tabuleiro[7].getVal() + " | " + tabuleiro[8].getVal();
        
        System.out.println(l1+"\n"+meio+"\n"+l2+"\n"+meio+"\n"+l3);
    }
    protected static void limpa()
    {
        for(CasaTab str : tabuleiro)
        {
            str.setVal(' ');
        }
    }
}
