package br.com.fiap.financas.util ;

import android.annotation.TargetApi ;
import android.os.Build ;
import android.view.ScaleGestureDetector ;

public class ScaleGestureDetectorCompat
{
	private ScaleGestureDetectorCompat( )
	{
	}

	@TargetApi( Build.VERSION_CODES.HONEYCOMB )
	public static float getCurrentSpanX( ScaleGestureDetector scaleGestureDetector )
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return scaleGestureDetector.getCurrentSpanX( ) ;
		}
		else
		{
			return scaleGestureDetector.getCurrentSpan( ) ;
		}
	}

	@TargetApi( Build.VERSION_CODES.HONEYCOMB )
	public static float getCurrentSpanY( ScaleGestureDetector scaleGestureDetector )
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return scaleGestureDetector.getCurrentSpanY( ) ;
		}
		else
		{
			return scaleGestureDetector.getCurrentSpan( ) ;
		}
	}
}
