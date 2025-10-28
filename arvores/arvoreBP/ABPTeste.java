package arvores.arvoreBP;

public class ABPTeste {
    public static void main(String[] args) {
        ABP arvore = new ABP(50);
        arvore.inserir(30);
        arvore.inserir(70);
        arvore.inserir(20);
        arvore.inserir(40);
        arvore.inserir(60);
        arvore.inserir(80);

        System.out.println("Árvore impressa:");
        arvore.printArvore();

        System.out.println("Elementos em ordem:");
        arvore.inOrder(arvore.root());
        System.out.println();

        System.out.println("Elementos em pré-ordem:");
        arvore.preOrder(arvore.root());
        System.out.println();

        System.out.println("Elementos em pós-ordem:");
        arvore.postOrder(arvore.root());
        System.out.println();

        System.out.println("Altura da árvore: " + arvore.height(arvore.root()));
        System.out.println("Tamanho da árvore: " + arvore.size());

        System.out.println("Removendo 20...");
        arvore.remover(20);
        System.out.println("Elementos em ordem após remoção:");
        arvore.inOrder(arvore.root());
        System.out.println();

        System.out.println("Removendo 30...");
        arvore.remover(30);
        System.out.println("Elementos em ordem após remoção:");
        arvore.inOrder(arvore.root());
        System.out.println();

        System.out.println("Removendo 50...");
        arvore.remover(50);
        System.out.println("Elementos em ordem após remoção:");
        arvore.inOrder(arvore.root());
        System.out.println();
    }
}