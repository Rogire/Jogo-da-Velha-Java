package JogoDaVelha;
import java.util.Scanner;
/**
 *
 * @author igort
 */
public class JogoDaVelha {
    
    public static void main(String[] args) 
    {
        ComecoDeJogo jogo = new ComecoDeJogo();
        Scanner teclado = new Scanner(System.in);
        
        int sel = 0;
        System.out.println("Selecione oque deseja fazer:\n(1)Jogar jogo da velha\n(2)Estatisticas\n(0)Sair");
        try{
            sel = teclado.nextInt();
        }
        catch(java.util.InputMismatchException t)
        {
            System.out.println("Erro: Valor invalido digitado");
            return;
        }
        
        while(sel != 0)
        {
            if(sel == 1)
                jogo.inicio();
            else
                jogo.Estatisticas();
            
            System.out.println("Selecione oque deseja fazer:\n(1)Jogar jogo da velha\n(2)Estatisticas\n(0)Sair");
            try
            {
                sel = teclado.nextInt();
            }
            catch(java.util.InputMismatchException t)
            {
                System.out.println("Erro: Valor invalido digitado");
                return;
            }
        }
    }
}
