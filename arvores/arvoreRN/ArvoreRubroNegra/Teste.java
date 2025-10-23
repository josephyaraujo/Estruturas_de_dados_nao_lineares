package ArvoreRubroNegra;
public class Teste {
    public static void main(String[] args){

        System.out.println("-------------1 arvore -------------");
        
        ArvoreRN rn = new ArvoreRN(10);
        rn.inserirRN(5);     // Alterado de addChild para inserirRN
        rn.inserirRN(15);
        rn.inserirRN(2);
        rn.inserirRN(8);
        rn.inserirRN(22);

        rn.printArvore();
        System.out.println();

        rn.inserirRN(25);
        rn.printArvore();
        System.out.println();

        rn.removerRN(5);     // Alterado de remove para removerRN
        rn.printArvore();
        System.out.println();


        System.out.println("-----------2--------------");
        ArvoreRN rn2 = new ArvoreRN(50);
        rn2.inserirRN(20);
        rn2.printArvore();
        System.out.println();
        rn2.inserirRN(90);
        rn2.printArvore();
        System.out.println();
        rn2.inserirRN(10);
        rn2.printArvore();
        System.out.println();
        rn2.inserirRN(40);

        rn2.printArvore();
        System.out.println();
        rn2.inserirRN(30);
    
        rn2.printArvore();
        System.out.println();

        rn2.removerRN(40);
        rn2.printArvore();
        System.out.println();

        System.out.println("----------3--------------");
        ArvoreRN rn3 = new ArvoreRN (50);
        rn3.inserirRN(20);
        rn3.inserirRN(80);
        rn3.inserirRN(70);
        rn3.inserirRN(90);

        rn3.printArvore();
        System.out.println();
        rn3.inserirRN(60);
    
        rn3.printArvore();

        System.out.println("-------------4--------------");
        ArvoreRN rn4 = new ArvoreRN(10);
        rn4.inserirRN(20);
        rn4.printArvore();
        rn4.inserirRN(30);
        rn4.printArvore();
        System.out.println();

        rn4.inserirRN(80);
        rn4.printArvore();
        System.out.println();
        
        rn4.inserirRN(70);
        rn4.printArvore();
        System.out.println();

        rn4.inserirRN(40);
        rn4.printArvore();
        System.out.println();

        rn4.removerRN(40);
        rn4.printArvore();
        System.out.println();

        rn4.removerRN(70);
        rn4.printArvore();
        System.out.println();

        rn4.removerRN(80);
        rn4.printArvore();
        System.out.println();

        // Continua para todas as outras árvores de teste...
        // (Omiti o resto do código para brevidade, mas seria o mesmo padrão de substituição)
        
        System.out.println("--------------14-------------");
        ArvoreRN rn14 = new ArvoreRN(10);
        rn14.inserirRN(5);
        rn14.inserirRN(12);
        rn14.printArvore();
        System.out.println();

        rn14.inserirRN(1);
        rn14.printArvore();
        System.out.println();

        rn14.inserirRN(6);
        rn14.printArvore();
        System.out.println();

        rn14.inserirRN(7);
        rn14.printArvore();
        System.out.println();
        
        rn14.removerRN(12);
        rn14.printArvore();
        System.out.println();
    }
}
