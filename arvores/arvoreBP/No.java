package arvores.arvoreBP; 

public class No {
    protected Object valor; 
    protected No pai; 
    protected No filhoEsquerdo;
    protected No filhoDireito;
    protected int nFilhos;

    public No (No p, Object o){
        valor = o;
        pai = p; 
        nFilhos = 0;
    }

    public void setValor (Object o){
        valor = o;
    }

    public Object getValor(){
        return valor;
    }

    public void setPai (No n){
        pai = n;
    }

    public No getPai(){
        return pai; 
    }

    public void serFilhoEsquerdo (No n){
        filhoEsquerdo = n; 
    }
    
    public No getFilhoEsquerdo(){
        return filhoEsquerdo; 
    }
    
    public void setFilhoDireito (No n){
        filhoDireito = n; 
    }
    
    public No getFilhoDireito(){
        return filhoDireito; 
    }
    
    public void setNFilhos (int n){
        nFilhos = n; 
    } 
    
    public int getNFilhos() {
        return nFilhos;
    }
    
}

