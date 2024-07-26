package JogoDaVelha;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Jogador
{
    private static final Scanner teclado = new Scanner(System.in);
    private static final Jogador player1 = new Jogador();
    private static final Jogador player2 = new Jogador();
    private static int col = 3;
    
    private static List<String> jogadas = new ArrayList<>();
    private static List<String> jogadasNoJogo = new ArrayList();
    
    private char XB = 'd'; 
    private static int iAt=0;
    
    protected char getXB() {
        return XB;
    }

    protected void setXB(char xb) {
        XB = xb;
    }
    
    protected void fazerJogada(int i, int j,String jogada)
    {
        int pos = Tabuleiro.vef(i,j);
        boolean fezJogada = false;
        
        while(!fezJogada)
        {
            if(pos >= 0 )
            {   
                Tabuleiro.tabuleiro[pos].setVal(XB);

                jogadasNoJogo.add(jogada);
                Tabuleiro.setNj(Tabuleiro.getNj()+1);
                fezJogada = true;
                Tabuleiro.imprime();
            }
            else
            {
                while(pos == -1)
                {
                    System.out.println("Posicao invalida, a casa ja tem valor armazenado");
                    System.out.println("Insira uma posicao valida");
                    i = teclado.nextInt();
                    j=teclado.nextInt();
                    pos = Tabuleiro.vef(i,j);
                }
                while(pos == -2)
                {
                    System.out.println("Posicao fora do tabuleiro");
                    System.out.println("Insira uma posicao valida");
                    i = teclado.nextInt();
                    j=teclado.nextInt();
                    pos = Tabuleiro.vef(i,j);
                }
            }
        }  
    }
    
    protected void fazerJogadaBot(int i, int j) //não imprime as mensagens de erro
    {
        int pos = Tabuleiro.vef(i,j);
        boolean fezJogada = false;
        
        while(!fezJogada)
        {
            if(pos >= 0)
            {   
                Tabuleiro.tabuleiro[pos].setVal(XB);

                jogadasNoJogo.add("("+i+","+j+")");
                Tabuleiro.setNj(Tabuleiro.getNj()+1);
                fezJogada = true;
                Tabuleiro.imprime();
            }
            else
            {
                while(pos <0)
                {
                    i = (int)Math.floor(Math.random() * (2 - 0 + 1) + 0);
                    j = (int)Math.floor(Math.random() * (2 - 0 + 1) + 0);
                    pos = Tabuleiro.vef(i,j);
                }
            }
        }  
    }
    protected void fazerJogadaLinBot(int pos) //não imprime as mensagens de erro
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
        
        P = Tabuleiro.vef(i,j);
        if(P>=0)
        {
            Tabuleiro.setNj(Tabuleiro.getNj()+1);
            Tabuleiro.tabuleiro[pos].setVal(XB);
            Tabuleiro.imprime();
        }    
    }
    
    protected static void imprimeJogadas(int vencedor)
    {
        System.out.println("Jogadas realizadas:");
        for(String str : jogadasNoJogo)
        {
            System.out.println(str);
        }
        for(int i=iAt; i<Tabuleiro.getNumJogos();i++) //roda uma vez (numJogos-iAt=1)
        {
            jogadas.add("---Jogo "+(i+1)+"---");
            if(vencedor != 0 && vencedor != 3)
                jogadas.add("---Vencedor: Player"+(vencedor)+"---");
            else if(vencedor == 3)
                jogadas.add("---Vencedor: Bot---");
            else
                jogadas.add("---O jogo deu velha---");
            
            for(String str : jogadasNoJogo)//adiciona as jogadas da partida atual na array das jogadas totais
                jogadas.add(str);
            
        }
        jogadasNoJogo.clear(); // limpa o vetor das jogadas atuais
        iAt++;  
    }
    protected static void imprimeTUDO()
    {
        System.out.println("Jogadas realizadas em todos os jogos:");
        for(String str : jogadas)
            System.out.println(str);
    }
}
