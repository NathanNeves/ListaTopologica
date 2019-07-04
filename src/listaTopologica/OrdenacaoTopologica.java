package listaTopologica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OrdenacaoTopologica
{
	private class Elo
	{
		/* Identificação do elemento. */
		public int chave;
		
		/* Número de predecessores. */
		public int contador;
		
		/* Aponta para o próximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
	}
	
	private class EloSuc
	{
		/* Aponta para o elo que é sucessor. */
		public Elo id;
		
		/* Aponta para o próximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
	}


	/* Ponteiro (referência) para primeiro elemento da lista. */
	private Elo prim;
	
	/* Número de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* Método responsável pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("src/"+nomeEntrada));
			String line;
			while((line = in.readLine()) != null){
				leituraLinha(line);		    
			}
			in.close();
			
			//imprimirElos(prim);
			debug();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void leituraLinha(String linha){
//		System.out.println(linha);
		//SEPARAR ELEMENTOS
		String[] elos = linha.replace(" ","").split("<");
		
		//ELEMENTO 1
		//JÁ EXISTE UM ELO DE MESMO VALOR?
		Elo elo1 = existeElo(Integer.valueOf(elos[0]));
		if(elo1==null) {
			elo1 = new Elo(Integer.valueOf(elos[0]),0,null, null);
			if(prim==null) {
				prim = elo1;
				n++;
			}else {
				ultimoElo().prox = elo1;
				n++;
			}
		}
		
		//ELEMENTO 2
		//JÁ EXISTE UM ELO DE MESMO VALOR?
		Elo elo2 = existeElo(Integer.valueOf(elos[1]));
		if(elo2==null) {
			elo2 = new Elo(Integer.valueOf(elos[1]),0,null, null);
			ultimoElo().prox = elo2;
			n++;
		}
		elo2.contador++;
		if(elo1.listaSuc==null) {
			elo1.listaSuc = new EloSuc(elo2,null);
		}else {
			ultimoEloSuc(elo1).prox = new EloSuc(elo2,null);
		}
		
		
		
		
//		
//		public int chave;
//		public int contador;
//		public Elo prox;
//		public EloSuc listaSuc;
	}
	
	private EloSuc ultimoEloSuc(Elo elo) {
		return ultimoEloSucBusca(elo.listaSuc);
	}
	
	private EloSuc ultimoEloSucBusca(EloSuc elo){
		if(elo.prox==null) {
			return elo;
		}else {
			return ultimoEloSucBusca(elo.prox);
		}
	}
	
	private Elo ultimoElo() {
		return ultimoEloBusca(prim);
	}
	
	private Elo ultimoEloBusca(Elo elo){
		if(elo.prox==null) {
			return elo;
		}else {
			return ultimoEloBusca(elo.prox);
		}
	}
	
	private Elo existeElo(int chave) {
		if(prim==null){
			return null;
		}else {
			return buscaElo(prim,chave);
		}
	}
	
	private Elo buscaElo(Elo elo,int chave){
		if(elo.chave==chave) {
			return elo;
		}
		if(elo.prox==null) {
			return null;
		}else {
			return buscaElo(elo.prox,chave);
		}
	}
	
	private void ArranjaConjunto() {
		Elo p = prim; prim = null;
		while(p!=null) {
			Elo q = p;
			p = q.prox;
			if(q.contador == 0) {
				q.prox = prim;
				prim = q;
			}
			p = p.prox;
		}
	}
	
	private void ordenaConjunto() {
		ArranjaConjunto();
		Elo q = prim;
		while(q!=null) {
			System.out.print(q.chave+" ");
			this.n--;
			EloSuc t = q.listaSuc;
			boolean teveZero = false;
			while(t!=null) {
				t.id.contador--;
				if(t.id.contador == 0) {
					t.id.prox = prim;
					prim = t.id;
					teveZero = true;
				}
				q.listaSuc = q.listaSuc.prox;
				t = q.listaSuc;
			}
			if(teveZero) {
				if(prim.prox.listaSuc==null) {
					prim.prox = q.prox;
				}else {
					Elo p = prim;
					prim = prim.prox;
					prim.prox = p;
					prim.prox.prox = null;
					
				}
				q = prim;
			}
			else {
				q = q.prox;
				prim = q;
			}
		}
		System.out.println("");
	}
	
	
	private void imprimirElos(Elo elo){
		System.out.print(elo.chave);
		System.out.print(" -- ");
		System.out.print(elo.contador);
		System.out.print(" ( ");
		imprimirEloSuc(elo.listaSuc);
		System.out.print(") ");
		System.out.println();
		if(elo.prox!=null) {
			imprimirElos(elo.prox);
		}
	}
	
	private void imprimirEloSuc(EloSuc elo) {
		if(elo!=null) {
			System.out.print(elo.id.chave+" ");
			if(elo.prox!=null) {
				imprimirEloSuc(elo.prox);
			}
		}		
	}
	
	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		debugRecursivo(prim);
		/* Preencher. */
	}
	
	private void debugRecursivo(Elo elo){
		if(elo!=null) {
			System.out.print(elo.chave+" predecessores: "+elo.contador+", sucessores: ");
			debugSucessoresRecursivo(elo.listaSuc);
			System.out.print("NULL");
			System.out.println();
			if(elo.prox!=null) {
				debugRecursivo(elo.prox);
			}
		}
	}
	
	private void debugSucessoresRecursivo(EloSuc elo){
		if(elo!=null) {
			if(elo.prox!=null) {
				debugSucessoresRecursivo(elo.prox);
			}
			System.out.print(elo.id.chave+" -> ");
		}	
	}
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{	String nomeEntrada = "entrada.txt";
		realizaLeitura(nomeEntrada);
		ordenaConjunto();
		boolean valor = (n == 0) ? true : false;
		return valor;
	}
}
