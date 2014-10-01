package br.com.fiap.minichef.util ;

import java.util.Map ;

public abstract class FIAPHttp
{
	// utiliza UrlConnection
	public static final int NORMAL = 1 ;

	// Utiliza o Jakarta HttpClient
	public static final int JAKARTA = 2 ;

	public static FIAPHttp getInstance( int tipo )
	{
		switch (tipo)
		{
		case NORMAL:
			// UrlConnection
			return new FIAPHttpNormalImpl( ) ;
		case JAKARTA:
			// Jakarta Commons HttpClient
			return new FIAPHttpClientImpl( ) ;
		default:
			return new FIAPHttpNormalImpl( ) ;
		}
	}

	// retorna o texto do arquivo
	public abstract String downloadArquivo( String url ) ;

	// retorna os bytes da imagem
	public abstract byte[ ] downloadImagem( String url ) ;

	// faz post enviando os parâmetros
	public abstract String doPost( String url, Map<String, Object> map ) ;
}