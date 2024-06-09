package JogoDaVelha;
import java.util.Scanner;

public class ComecoDeJogo
{
    Scanner teclado = new Scanner(System.in);
    private final Jogador player1 = new Jogador();
    private final Jogador player2 = new Jogador();
    private int md;
    private int winP1 = 0;
    private int winP2 = 0;
    private int velha = 0;
    private char jogandoAgora = 'd';
    private int P1FaltaUm;
    private int P2FaltaUm;
    
    private String boasVindas()
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
    
    private int SelModo() 
    {
        int modo=0;
        try
        {
            modo = teclado.nextInt();
        }
        catch(java.util.InputMismatchException t)
        {
            System.out.println("Erro: Valor invalido digitado");
            return -1;
        }
        
        while(modo != 1 && modo != 2)
        {
            System.out.println("Selecione uma opcao valida");
            try{
                modo = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                return -1;
            }
        }
        return modo;
    }
    
    private void SetPlayers()
    {
        System.out.println("Selecione qual jogador deseja ser:(X ou O)");
        boolean v=false, inseriu=false;
        char Jv=' ';
        
        try{
            Jv = teclado.next().charAt(0);
        }
        catch(java.util.InputMismatchException t)
        {
            System.out.println("Erro: Valor invalido digitado");
            return;
        }
        
        
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
                    try
                    {
                        Jv = teclado.next().charAt(0);
                    }
                    catch(java.util.InputMismatchException t)
                    {
                        System.out.println("Erro: Valor invalido digitado");
                        return;
                    }
                    
                    v = Jv == 'X' || Jv == 'O' ? true : false;
                }
            }
        }
            
    }
    private void SelBot()
    {
        int dif=0;
        System.out.println("Selecione a dificuldade:\n(1)Facil\n(2)Medio\n(3)Dificil");
        try
        {
            dif = teclado.nextInt();
        }
        catch(java.util.InputMismatchException t)
        {
            System.out.println("Erro: Valor invalido digitado");
            return;
        }
        
        if(dif == 1)
            VsBotEasy();
        else if(dif == 2)
            VsBotMed();
        else if(dif == 3)
            VsBotHard();
    }
    
    private void VsBotEasy() //o bot recebe m e n gerados aleatoriamente entre 0 e 2
    {   //gera posições aleatórias dentro dos limites do tabuleiro, esse bot perde facilmente
        int m=0 , n=0, max =2, min = 0;
        
        while(!Tabuleiro.VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir (valores de 0 a 2)");
            
            try
            {
                m = teclado.nextInt();
                n = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                return;
            }

            player1.fazerJogada(m, n);
            
            if(Tabuleiro.VefWinner())
                break;
            else if(Tabuleiro.nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                Tabuleiro.nj = 0;
                Tabuleiro.numJogos++;
                Jogador.imprimeJogadas(0);
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            m = (int)Math.floor(Math.random() * (max - min + 1) + min); 
            n = (int)Math.floor(Math.random() * (max - min + 1) + min);
            player2.fazerJogadaBot(m, n);
        }
        vencedor(true);
    }
    
    private void VsBotMed()
    {   //o bot gera sua posição inicial a partir de uma posição aleatória e tenta apartir dela completar 
        //a linha, e caso não seja possível tenta a coluna, se o jogador estiver para ganhar ele bloqueia
        //se o bot estiver para ganhar ele ganha, esse bot perde caso o jogador faça uma quina
        
        int lin=0 , col=0, max =2, min = 0;
        int MIc = -1 ,NIc=-1;//contador para iterar a posição inicial da coluna e linha
        int MI=-1, NI=-1;    // posições iniciais geradas aleatoriamente de m e n
        
        while(!Tabuleiro.VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir (valores de 0 a 2)");
            
            
            try
            {
                lin = teclado.nextInt();
                col = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                System.exit(-1);
            }
            
            player1.fazerJogada(lin, col);
            
            if(Tabuleiro.VefWinner())
                break;
            else if(Tabuleiro.nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                Tabuleiro.nj = 0;
                Tabuleiro.numJogos++;
                Jogador.imprimeJogadas(0);
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            if(Tabuleiro.nj == 1) // se é a primeira jogada salva a posição aleatoria inicial
            {
                while(Tabuleiro.vef(lin,col) == -1)
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
                    if(Tabuleiro.vef(lin,col+1) != -1)
                        col = ++NIc;
                    else if(Tabuleiro.vef(lin,col-1) != -1)
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
                            if(Tabuleiro.vef(lin+1,col) != -1)
                                lin = ++MIc;
                            else if(Tabuleiro.vef(lin-1,col) != -1)
                                lin = --MIc;
                        }
                    }
                }
                if(Tabuleiro.vef(lin,col) != -1) //se a posição é válida
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
                    while(Tabuleiro.vef(lin,col) == -1)
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
        vencedor(true);
    } 
    
    private void VsBotHard() 
    {//sempre que pode o bot tenta começar pelo meio, caso não seja possível ele começa por uma das quinas
     //a partir da sua posição inicial ele tenta completar a linha, e caso não seja possível tenta a coluna,   
     //caso o jogador esteja para vencer ele bloqueia o jogador, se o bot estiver para vencer ele vence, caso o 
     //jogador esteja para fazer uma quina ele ocupa uma das outras quinas do tabuleiro disponíveis, e se o 
     //jogador não bloquear essa jogada o bot ganha na sequência, esse bot não perde
        
        int [] Quinas = {0,2,6,8};
        int lin=0 , col=0, max =2, min = 0, aux=0, mx=3,mn=0;
        int MIc = -1 ,NIc=-1;//contador para iterar a posição inicial da coluna e linha
        int MI=-1, NI=-1;    // posições iniciais geradas aleatoriamente de m e n
        boolean ehValido = true, QuinasOcupadas = false;
        
        while(!Tabuleiro.VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir (valores de 0 a 2)");
            
            
            try
            {
                lin = teclado.nextInt();
                col = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                System.exit(-1);
            }
            player1.fazerJogada(lin, col);
            
            if(Tabuleiro.VefWinner())
                break;
            else if(Tabuleiro.nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                Tabuleiro.nj = 0;
                Tabuleiro.numJogos++;
                Jogador.imprimeJogadas(0);
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());

            if(Tabuleiro.nj == 1) 
            {// se é a primeira jogada do bot
                if(Tabuleiro.vef(1,1) != -1) // o bot sempre começa pelo meio se tiver liberado
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
                    if(Tabuleiro.vef(lin,col+1) != -1)
                        col = ++NIc;
                    else if(Tabuleiro.vef(lin,col-1) != -1)
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
                            if(Tabuleiro.vef(lin+1,col) != -1)
                                lin = ++MIc;
                            else if(Tabuleiro.vef(lin-1,col) != -1)
                                lin = --MIc;
                        }
                    }
                }
                if(Tabuleiro.vef(lin,col) != -1) //se a posição é válida
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
                        QuinasOcupadas = false;
                        
                        for(int i=0;i<Quinas.length;i++) //verifica se todas as quinas estão ocupadas
                        {
                            QuinasOcupadas = Tabuleiro.vefLin(Quinas[i])<0;
                            
                            if(!QuinasOcupadas)
                                break;
                        }
                        if(QuinasOcupadas == false)// se não estiverem o bot pega uma das quinas disponíveis para fazer a jogada
                        {
                            aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn); //Pega uma das quinas do tabuleiro
                        
                            while(Tabuleiro.vefLin(Quinas[aux])<0)
                            {
                                aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn);
                            }
                            player2.fazerJogadaLinBot(Quinas[aux]);
                        }
                        else //nesse ponto do jogo o resultado já é velha, então o bot gera posições aleatórias
                        {
                            while(Tabuleiro.vef(lin,col) == -1)
                            {
                                lin = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                                col = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                            }
                            player2.fazerJogadaBot(lin, col);
                        }
                    }
                }
                else
                {
                    boolean add = false;
                    while(Tabuleiro.vef(lin,col) == -1)
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
                        add = false;
                        
                        for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                                if(i*col+j == P1FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    add = true;
                                    break;
                                }
                            if(add) break;
                        }
                        player2.fazerJogadaLinBot(P1FaltaUm);//bloqueia o jogador 
                    }  
                    else if(P2FaltaUm != -1)//se o bot está pra ganhar
                    {
                        add = false;
                        for(int i=0;i<3;i++)
                        { //deslineariza a posição de faltaUm e salva nos contadores
                            for(int j=0;j<3;j++)
                            {
                                if(i*col+j == P2FaltaUm)
                                {
                                    MIc = i;
                                    NIc = j;
                                    add = true;
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
                        QuinasOcupadas = false;
                        for(int i=0;i<Quinas.length;i++) //verifica se todas as quinas estão ocupadas
                        {
                            QuinasOcupadas = Tabuleiro.vefLin(Quinas[i])<0;
                            
                            if(!QuinasOcupadas)
                                break;
                        }
                        
                        if(QuinasOcupadas == false) // se não estiverem o bot pega uma das quinas disponíveis para fazer a jogada
                        {
                            
                            aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn); //Pega uma das quinas do tabuleiro
                        
                            while(Tabuleiro.vefLin(Quinas[aux])<0)
                            {
                                aux = (int)Math.floor(Math.random() * (mx - mn + 1) + mn);
                            }
                            player2.fazerJogadaLinBot(Quinas[aux]);
                        }
                        else //nesse ponto do jogo o resultado já é velha, então o bot gera posições aleatórias
                        {
                            while(Tabuleiro.vef(lin,col) == -1)
                            {
                                lin = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                                col = (int)Math.floor(Math.random() * (max - min + 1) + min); 
                            }
                            player2.fazerJogadaBot(lin, col);
                        }  
                    }
                }
            } 
        }
        vencedor(true);
    }
    private void VsPlayer()
    {
        int m=0 , n=0;
        while(!Tabuleiro.VefWinner())
        {
            jogandoAgora = player1.getXB();
            System.out.println("Jogando agora: "+jogandoAgora);
            System.out.println("Digite a linha e coluna em que deseja inserir (valores de 0 a 2)");
            
           
            try
            {
                m = teclado.nextInt();
                n = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                System.exit(-1);
            }
            player1.fazerJogada(m, n);
            
            if(Tabuleiro.VefWinner())
                break;
            
            else if(Tabuleiro.nj == 9)
            {
                System.out.println("Deu velha");
                velha++;
                Tabuleiro.nj = 0;
                Tabuleiro.numJogos++;
                Jogador.imprimeJogadas(0);
                
                jogarNovamente();
                return;
            }
            
            jogandoAgora = player2.getXB();
            System.out.println("Jogando agora: "+player2.getXB());
            System.out.println("Digite a linha e coluna em que deseja inserir (valores de 0 a 2)");
            
           
            try
            {
                m = teclado.nextInt();
                n = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                System.exit(-1);
            }
            player2.fazerJogada(m, n);
        } 
        vencedor(false);
    }
    
    private int faltaUm(char xb) //verifica se falta um pra fechar uma linha/ coluna/ diagonal
    {
        for(int i=0; i<Tabuleiro.tabuleiro.length; i++)
        {
            if((i == 0) || (i == 3) || (i == 6)) //VEF CASOS LINHAS
            {
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+1].getVal()== ' ' && Tabuleiro.tabuleiro[i+2].getVal()==xb)
                    return i+1;
                if(Tabuleiro.tabuleiro[i].getVal() == ' ' && Tabuleiro.tabuleiro[i+1].getVal()==xb && Tabuleiro.tabuleiro[i+2].getVal()==xb)
                    return i;
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+1].getVal()==xb && Tabuleiro.tabuleiro[i+2].getVal()==' ')
                    return i+2;
            }
            if((i == 0) || (i == 1) || (i == 2)) // VEF CASOS COLUNAS
            {
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+3].getVal()== ' ' && Tabuleiro.tabuleiro[i+6].getVal()==xb)
                    return i+3;
                if(Tabuleiro.tabuleiro[i].getVal() == ' ' && Tabuleiro.tabuleiro[i+3].getVal()==xb && Tabuleiro.tabuleiro[i+6].getVal()==xb)
                    return i;
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+3].getVal()==xb && Tabuleiro.tabuleiro[i+6].getVal()==' ')
                    return i+6;
            }
            if((i == 0)) //VEF CASOS DIAG P
            {
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+4].getVal()== ' ' && Tabuleiro.tabuleiro[i+8].getVal()==xb)
                    return i+4;
                if(Tabuleiro.tabuleiro[i].getVal() == ' ' && Tabuleiro.tabuleiro[i+4].getVal()==xb && Tabuleiro.tabuleiro[i+8].getVal()==xb)
                    return i;
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+4].getVal()==xb && Tabuleiro.tabuleiro[i+8].getVal()==' ')
                    return i+8;
            }
            if((i == 2)) //VEF CASOS DIAG S
            {
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+2].getVal()== ' ' && Tabuleiro.tabuleiro[i+4].getVal()==xb)
                    return i+2;
                if(Tabuleiro.tabuleiro[i].getVal() == ' ' && Tabuleiro.tabuleiro[i+2].getVal()==xb && Tabuleiro.tabuleiro[i+4].getVal()==xb)
                    return i;
                if(Tabuleiro.tabuleiro[i].getVal() == xb && Tabuleiro.tabuleiro[i+2].getVal()==xb && Tabuleiro.tabuleiro[i+4].getVal()==' ')
                    return i+4;
            }
        }
        return -1;
    }
    
    private boolean FezQuina()
    {//verifica se o jogador marcou duas casas em sequencia (i,j; i+1,j+1), ou  (i,j; i+1,j-1) e se sim 
        for(int i=0; i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(j<=1 && i<=1)
                    if(Tabuleiro.tabuleiro[i*Tabuleiro.col+(j+1)].getVal() != player2.getXB())//verifica se a casa i,j+1 já está marcada pelo bot
                        if(Tabuleiro.tabuleiro[(i*Tabuleiro.col+j)].getVal() == player1.getXB() && Tabuleiro.tabuleiro[((i+1)*Tabuleiro.col+(j+1))].getVal() == player1.getXB())
                            return true;
    
                if(j>0 && i<=1)
                    if(Tabuleiro.tabuleiro[i*Tabuleiro.col+(j-1)].getVal() != player2.getXB())//verifica se a casa i,j-1 já está marcada pelo bot
                        if(Tabuleiro.tabuleiro[(i*Tabuleiro.col+j)].getVal() == player1.getXB() && Tabuleiro.tabuleiro[((i+1)*Tabuleiro.col+(j-1))].getVal() == player1.getXB())
                            return true;
            }
        }
        return false;
    }
    
    private void vencedor(boolean vsBot)
    {
        if(jogandoAgora == player1.getXB())
        {
            Tabuleiro.nj = 0;
            Tabuleiro.numJogos++;
            System.out.println("Player 1 venceu");
            winP1++;
            Jogador.imprimeJogadas(1);
            
            jogarNovamente();
        }
        else if(jogandoAgora == player2.getXB())
        {
            Tabuleiro.nj = 0;
            Tabuleiro.numJogos++;
            if(vsBot == true)
                System.out.println("O Bot venceu");
            else
                System.out.println("Player 2 venceu"); 
            winP2++;
            if(vsBot)
                Jogador.imprimeJogadas(3);
            else
                Jogador.imprimeJogadas(2);
            
            jogarNovamente();
        }
    }
    
    private void jogarNovamente()
    {
        int escolha=0;
        System.out.println("Deseja jogar novamente? \n(1)sim\n(2)nao");
        
        try
        {
            escolha = teclado.nextInt();
        }
        catch(java.util.InputMismatchException t)
        {
            System.out.println("Erro: Valor invalido digitado");
            return;
        }
        
        if(escolha == 1)
        {
            Tabuleiro.limpa();
            
            if(md == 2)
                SelBot();
            else
                VsPlayer();
        }
        else
            return;
    }
    
    public void inicio()
        {
            Tabuleiro.iniciaVet();
            
            System.out.println(boasVindas());
            
            md = SelModo();
            
            if(md == -1)
                return;
            
            SetPlayers();
            
            if(md == 2)
                SelBot();
            else
                VsPlayer();
        }
    public void Estatisticas()
    {
       System.out.println("==============================================\n" +
"       ===  Estatisticas de Jogo   ===\n" + "==============================================");
       System.out.println("Total de jogos: "+Tabuleiro.numJogos);
       System.out.println("Vitorias do Player1: "+winP1);
       System.out.println("Vitorias do Player2: "+winP2);
       System.out.println("O jogo deu velha "+velha+" vezes");
       Jogador.imprimeTUDO();
       System.out.println("==============================================");
    }
}
