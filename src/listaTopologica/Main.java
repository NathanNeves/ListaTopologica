package listaTopologica;
public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		
		
		
		ord.executa();

		if(!ord.executa())
			System.out.println("O conjunto nao � parcialmente ordenado.");
		else
			System.out.println("O conjunto � parcialmente ordenado.");
	}
}
