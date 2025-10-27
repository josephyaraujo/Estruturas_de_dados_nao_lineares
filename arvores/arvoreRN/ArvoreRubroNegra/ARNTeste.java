package ArvoreRubroNegra;
public class ARNTeste {
    public static void main(String[] args){

        System.out.println("----------Teste 1----------");
        
        ArvoreRN rn = new ArvoreRN(10);
        rn.inserirRN(5);     
        rn.inserirRN(15);
        rn.inserirRN(2);
        rn.inserirRN(8);
        rn.inserirRN(22);
        rn.printArvore();
        System.out.println("--------------------------");
        System.out.println();
            
        rn.inserirRN(25);
        rn.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn.removerRN(5);     
        rn.printArvore();
        System.out.println();


        System.out.println("----------Teste 2----------");
        ArvoreRN rn2 = new ArvoreRN(50);
        rn2.inserirRN(20);
        rn2.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn2.inserirRN(90);
        rn2.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn2.inserirRN(10);
        rn2.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn2.inserirRN(40);
        rn2.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn2.inserirRN(30);
        rn2.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn2.removerRN(40);
        rn2.printArvore();
        System.out.println();

        System.out.println("----------Teste 3----------");
        ArvoreRN rn3 = new ArvoreRN (50);
        rn3.inserirRN(20);
        rn3.inserirRN(80);
        rn3.inserirRN(70);
        rn3.inserirRN(90);
        rn3.printArvore();
        System.out.println("--------------------------");
        System.out.println();
        
        rn3.inserirRN(60);
        rn3.printArvore();
        System.out.println();

        System.out.println("----------Teste 4-----------");
        ArvoreRN rn4 = new ArvoreRN(10);
        rn4.inserirRN(20);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.inserirRN(30);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.inserirRN(80);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();
        
        rn4.inserirRN(70);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.inserirRN(40);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.removerRN(40);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.removerRN(70);
        rn4.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn4.removerRN(80);
        rn4.printArvore();
        System.out.println();
        
        System.out.println("-----------Teste 5----------");
        ArvoreRN rn5 = new ArvoreRN(10);
        rn5.inserirRN(5);
        rn5.inserirRN(12);
        rn5.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn5.inserirRN(1);
        rn5.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn5.inserirRN(6);
        rn5.printArvore();
        System.out.println("--------------------------");
        System.out.println();

        rn5.inserirRN(7);
        rn5.printArvore();
        System.out.println("--------------------------");
        System.out.println();
        
        rn5.removerRN(12);
        rn5.printArvore();
        System.out.println();
    }
}
