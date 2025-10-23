package ArvoreRubroNegra;

public class No {
    private char cor;
    protected Object chave;
    protected No pai;
    protected No esquerdo;
    protected No direito;

    public No (No p, Object o){
        chave = o;
        pai = p;
        cor = 'R'; //Novo nó sempre rubro
        esquerdo = null;
        direito = null;
    }
    public void setCor(char c){
        cor = c;
    }
    public char getCor(){
        return cor;
    }

    public int getNFilhos(){
        int count = 0;
        if (esquerdo != null) count++;
        if (direito != null) count++;
        return count;
    }

    public void setChave (Object o){
        chave = o;
    }

    public Object getChave(){
        return chave;
    }

    public void setPai(No n){
        pai = n;
    }

    public No getPai(){
        return pai;
    }

    public void setDireito(No n) {
        direito = n;
    }

    public No getDireito(){
        return direito;
    }

    public void setEsquerdo(No n){
        esquerdo = n;
    }

    public No getEsquerdo(){
        return esquerdo;
    }


}
