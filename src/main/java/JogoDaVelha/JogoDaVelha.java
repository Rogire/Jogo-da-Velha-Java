package JogoDaVelha;
import java.util.Scanner;
/**
 *
 * @author igort
 */
public class JogoDaVelha {
    
    public static void main(String[] args) 
    {
        Scanner teclado = new Scanner(System.in);
        int sel;
        System.out.println("Selecione oque deseja fazer:\n(1)Jogar jogo da velha\n(2)Estatísticas\n(0)Sair");
        sel = teclado.nextInt();
        while(sel != 0)
        {
            if(sel == 1)
                ComecoDeJogo.inicio();
            else
                ComecoDeJogo.Estatisticas();
            
            System.out.println("Selecione oque deseja fazer:\n(1)Jogar jogo da velha\n(2)Estatísticas\n(0)Sair");
            sel = teclado.nextInt();
        }
    }
}
