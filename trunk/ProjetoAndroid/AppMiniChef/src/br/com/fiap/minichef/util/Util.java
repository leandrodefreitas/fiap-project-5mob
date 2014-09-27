package br.com.fiap.minichef.util ;

import java.text.NumberFormat ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;

public class Util
{

	public static boolean numeroEstaNoJogo( String numero, List<String> jogo )
	{
		NumberFormat nf = NumberFormat.getInstance( ) ;
		nf.setMinimumIntegerDigits( 2 ) ;

		String numeroDoisDigitos = nf.format( Long.valueOf( numero ) ) ;

		for (String dezena : jogo)
		{
			if (dezena.equals( numeroDoisDigitos ))
			{
				return true ;
			}
		}
		return false ;
	}

	public static List<Integer> obterNumerosRandomicos( int quantidade, int numeroMaximo )
	{
		List<Integer> listaNumeros = new ArrayList<Integer>( ) ;

		if (numeroMaximo < quantidade)
		{
			return null ;
		}

		do
		{
			Integer num = Integer.valueOf( ( ( 1 + ( int ) ( Math.random( ) * numeroMaximo ) ) ) ) ;

			boolean existe = false ;
			for (Integer item : listaNumeros)
			{
				if (item.intValue( ) == num.intValue( ))
				{
					existe = true ;
				}
			}
			if (!existe)
			{
				listaNumeros.add( num ) ;
			}
		}
		while (listaNumeros.size( ) < quantidade) ;

		Collections.sort( listaNumeros ) ;

		return listaNumeros ;
	}
}
