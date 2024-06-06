package JogoDaVelha;
import java.util.Scanner;
/**
 *
 * @author igort
 */
public class ComecoDeJogo extends Jogador
{
    private static final Jogador player1 = new Jogador();
    private static final Jogador player2 = new Jogador();
    private static final int[] IJ = {0,0}; //auxiliar para as posições lineariazadas dos bots
    private static int md;
    private static int winP1 = 0;
    private static int winP2 = 0;
    private static int velha = 0;
    private static char jogandoAgora = 'd';
    private static int P1FaltaUm;
    private static int P2FaltaUm;
    
    static Scanner teclado = new Scanner(System.in);
    private static String boasVindas()
    {
        return """
               ==============================================
               ===  Seja bem-vindo ao Jogo da Velha!!!   ===
               ==============================================
               
               Selecione o modo de jogo:
               (1) Jogador Vs Jogador
               (2) Jogador Vs Bot
               """;
    }
    
    private static int SelModo() //TODO: FAZER MODO INFINITO
    {
        int modo;
        modo = teclado.nextInt();
        
        while(modo != 1 && modo != 2)
        {
            System.out.println("Selecione uma opcao valida");
            modo = teclado.nextInt();
        }
        return modo;
    }
    
    private static void SetPlayers()
    {
        System.out.println("Selecione qual jogador deseja ser:(X ou O)");
        boolean v=false, inseriu=false;
        char Jv = teclado.next().charAt(0);
        
        v = Jv == 'X' || Jv == 'O' ? true : false;
        
        while(!inseriu) // roda o loop denovo para quando o caractere for válido
        {
            if(v)
            {           
                player1.setXB(Jv);
                player2.setXB((Jv=='X')?('O'):('X'));      // se o player 0 é X o player 1 recebe O e vice-versa             
                inseriu = true;
            }
            else
            {
                while(!v) // enquanto o caractere não for válido o loop roda denovo
                {
                    System.out.println("Selecione uma opcao valida");
                    Jv = teclado.next().charAt(0);
                    v = Jv == 'X' || Jv == 'O' ? true : false;
                }
            }
        }
            
    }
    private static void SelBot()
    {
        int dif=0;
        System.out.println("Selecione a dificuldade:\n(1)Facil\n(2)Medio\n(3)Dificil");
        dif = teclado.nextInt();
        if(dif == 1)
            VsBotEasy();
        else if(dif == 2)
            VsBotMed();
        else if(dif == 3)
            VsBotHard();
    }
    
    private static void VsBotEasy() //o bot recebe m e n gerados aleatoriamente entre 0 e 2
    {
        int m , n, max =2, min = 0;
        
        while(!Tabuleiro.VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir");
            
            m = teclado.nextInt();
            n = teclado.nextInt();
            player1.fazerJogada(m, n);
            
            if(Tabuleiro.VefWinner())
                break;
            else if(Tabuleiro.nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                Tabuleiro.nj = 0;
                Tabuleiro.numJogos++;
                Jogador.imprimeJogadas();
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            m = (int)Math.floor(Math.random() * (max - min + 1) + min); 
            n = (int)Math.floor(Math.random() * (max - min + 1) + min);
            player2.fazerJogadaBot(m, n);
        }
        vencedor();
    }
    
    private static void VsBotMed()
    {
        int lin , col, max =2, min = 0;
        int MIc = -1 ,NIc=-1;//contador para iterar a posição inicial da coluna e linha
        int MI=-1, NI=-1;    // posições iniciais geradas aleatoriamente de m e n
        
        while(!VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir");
            
            lin = teclado.nextInt();
            col = teclado.nextInt();
            player1.fazerJogada(lin, col);
            
            if(VefWinner())
                break;
            else if(nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                nj = 0;
                numJogos++;
                imprimeJogadas();
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            if(nj == 1) // se é a primeira jogada salva a posição aleatoria inicial
            {
                while(vef(lin,col) == -1)
                {
                    lin = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                    col = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                }

                MI = MIc = lin;
                NI = NIc = col;
                
                player2.fazerJogadaBot(lin, col);
            }
            else
            {
                lin = MI;
                if(NI == 2) //vai andar pra trás na linha
                    col = --NIc;
                if(NI == 0) //vai andar pra frente na linha
                    col = ++NIc;
                
                if(NI == 1) //vai pra frente e depois pra trás
                {
                    if(vef(lin,col+1) != -1)
                        col = ++NIc;
                    else if(vef(lin,col-1) != -1)
                        col = --NIc;
                    else
                    {                // se não tem como colocar na linha ele vai tentar colocar na coluna
                        col = NI;
                        MIc = MI;
                        NIc = NI;

                        if(MI == 2) //vai subir a coluna
                            lin = --MIc;
                        if(MI == 0) //vai descer a coluna
                            lin = ++MIc;

                        if(MI == 1) //vai subir e depois pra descer
                        {
                            if(vef(lin+1,col) != -1)
                                lin = ++MIc;
                            else if(vef(lin-1,col) != -1)
                                lin = --MIc;
                        }
                    }
                }
                if(vef(lin,col) != -1) //se a posição é válida
                {
                    if(faltaUm(player2.getXB()) == -1 && faltaUm(player1.getXB()) != -1) // se o bot não está pra ganhar em uma jogada
                        player2.fazerJogadaLinBot(faltaUm(player1.getXB()));                                // mas o player está
                    else if(faltaUm(player2.getXB()) != -1)
                        player2.fazerJogadaLinBot(faltaUm(player2.getXB()));
                    else
                        player2.fazerJogadaBot(lin, col);
                }
                else
                {
                    while(vef(lin,col) == -1)
                    {
                        lin = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                        col = (int)Math.floor(Math.random() * (max - min + 1) + min);  

                        MI = MIc = lin;
                        NI = NIc = col;
                    }
                     if(faltaUm(player2.getXB()) == -1 && faltaUm(player1.getXB()) != -1)
                        player2.fazerJogadaLinBot(faltaUm(player1.getXB()));                                
                    else if(faltaUm(player2.getXB()) != -1)
                        player2.fazerJogadaLinBot(faltaUm(player2.getXB()));
                    else
                        player2.fazerJogadaBot(lin, col);
                }
                
            } 
        }
        vencedor();
    } 
    
    private static void VsBotHard()
    {
        int [] Quinas = {0,2,6,8};
        int lin , col, max =2, min = 0, aux=0, mx=3,mn=0;
        int MIc = -1 ,NIc=-1;//contador para iterar a posição inicial da coluna e linha
        int MI=-1, NI=-1;    // posições iniciais geradas aleatoriamente de m e n
        boolean ehValido = true;
        
        while(!VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir");
            
            lin = teclado.nextInt();
            col = teclado.nextInt();
            player1.fazerJogada(lin, col);
            
            if(VefWinner())
                break;
            else if(nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                nj = 0;
                numJogos++;
                imprimeJogadas();
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            /*
            while(vef(lin,col)!=-1)
            {
            
            }
            
            */
            if(nj == 1) // se é a primeira jogada do bot
            {
                if(vef(1,1) != -1) // o bot sempre começa pelo meio se tiver liberado
                {
                    lin = 1;
                    col = 1;
                    
                    MI = MIc = lin;
                    NI = NIc = col;
                    player2.fazerJogadaBot(lin, col);
                }
                else
                {
                    
                    aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn); //Pega uma das quinas do tabuleiro
                    MI = MIc = lin;
                    NI = NIc = col;

                    player2.fazerJogadaLinBot(Quinas[aux]);
                }  
            }
            else
            {
                lin = MI;
                if(NI == 2) //vai andar pra trás na linha
                    col = --NIc;
                if(NI == 0) //vai andar pra frente na linha
                    col = ++NIc;
                
                if(NI == 1) //vai pra frente e depois pra trás
                {
                    if(vef(lin,col+1) != -1)
                        col = ++NIc;
                    else if(vef(lin,col-1) != -1)
                        col = --NIc;
                    else
                    {                // se não tem como colocar na linha ele vai tentar colocar na coluna
                        col = NI;
                        MIc = MI;
                        NIc = NI;

                        if(MI == 2) //vai subir a coluna
                            lin = --MIc;
                        if(MI == 0) //vai descer a coluna
                            lin = ++MIc;

                        if(MI == 1) //vai subir e depois pra descer
                        {
                            if(vef(lin+1,col) != -1)
                                lin = ++MIc;
                            else if(vef(lin-1,col) != -1)
                                lin = --MIc;
                        }
                    }
                }
                if(vef(lin,col) != -1) //se a posição é válida
                {
                    P1FaltaUm = faltaUm(player1.getXB());
                    P2FaltaUm = faltaUm(player2.getXB());
                    boolean add = false;
                    
                    if(P2FaltaUm == -1 && P1FaltaUm != -1)
                    {   // se o bot não está pra ganhar em uma jogada mas o player está
                        
                        for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                            {
                                if(i*col+j == P1FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    break;
                                }
                            }
                            if(add) break;
                        }
                        
                        player2.fazerJogadaLinBot(P1FaltaUm);    //bloqueia o jogador 
                            
                    }  
                    else if(P2FaltaUm != -1)//se o bot está pra ganhar
                    {
                        for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                            {
                                if(i*col+j == P2FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    break;
                                }
                            }
                            if(add) break;
                        }
                        
                        player2.fazerJogadaLinBot(P2FaltaUm);//ganha
                    }    
                    else if(!FezQuina())                                     //O jogador não fez uma quina
                    {
                        player2.fazerJogadaBot(lin, col);  
                    }
                    else                                                            //O jogador fez uma quina
                    {
                        aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn); //Pega uma das quinas do tabuleiro
                        deslineariza(Quinas[aux]);
                        
                        while(vef(IJ[0],IJ[1]) == -1)
                        {
                            aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn);
                            deslineariza(Quinas[aux]);
                        }
                        
                        player2.fazerJogadaLinBot(Quinas[aux]);
                    }
                }
                else
                {
                    while(vef(lin,col) == -1)
                    {
                        lin = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                        col = (int)Math.floor(Math.random() * (max - min + 1) + min);  

                        MI = MIc = lin;
                        NI = NIc = col;
                    }
                    
                    P1FaltaUm = faltaUm(player1.getXB());
                    P2FaltaUm = faltaUm(player2.getXB());
                    
                    if(P2FaltaUm == -1 && P1FaltaUm != -1)
                    {// se o bot não está pra ganhar em uma jogada mas o player está
                        boolean add = false;
                        
                        for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                            {
                                if(i*col+j == P1FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    break;
                                }
                            }
                            if(add) break;
                        }
                        
                        player2.fazerJogadaLinBot(P1FaltaUm);//bloqueia o jogador 
                    }  
                    else if(P2FaltaUm != -1)//se o bot está pra ganhar
                    {
                        boolean add = false;
                        
                         for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                            {
                                if(i*col+j == P2FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    break;
                                }
                            }
                            if(add) break;
                        }
                        player2.fazerJogadaLinBot(P2FaltaUm);//ganha
                    }    
                    else if(!FezQuina())                                     //O jogador não fez uma quina
                    {
                        player2.fazerJogadaBot(lin, col);  
                    }
                    else                                                            //O jogador fez uma quina
                    {
                        aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn); //Pega uma das quinas do tabuleiro
                        deslineariza(Quinas[aux]);
                        
                        while(vef(IJ[0],IJ[1]) == -1)
                        {
                            aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn);
                            deslineariza(Quinas[aux]);
                        }
                        
                        player2.fazerJogadaLinBot(Quinas[aux]);
                        
                    }
                }
                
            } 
        }
        vencedor();
    }
    private static void VsPlayer()
    {
        int m , n;
        while(!VefWinner())
        {
            
            
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir");
            
            m = teclado.nextInt();
            n = teclado.nextInt();
            player1.fazerJogada(m, n);
            
            if(VefWinner())
                break;
            else if(nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                nj = 0;
                numJogos++;
                Jogador.imprimeJogadas();
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            System.out.println("Digite a linha e coluna em que deseja inserir");
            
            m = teclado.nextInt();
            n = teclado.nextInt();
            player2.fazerJogada(m, n);
        } 
        vencedor();
    }
    
    private static int faltaUm(char xb) //verifica se falta um pra fechar uma linha/ coluna/ diagonal
    {
        for(int i=0; i<tabuleiro.length; i++)
        {
            if((i == 0) || (i == 3) || (i == 6)) //VEF CASOS LINHAS
            {
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+1].getVal()== ' ' && tabuleiro[i+2].getVal()==xb)
                    return i+1;
                if(tabuleiro[i].getVal() == ' ' && tabuleiro[i+1].getVal()==xb && tabuleiro[i+2].getVal()==xb)
                    return i;
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+1].getVal()==xb && tabuleiro[i+2].getVal()==' ')
                    return i+2;
            }
            if((i == 0) || (i == 1) || (i == 2)) // VEF CASOS COLUNAS
            {
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+3].getVal()== ' ' && tabuleiro[i+6].getVal()==xb)
                    return i+3;
                if(tabuleiro[i].getVal() == ' ' && tabuleiro[i+3].getVal()==xb && tabuleiro[i+6].getVal()==xb)
                    return i;
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+3].getVal()==xb && tabuleiro[i+6].getVal()==' ')
                    return i+6;
            }
            if((i == 0)) //VEF CASOS DIAG P
            {
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+4].getVal()== ' ' && tabuleiro[i+8].getVal()==xb)
                    return i+4;
                if(tabuleiro[i].getVal() == ' ' && tabuleiro[i+4].getVal()==xb && tabuleiro[i+8].getVal()==xb)
                    return i;
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+4].getVal()==xb && tabuleiro[i+8].getVal()==' ')
                    return i+8;
            }
            if((i == 2)) //VEF CASOS DIAG S
            {
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+2].getVal()== ' ' && tabuleiro[i+4].getVal()==xb)
                    return i+2;
                if(tabuleiro[i].getVal() == ' ' && tabuleiro[i+2].getVal()==xb && tabuleiro[i+4].getVal()==xb)
                    return i;
                if(tabuleiro[i].getVal() == xb && tabuleiro[i+2].getVal()==xb && tabuleiro[i+4].getVal()==' ')
                    return i+4;
            }
        }
        return -1;
    }
    
    private static boolean FezQuina()
    {//verifica se o jogador marcou duas casas em sequencia (i,j; i+1,j+1), ou  (i,j; i+1,j-1) e se sim 
        for(int i=0; i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(j<=1 && i<=1)
                    if(tabuleiro[i*col+(j+1)].getVal() != player2.getXB())//verifica se a casa i,j+1 já está marcada pelo bot
                        if(tabuleiro[(i*col+j)].getVal() == player1.getXB() && tabuleiro[((i+1)*col+(j+1))].getVal() == player1.getXB())
                            return true;
    
                if(j>0 && i<=1)
                    if(tabuleiro[i*col+(j-1)].getVal() != player2.getXB())//verifica se a casa i,j-1 já está marcada pelo bot
                        if(tabuleiro[(i*col+j)].getVal() == player1.getXB() && tabuleiro[((i+1)*col+(j-1))].getVal() == player1.getXB())
                            return true;
            }
        }
        return false;
    }
    
    private static void vencedor()
    {
        if(jogandoAgora == player1.getXB())
        {
            nj = 0;
            numJogos++;
            System.out.println("Player 1 venceu");
            winP1++;
            Jogador.imprimeJogadas();
            
            jogarNovamente();
        }
        else if(jogandoAgora == player2.getXB())
        {
            nj = 0;
            numJogos++;
            System.out.println("Player 2 venceu"); 
            winP2++;
            Jogador.imprimeJogadas();
            
            jogarNovamente();
        }
    }
    
    private static void deslineariza(int aux)
    {
        int lin=0,col=0;
        boolean add = false;
        for(int m=0; m<3;m++)
        {   //deslineariza a posição
            lin= m;
            for(int n=0;n<3;n++)
            {
                col=n;
                if(m*col+n == aux)
                {
                    add =true;
                    break;
                }      
            }
            if(add) break;
        } 
        
        IJ[0] = lin;
        IJ[1] = col;
    }
    
    private static void jogarNovamente()
    {
        int escolha;
        System.out.println("Deseja jogar novamente? \n(1)sim\n(2)nao");
        escolha = teclado.nextInt();
        if(escolha == 1)
        {
            limpa();
            
            if(md == 2)
                SelBot();
            else
                VsPlayer();
        }
        else
            return;
    }
    
    public static void inicio()
        {
            iniciaVet();
            
            System.out.println(boasVindas());
            
            md = SelModo();
            SetPlayers();
            
            if(md == 2)
                SelBot();
            else
                VsPlayer();
        }
    public static void Estatisticas()
    {
       System.out.println("==============================================\n" +
"       ===  Estatisticas de Jogo   ===\n" + "==============================================");
       System.out.println("Total de jogos: "+Tabuleiro.numJogos);
       System.out.println("Vitorias do Player1: "+winP1);
       System.out.println("Vitorias do Player2: "+winP2);
       System.out.println("O jogo deu velha "+velha+" vezes");
       imprimeTUDO();
       System.out.println("==============================================");
    }
}
