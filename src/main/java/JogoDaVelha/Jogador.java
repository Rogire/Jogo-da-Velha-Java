package JogoDaVelha;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author igort
 */
public class Jogador extends Tabuleiro
{
    static Scanner teclado = new Scanner(System.in);
    protected static final Jogador player1 = new Jogador();
    protected static final Jogador player2 = new Jogador();
    
    static List<String> jogadas = new ArrayList<>();
    static List<String> jogadasNoJogo = new ArrayList();
    
    private char XB = 'd'; 
    private static int iAt=0;
    
    public char getXB() {
        return XB;
    }

    public void setXB(char xb) {
        XB = xb;
    }
    
    public void fazerJogada(int i, int j)
    {
        int pos = vef(i,j);
        boolean fezJogada = false;
        
        while(!fezJogada)
        {
            if(pos >= 0 )
            {   
                tabuleiro[pos].setVal(XB);

                jogadasNoJogo.add("("+i+","+j+")");
                nj++;
                fezJogada = true;
                Tabuleiro.imprime();
            }
            else
            {
                while(pos == -1)
                {
                    System.out.println("Posicao invalida, a casa já tem valor armazenado");
                    System.out.println("Insira uma posição valida");
                    i = teclado.nextInt();
                    j=teclado.nextInt();
                    pos = vef(i,j);
                }
                while(pos == -2)
                {
                    System.out.println("Posicao fora do tabuleiro");
                    System.out.println("Insira uma posição valida");
                    i = teclado.nextInt();
                    j=teclado.nextInt();
                    pos = vef(i,j);
                }
            }
        }  
    }
    
    public void fazerJogadaBot(int i, int j) //não imprime as mensagens de erro
    {
        int pos = vef(i,j);
        boolean fezJogada = false;
        
        while(!fezJogada)
        {
            if(pos >= 0)
            {   
                tabuleiro[pos].setVal(XB);

                jogadasNoJogo.add("("+i+","+j+")");
                nj++;
                fezJogada = true;
                Tabuleiro.imprime();
            }
            else
            {
                while(pos <0)
                {
                    i = (int)Math.floor(Math.random() * (2 - 0 + 1) + 0);
                    j = (int)Math.floor(Math.random() * (2 - 0 + 1) + 0);
                    pos = vef(i,j);
                }
            }
        }  
    }
    public void fazerJogadaLinBot(int pos) //não imprime as mensagens de erro
    {
        int i=0, j=0, P;
        boolean add=false;
        
        
        
        for(int m=0; m<3;m++)
        {
            i= m;
            for(int n=0;n<3;n++)
            {
                j=n;
                if(m*col+n == pos)
                {
                    jogadasNoJogo.add("("+i+","+j+")");
                    add =true;
                    break;
                }      
            }
            if(add) break;
        }
        
        P = vef(i,j);
        if(P>=0)
        {
            nj++;
            tabuleiro[pos].setVal(XB);
            Tabuleiro.imprime();
        }    
    }
    
    public static void imprimeJogadas()
    {
        System.out.println("Jogadas realizadas:");
        for(String str : jogadasNoJogo)
        {
            System.out.println(str);
        }
        for(int i=iAt; i<numJogos;i++) //roda uma vez (numJogos-iAt=1)
        {
            jogadas.add("---Jogo "+(i+1)+"---");
            for(String str : jogadasNoJogo)
                jogadas.add(str);
            
        }
        jogadasNoJogo.clear();
        iAt++;  
    }
     public static void imprimeTUDO()
    {
        System.out.println("Jogadas realizadas em todos os jogos:");
        for(String str : jogadas)
        {
            System.out.println(str);
        }
    }
}
