package arvores.arvoreAVL;

public interface ArvoreAVL {
    public void rotacao(AVLNo atual, AVLNo pai);
    public void altFBinsercao(AVLNo n);
    public void altFBremocao(AVLNo n);
    public void rotacaoSimplesDireita(AVLNo n, AVLNo atual);
    public void rotacaoSimplesEsquerda(AVLNo n, AVLNo atual);
}