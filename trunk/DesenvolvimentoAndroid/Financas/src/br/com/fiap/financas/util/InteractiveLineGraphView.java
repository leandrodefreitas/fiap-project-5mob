package br.com.fiap.financas.util;

import br.com.fiap.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

public class InteractiveLineGraphView extends View {
	
	private static final String TAG = "InteractiveLineGraphView";

	private static final int DRAW_STEPS = 30;

	private static final float PAN_VELOCITY_FACTOR = 2f;

	private static final float ZOOM_AMOUNT = 0.25f;

	private static final float AXIS_X_MIN = -1f;
	private static final float AXIS_X_MAX = 1f;
	private static final float AXIS_Y_MIN = -1f;
	private static final float AXIS_Y_MAX = 1f;

	private RectF mCurrentViewport = new RectF( AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX );

	private Rect mContentRect = new Rect( );

	private float mLabelTextSize;
	private int mLabelSeparation;
	private int mLabelTextColor;
	private Paint mLabelTextPaint;
	private int mMaxLabelWidth;
	private int mLabelHeight;
	private float mGridThickness;
	private int mGridColor;
	private Paint mGridPaint;
	private float mAxisThickness;
	private int mAxisColor;
	private Paint mAxisPaint;
	private float mDataThickness;
	private int mDataColor;
	private Paint mDataPaint;

	private ScaleGestureDetector mScaleGestureDetector;
	private GestureDetectorCompat mGestureDetector;
	private OverScroller mScroller;
	private Zoomer mZoomer;
	private PointF mZoomFocalPoint = new PointF( );
	private RectF mScrollerStartViewport = new RectF( );

	private EdgeEffectCompat mEdgeEffectTop;
	private EdgeEffectCompat mEdgeEffectBottom;
	private EdgeEffectCompat mEdgeEffectLeft;
	private EdgeEffectCompat mEdgeEffectRight;

	private boolean mEdgeEffectTopActive;
	private boolean mEdgeEffectBottomActive;
	private boolean mEdgeEffectLeftActive;
	private boolean mEdgeEffectRightActive;

	private final AxisStops mXStopsBuffer = new AxisStops( );
	private final AxisStops mYStopsBuffer = new AxisStops( );

	private float[ ] mAxisXPositionsBuffer = new float[ ]
	{};
	private float[ ] mAxisYPositionsBuffer = new float[ ]
	{};
	private float[ ] mAxisXLinesBuffer = new float[ ]
	{};
	private float[ ] mAxisYLinesBuffer = new float[ ]
	{};
	private float[ ] mSeriesLinesBuffer = new float[ ( DRAW_STEPS + 1 ) * 4 ];
	private final char[ ] mLabelBuffer = new char[ 100 ];
	private Point mSurfaceSizeBuffer = new Point( );

	protected static float fun( float x )
	{
		return ( float ) Math.pow( x, 3 ) - x / 4;
	}

	public InteractiveLineGraphView( Context context )
	{
		this( context, null, 0 );
	}

	public InteractiveLineGraphView( Context context, AttributeSet attrs )
	{
		this( context, attrs, 0 );
	}

	public InteractiveLineGraphView( Context context, AttributeSet attrs, int defStyle ) {
		super( context, attrs, defStyle );

		TypedArray a = context.getTheme( ).obtainStyledAttributes( attrs,
				R.styleable.InteractiveLineGraphView, defStyle, defStyle );

		try
		{
			mLabelTextColor = a.getColor( R.styleable.InteractiveLineGraphView_labelTextColor,
					mLabelTextColor );
			mLabelTextSize = a.getDimension( R.styleable.InteractiveLineGraphView_labelTextSize,
					mLabelTextSize );
			mLabelSeparation = a.getDimensionPixelSize(
					R.styleable.InteractiveLineGraphView_labelSeparation, mLabelSeparation );

			mGridThickness = a.getDimension( R.styleable.InteractiveLineGraphView_gridThickness,
					mGridThickness );
			mGridColor = a.getColor( R.styleable.InteractiveLineGraphView_gridColor, mGridColor );

			mAxisThickness = a.getDimension( R.styleable.InteractiveLineGraphView_axisThickness,
					mAxisThickness );
			mAxisColor = a.getColor( R.styleable.InteractiveLineGraphView_axisColor, mAxisColor );

			mDataThickness = a.getDimension( R.styleable.InteractiveLineGraphView_dataThickness,
					mDataThickness );
			mDataColor = a.getColor( R.styleable.InteractiveLineGraphView_dataColor, mDataColor );
		}
		finally
		{
			a.recycle( );
		}

		initPaints( );

		mScaleGestureDetector = new ScaleGestureDetector( context, mScaleGestureListener );
		mGestureDetector = new GestureDetectorCompat( context, mGestureListener );

		mScroller = new OverScroller( context );
		mZoomer = new Zoomer( context );

		mEdgeEffectLeft = new EdgeEffectCompat( context );
		mEdgeEffectTop = new EdgeEffectCompat( context );
		mEdgeEffectRight = new EdgeEffectCompat( context );
		mEdgeEffectBottom = new EdgeEffectCompat( context );
	}

	private void initPaints( )
	{
		mLabelTextPaint = new Paint( );
		mLabelTextPaint.setAntiAlias( true );
		mLabelTextPaint.setTextSize( mLabelTextSize );
		mLabelTextPaint.setColor( mLabelTextColor );
		mLabelHeight = ( int ) Math.abs( mLabelTextPaint.getFontMetrics( ).top );
		mMaxLabelWidth = ( int ) mLabelTextPaint.measureText( "0000" );

		mGridPaint = new Paint( );
		mGridPaint.setStrokeWidth( mGridThickness );
		mGridPaint.setColor( mGridColor );
		mGridPaint.setStyle( Paint.Style.STROKE );

		mAxisPaint = new Paint( );
		mAxisPaint.setStrokeWidth( mAxisThickness );
		mAxisPaint.setColor( mAxisColor );
		mAxisPaint.setStyle( Paint.Style.STROKE );

		mDataPaint = new Paint( );
		mDataPaint.setStrokeWidth( mDataThickness );
		mDataPaint.setColor( mDataColor );
		mDataPaint.setStyle( Paint.Style.STROKE );
		mDataPaint.setAntiAlias( true );
	}

	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		super.onSizeChanged( w, h, oldw, oldh );
		mContentRect.set( getPaddingLeft( ) + mMaxLabelWidth + mLabelSeparation, getPaddingTop( ),
				getWidth( ) - getPaddingRight( ), getHeight( ) - getPaddingBottom( ) - mLabelHeight
						- mLabelSeparation );
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
	{
		int minChartSize = getResources( ).getDimensionPixelSize( R.dimen.min_chart_size );
		setMeasuredDimension(
				Math.max(
						getSuggestedMinimumWidth( ),
						resolveSize( minChartSize + getPaddingLeft( ) + mMaxLabelWidth
								+ mLabelSeparation + getPaddingRight( ), widthMeasureSpec ) ),
				Math.max(
						getSuggestedMinimumHeight( ),
						resolveSize( minChartSize + getPaddingTop( ) + mLabelHeight
								+ mLabelSeparation + getPaddingBottom( ), heightMeasureSpec ) ) );
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		super.onDraw( canvas );

		drawAxes( canvas );

		int clipRestoreCount = canvas.save( );
		canvas.clipRect( mContentRect );

		drawDataSeriesUnclipped( canvas );
		drawEdgeEffectsUnclipped( canvas );

		canvas.restoreToCount( clipRestoreCount );

		canvas.drawRect( mContentRect, mAxisPaint );
	}

	private void drawAxes( Canvas canvas )
	{
		int i;

		computeAxisStops( mCurrentViewport.left, mCurrentViewport.right, mContentRect.width( )
				/ mMaxLabelWidth / 2, mXStopsBuffer );
		computeAxisStops( mCurrentViewport.top, mCurrentViewport.bottom, mContentRect.height( )
				/ mLabelHeight / 2, mYStopsBuffer );

		if (mAxisXPositionsBuffer.length < mXStopsBuffer.numStops)
		{
			mAxisXPositionsBuffer = new float[ mXStopsBuffer.numStops ];
		}
		if (mAxisYPositionsBuffer.length < mYStopsBuffer.numStops)
		{
			mAxisYPositionsBuffer = new float[ mYStopsBuffer.numStops ];
		}
		if (mAxisXLinesBuffer.length < mXStopsBuffer.numStops * 4)
		{
			mAxisXLinesBuffer = new float[ mXStopsBuffer.numStops * 4 ];
		}
		if (mAxisYLinesBuffer.length < mYStopsBuffer.numStops * 4)
		{
			mAxisYLinesBuffer = new float[ mYStopsBuffer.numStops * 4 ];
		}

		for (i = 0; i < mXStopsBuffer.numStops; i++)
		{
			mAxisXPositionsBuffer[ i ] = getDrawX( mXStopsBuffer.stops[ i ] );
		}
		for (i = 0; i < mYStopsBuffer.numStops; i++)
		{
			mAxisYPositionsBuffer[ i ] = getDrawY( mYStopsBuffer.stops[ i ] );
		}

		for (i = 0; i < mXStopsBuffer.numStops; i++)
		{
			mAxisXLinesBuffer[ i * 4 + 0 ] = ( float ) Math.floor( mAxisXPositionsBuffer[ i ] );
			mAxisXLinesBuffer[ i * 4 + 1 ] = mContentRect.top;
			mAxisXLinesBuffer[ i * 4 + 2 ] = ( float ) Math.floor( mAxisXPositionsBuffer[ i ] );
			mAxisXLinesBuffer[ i * 4 + 3 ] = mContentRect.bottom;
		}
		canvas.drawLines( mAxisXLinesBuffer, 0, mXStopsBuffer.numStops * 4, mGridPaint );

		for (i = 0; i < mYStopsBuffer.numStops; i++)
		{
			mAxisYLinesBuffer[ i * 4 + 0 ] = mContentRect.left;
			mAxisYLinesBuffer[ i * 4 + 1 ] = ( float ) Math.floor( mAxisYPositionsBuffer[ i ] );
			mAxisYLinesBuffer[ i * 4 + 2 ] = mContentRect.right;
			mAxisYLinesBuffer[ i * 4 + 3 ] = ( float ) Math.floor( mAxisYPositionsBuffer[ i ] );
		}
		canvas.drawLines( mAxisYLinesBuffer, 0, mYStopsBuffer.numStops * 4, mGridPaint );

		int labelOffset;
		int labelLength;
		mLabelTextPaint.setTextAlign( Paint.Align.CENTER );
		for (i = 0; i < mXStopsBuffer.numStops; i++)
		{
			labelLength = formatFloat( mLabelBuffer, mXStopsBuffer.stops[ i ],
					mXStopsBuffer.decimals );
			labelOffset = mLabelBuffer.length - labelLength;
			canvas.drawText( mLabelBuffer, labelOffset, labelLength, mAxisXPositionsBuffer[ i ],
					mContentRect.bottom + mLabelHeight + mLabelSeparation, mLabelTextPaint );
		}

		mLabelTextPaint.setTextAlign( Paint.Align.RIGHT );
		for (i = 0; i < mYStopsBuffer.numStops; i++)
		{
			labelLength = formatFloat( mLabelBuffer, mYStopsBuffer.stops[ i ],
					mYStopsBuffer.decimals );
			labelOffset = mLabelBuffer.length - labelLength;
			canvas.drawText( mLabelBuffer, labelOffset, labelLength, mContentRect.left
					- mLabelSeparation, mAxisYPositionsBuffer[ i ] + mLabelHeight / 2,
					mLabelTextPaint );
		}
	}

	private static float roundToOneSignificantFigure( double num )
	{
		final float d = ( float ) Math.ceil( ( float ) Math.log10( num < 0 ? -num : num ) );
		final int power = 1 - ( int ) d;
		final float magnitude = ( float ) Math.pow( 10, power );
		final long shifted = Math.round( num * magnitude );
		return shifted / magnitude;
	}

	private static final int POW10[] =
	{ 1, 10, 100, 1000, 10000, 100000, 1000000 };

	private static int formatFloat( final char[ ] out, float val, int digits )
	{
		boolean negative = false;
		if (val == 0)
		{
			out[ out.length - 1 ] = '0';
			return 1;
		}
		if (val < 0)
		{
			negative = true;
			val = -val;
		}
		if (digits > POW10.length)
		{
			digits = POW10.length - 1;
		}
		val *= POW10[ digits ];
		long lval = Math.round( val );
		int index = out.length - 1;
		int charCount = 0;
		while (lval != 0 || charCount < ( digits + 1 ))
		{
			int digit = ( int ) ( lval % 10 );
			lval = lval / 10;
			out[ index-- ] = ( char ) ( digit + '0' );
			charCount++;
			if (charCount == digits)
			{
				out[ index-- ] = '.';
				charCount++;
			}
		}
		if (negative)
		{
			out[ index-- ] = '-';
			charCount++;
		}
		return charCount;
	}

	private static void computeAxisStops( float start, float stop, int steps, AxisStops outStops )
	{
		double range = stop - start;
		if (steps == 0 || range <= 0)
		{
			outStops.stops = new float[ ]
			{};
			outStops.numStops = 0;
			return;
		}

		double rawInterval = range / steps;
		double interval = roundToOneSignificantFigure( rawInterval );
		double intervalMagnitude = Math.pow( 10, ( int ) Math.log10( interval ) );
		int intervalSigDigit = ( int ) ( interval / intervalMagnitude );
		if (intervalSigDigit > 5)
		{
			interval = Math.floor( 10 * intervalMagnitude );
		}

		double first = Math.ceil( start / interval ) * interval;
		double last = Math.nextUp( Math.floor( stop / interval ) * interval );

		double f;
		int i;
		int n = 0;
		for (f = first; f <= last; f += interval)
		{
			++n;
		}

		outStops.numStops = n;

		if (outStops.stops.length < n)
		{
			outStops.stops = new float[ n ];
		}

		for (f = first, i = 0; i < n; f += interval, ++i)
		{
			outStops.stops[ i ] = ( float ) f;
		}

		if (interval < 1)
		{
			outStops.decimals = ( int ) Math.ceil( -Math.log10( interval ) );
		}
		else
		{
			outStops.decimals = 0;
		}
	}

	private float getDrawX( float x )
	{
		return mContentRect.left + mContentRect.width( ) * ( x - mCurrentViewport.left )
				/ mCurrentViewport.width( );
	}

	private float getDrawY( float y )
	{
		return mContentRect.bottom - mContentRect.height( ) * ( y - mCurrentViewport.top )
				/ mCurrentViewport.height( );
	}

	private void drawDataSeriesUnclipped( Canvas canvas )
	{
		mSeriesLinesBuffer[ 0 ] = mContentRect.left;
		mSeriesLinesBuffer[ 1 ] = getDrawY( fun( mCurrentViewport.left ) );
		mSeriesLinesBuffer[ 2 ] = mSeriesLinesBuffer[ 0 ];
		mSeriesLinesBuffer[ 3 ] = mSeriesLinesBuffer[ 1 ];
		float x;
		for (int i = 1; i <= DRAW_STEPS; i++)
		{
			mSeriesLinesBuffer[ i * 4 + 0 ] = mSeriesLinesBuffer[ ( i - 1 ) * 4 + 2 ];
			mSeriesLinesBuffer[ i * 4 + 1 ] = mSeriesLinesBuffer[ ( i - 1 ) * 4 + 3 ];

			x = ( mCurrentViewport.left + ( mCurrentViewport.width( ) / DRAW_STEPS * i ) );
			mSeriesLinesBuffer[ i * 4 + 2 ] = getDrawX( x );
			mSeriesLinesBuffer[ i * 4 + 3 ] = getDrawY( fun( x ) );
		}
		canvas.drawLines( mSeriesLinesBuffer, mDataPaint );
	}

	private void drawEdgeEffectsUnclipped( Canvas canvas )
	{
		boolean needsInvalidate = false;

		if (!mEdgeEffectTop.isFinished( ))
		{
			final int restoreCount = canvas.save( );
			canvas.translate( mContentRect.left, mContentRect.top );
			mEdgeEffectTop.setSize( mContentRect.width( ), mContentRect.height( ) );
			if (mEdgeEffectTop.draw( canvas ))
			{
				needsInvalidate = true;
			}
			canvas.restoreToCount( restoreCount );
		}

		if (!mEdgeEffectBottom.isFinished( ))
		{
			final int restoreCount = canvas.save( );
			canvas.translate( 2 * mContentRect.left - mContentRect.right, mContentRect.bottom );
			canvas.rotate( 180, mContentRect.width( ), 0 );
			mEdgeEffectBottom.setSize( mContentRect.width( ), mContentRect.height( ) );
			if (mEdgeEffectBottom.draw( canvas ))
			{
				needsInvalidate = true;
			}
			canvas.restoreToCount( restoreCount );
		}

		if (!mEdgeEffectLeft.isFinished( ))
		{
			final int restoreCount = canvas.save( );
			canvas.translate( mContentRect.left, mContentRect.bottom );
			canvas.rotate( -90, 0, 0 );
			mEdgeEffectLeft.setSize( mContentRect.height( ), mContentRect.width( ) );
			if (mEdgeEffectLeft.draw( canvas ))
			{
				needsInvalidate = true;
			}
			canvas.restoreToCount( restoreCount );
		}

		if (!mEdgeEffectRight.isFinished( ))
		{
			final int restoreCount = canvas.save( );
			canvas.translate( mContentRect.right, mContentRect.top );
			canvas.rotate( 90, 0, 0 );
			mEdgeEffectRight.setSize( mContentRect.height( ), mContentRect.width( ) );
			if (mEdgeEffectRight.draw( canvas ))
			{
				needsInvalidate = true;
			}
			canvas.restoreToCount( restoreCount );
		}

		if (needsInvalidate)
		{
			ViewCompat.postInvalidateOnAnimation( this );
		}
	}

	private boolean hitTest( float x, float y, PointF dest )
	{
		if (!mContentRect.contains( ( int ) x, ( int ) y ))
		{
			return false;
		}

		dest.set( mCurrentViewport.left + mCurrentViewport.width( ) * ( x - mContentRect.left )
				/ mContentRect.width( ), mCurrentViewport.top + mCurrentViewport.height( )
				* ( y - mContentRect.bottom ) / -mContentRect.height( ) );
		return true;
	}

	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		boolean retVal = mScaleGestureDetector.onTouchEvent( event );
		retVal = mGestureDetector.onTouchEvent( event ) || retVal;
		return retVal || super.onTouchEvent( event );
	}

	private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener( )
	{
		private PointF viewportFocus = new PointF( );
		private float lastSpanX;
		private float lastSpanY;

		@Override
		public boolean onScaleBegin( ScaleGestureDetector scaleGestureDetector )
		{
			lastSpanX = ScaleGestureDetectorCompat.getCurrentSpanX( scaleGestureDetector );
			lastSpanY = ScaleGestureDetectorCompat.getCurrentSpanY( scaleGestureDetector );
			return true;
		}

		@Override
		public boolean onScale( ScaleGestureDetector scaleGestureDetector )
		{
			float spanX = ScaleGestureDetectorCompat.getCurrentSpanX( scaleGestureDetector );
			float spanY = ScaleGestureDetectorCompat.getCurrentSpanY( scaleGestureDetector );

			float newWidth = lastSpanX / spanX * mCurrentViewport.width( );
			float newHeight = lastSpanY / spanY * mCurrentViewport.height( );

			float focusX = scaleGestureDetector.getFocusX( );
			float focusY = scaleGestureDetector.getFocusY( );
			hitTest( focusX, focusY, viewportFocus );

			mCurrentViewport.set( viewportFocus.x - newWidth * ( focusX - mContentRect.left )
					/ mContentRect.width( ), viewportFocus.y - newHeight
					* ( mContentRect.bottom - focusY ) / mContentRect.height( ), 0, 0 );
			mCurrentViewport.right = mCurrentViewport.left + newWidth;
			mCurrentViewport.bottom = mCurrentViewport.top + newHeight;
			constrainViewport( );
			ViewCompat.postInvalidateOnAnimation( InteractiveLineGraphView.this );

			lastSpanX = spanX;
			lastSpanY = spanY;
			return true;
		}
	};

	private void constrainViewport( )
	{
		mCurrentViewport.left = Math.max( AXIS_X_MIN, mCurrentViewport.left );
		mCurrentViewport.top = Math.max( AXIS_Y_MIN, mCurrentViewport.top );
		mCurrentViewport.bottom = Math.max( Math.nextUp( mCurrentViewport.top ),
				Math.min( AXIS_Y_MAX, mCurrentViewport.bottom ) );
		mCurrentViewport.right = Math.max( Math.nextUp( mCurrentViewport.left ),
				Math.min( AXIS_X_MAX, mCurrentViewport.right ) );
	}

	private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener( )
	{
		@Override
		public boolean onDown( MotionEvent e )
		{
			releaseEdgeEffects( );
			mScrollerStartViewport.set( mCurrentViewport );
			mScroller.forceFinished( true );
			ViewCompat.postInvalidateOnAnimation( InteractiveLineGraphView.this );
			return true;
		}

		@Override
		public boolean onDoubleTap( MotionEvent e )
		{
			mZoomer.forceFinished( true );
			if (hitTest( e.getX( ), e.getY( ), mZoomFocalPoint ))
			{
				mZoomer.startZoom( ZOOM_AMOUNT );
			}
			ViewCompat.postInvalidateOnAnimation( InteractiveLineGraphView.this );
			return true;
		}

		@Override
		public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY )
		{
			float viewportOffsetX = distanceX * mCurrentViewport.width( ) / mContentRect.width( );
			float viewportOffsetY = -distanceY * mCurrentViewport.height( ) / mContentRect.height( );
			computeScrollSurfaceSize( mSurfaceSizeBuffer );
			int scrolledX = ( int ) ( mSurfaceSizeBuffer.x
					* ( mCurrentViewport.left + viewportOffsetX - AXIS_X_MIN ) / ( AXIS_X_MAX - AXIS_X_MIN ) );
			int scrolledY = ( int ) ( mSurfaceSizeBuffer.y
					* ( AXIS_Y_MAX - mCurrentViewport.bottom - viewportOffsetY ) / ( AXIS_Y_MAX - AXIS_Y_MIN ) );
			boolean canScrollX = mCurrentViewport.left > AXIS_X_MIN
					|| mCurrentViewport.right < AXIS_X_MAX;
			boolean canScrollY = mCurrentViewport.top > AXIS_Y_MIN
					|| mCurrentViewport.bottom < AXIS_Y_MAX;
			setViewportBottomLeft( mCurrentViewport.left + viewportOffsetX, mCurrentViewport.bottom
					+ viewportOffsetY );

			if (canScrollX && scrolledX < 0)
			{
				mEdgeEffectLeft.onPull( scrolledX / ( float ) mContentRect.width( ) );
				mEdgeEffectLeftActive = true;
			}
			if (canScrollY && scrolledY < 0)
			{
				mEdgeEffectTop.onPull( scrolledY / ( float ) mContentRect.height( ) );
				mEdgeEffectTopActive = true;
			}
			if (canScrollX && scrolledX > mSurfaceSizeBuffer.x - mContentRect.width( ))
			{
				mEdgeEffectRight
						.onPull( ( scrolledX - mSurfaceSizeBuffer.x + mContentRect.width( ) )
								/ ( float ) mContentRect.width( ) );
				mEdgeEffectRightActive = true;
			}
			if (canScrollY && scrolledY > mSurfaceSizeBuffer.y - mContentRect.height( ))
			{
				mEdgeEffectBottom.onPull( ( scrolledY - mSurfaceSizeBuffer.y + mContentRect
						.height( ) ) / ( float ) mContentRect.height( ) );
				mEdgeEffectBottomActive = true;
			}
			return true;
		}

		@Override
		public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
		{
			fling( ( int ) -velocityX, ( int ) -velocityY );
			return true;
		}
	};

	private void releaseEdgeEffects( )
	{
		mEdgeEffectLeftActive = mEdgeEffectTopActive = mEdgeEffectRightActive = mEdgeEffectBottomActive = false;
		mEdgeEffectLeft.onRelease( );
		mEdgeEffectTop.onRelease( );
		mEdgeEffectRight.onRelease( );
		mEdgeEffectBottom.onRelease( );
	}

	private void fling( int velocityX, int velocityY )
	{
		releaseEdgeEffects( );
		computeScrollSurfaceSize( mSurfaceSizeBuffer );
		mScrollerStartViewport.set( mCurrentViewport );
		int startX = ( int ) ( mSurfaceSizeBuffer.x * ( mScrollerStartViewport.left - AXIS_X_MIN ) / ( AXIS_X_MAX - AXIS_X_MIN ) );
		int startY = ( int ) ( mSurfaceSizeBuffer.y * ( AXIS_Y_MAX - mScrollerStartViewport.bottom ) / ( AXIS_Y_MAX - AXIS_Y_MIN ) );
		mScroller.forceFinished( true );
		mScroller.fling( startX, startY, velocityX, velocityY, 0, mSurfaceSizeBuffer.x
				- mContentRect.width( ), 0, mSurfaceSizeBuffer.y - mContentRect.height( ),
				mContentRect.width( ) / 2, mContentRect.height( ) / 2 );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	private void computeScrollSurfaceSize( Point out )
	{
		out.set( ( int ) ( mContentRect.width( ) * ( AXIS_X_MAX - AXIS_X_MIN ) / mCurrentViewport
				.width( ) ),
				( int ) ( mContentRect.height( ) * ( AXIS_Y_MAX - AXIS_Y_MIN ) / mCurrentViewport
						.height( ) ) );
	}

	@Override
	public void computeScroll( )
	{
		super.computeScroll( );

		boolean needsInvalidate = false;

		if (mScroller.computeScrollOffset( ))
		{
			computeScrollSurfaceSize( mSurfaceSizeBuffer );
			int currX = mScroller.getCurrX( );
			int currY = mScroller.getCurrY( );

			boolean canScrollX = ( mCurrentViewport.left > AXIS_X_MIN || mCurrentViewport.right < AXIS_X_MAX );
			boolean canScrollY = ( mCurrentViewport.top > AXIS_Y_MIN || mCurrentViewport.bottom < AXIS_Y_MAX );

			if (canScrollX && currX < 0 && mEdgeEffectLeft.isFinished( ) && !mEdgeEffectLeftActive)
			{
				mEdgeEffectLeft.onAbsorb( ( int ) OverScrollerCompat.getCurrVelocity( mScroller ) );
				mEdgeEffectLeftActive = true;
				needsInvalidate = true;
			}
			else if (canScrollX && currX > ( mSurfaceSizeBuffer.x - mContentRect.width( ) )
					&& mEdgeEffectRight.isFinished( ) && !mEdgeEffectRightActive)
			{
				mEdgeEffectRight.onAbsorb( ( int ) OverScrollerCompat.getCurrVelocity( mScroller ) );
				mEdgeEffectRightActive = true;
				needsInvalidate = true;
			}

			if (canScrollY && currY < 0 && mEdgeEffectTop.isFinished( ) && !mEdgeEffectTopActive)
			{
				mEdgeEffectTop.onAbsorb( ( int ) OverScrollerCompat.getCurrVelocity( mScroller ) );
				mEdgeEffectTopActive = true;
				needsInvalidate = true;
			}
			else if (canScrollY && currY > ( mSurfaceSizeBuffer.y - mContentRect.height( ) )
					&& mEdgeEffectBottom.isFinished( ) && !mEdgeEffectBottomActive)
			{
				mEdgeEffectBottom
						.onAbsorb( ( int ) OverScrollerCompat.getCurrVelocity( mScroller ) );
				mEdgeEffectBottomActive = true;
				needsInvalidate = true;
			}

			float currXRange = AXIS_X_MIN + ( AXIS_X_MAX - AXIS_X_MIN ) * currX
					/ mSurfaceSizeBuffer.x;
			float currYRange = AXIS_Y_MAX - ( AXIS_Y_MAX - AXIS_Y_MIN ) * currY
					/ mSurfaceSizeBuffer.y;
			setViewportBottomLeft( currXRange, currYRange );
		}

		if (mZoomer.computeZoom( ))
		{
			float newWidth = ( 1f - mZoomer.getCurrZoom( ) ) * mScrollerStartViewport.width( );
			float newHeight = ( 1f - mZoomer.getCurrZoom( ) ) * mScrollerStartViewport.height( );
			float pointWithinViewportX = ( mZoomFocalPoint.x - mScrollerStartViewport.left )
					/ mScrollerStartViewport.width( );
			float pointWithinViewportY = ( mZoomFocalPoint.y - mScrollerStartViewport.top )
					/ mScrollerStartViewport.height( );
			mCurrentViewport.set( mZoomFocalPoint.x - newWidth * pointWithinViewportX,
					mZoomFocalPoint.y - newHeight * pointWithinViewportY, mZoomFocalPoint.x
							+ newWidth * ( 1 - pointWithinViewportX ), mZoomFocalPoint.y
							+ newHeight * ( 1 - pointWithinViewportY ) );
			constrainViewport( );
			needsInvalidate = true;
		}

		if (needsInvalidate)
		{
			ViewCompat.postInvalidateOnAnimation( this );
		}
	}

	private void setViewportBottomLeft( float x, float y )
	{
		float curWidth = mCurrentViewport.width( );
		float curHeight = mCurrentViewport.height( );
		x = Math.max( AXIS_X_MIN, Math.min( x, AXIS_X_MAX - curWidth ) );
		y = Math.max( AXIS_Y_MIN + curHeight, Math.min( y, AXIS_Y_MAX ) );

		mCurrentViewport.set( x, y - curHeight, x + curWidth, y );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public RectF getCurrentViewport( )
	{
		return new RectF( mCurrentViewport );
	}

	public void setCurrentViewport( RectF viewport )
	{
		mCurrentViewport = viewport;
		constrainViewport( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public void zoomIn( )
	{
		mScrollerStartViewport.set( mCurrentViewport );
		mZoomer.forceFinished( true );
		mZoomer.startZoom( ZOOM_AMOUNT );
		mZoomFocalPoint.set( ( mCurrentViewport.right + mCurrentViewport.left ) / 2,
				( mCurrentViewport.bottom + mCurrentViewport.top ) / 2 );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public void zoomOut( )
	{
		mScrollerStartViewport.set( mCurrentViewport );
		mZoomer.forceFinished( true );
		mZoomer.startZoom( -ZOOM_AMOUNT );
		mZoomFocalPoint.set( ( mCurrentViewport.right + mCurrentViewport.left ) / 2,
				( mCurrentViewport.bottom + mCurrentViewport.top ) / 2 );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public void panLeft( )
	{
		fling( ( int ) ( -PAN_VELOCITY_FACTOR * getWidth( ) ), 0 );
	}

	public void panRight( )
	{
		fling( ( int ) ( PAN_VELOCITY_FACTOR * getWidth( ) ), 0 );
	}

	public void panUp( )
	{
		fling( 0, ( int ) ( -PAN_VELOCITY_FACTOR * getHeight( ) ) );
	}

	public void panDown( )
	{
		fling( 0, ( int ) ( PAN_VELOCITY_FACTOR * getHeight( ) ) );
	}

	public float getLabelTextSize( )
	{
		return mLabelTextSize;
	}

	public void setLabelTextSize( float labelTextSize )
	{
		mLabelTextSize = labelTextSize;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public int getLabelTextColor( )
	{
		return mLabelTextColor;
	}

	public void setLabelTextColor( int labelTextColor )
	{
		mLabelTextColor = labelTextColor;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public float getGridThickness( )
	{
		return mGridThickness;
	}

	public void setGridThickness( float gridThickness )
	{
		mGridThickness = gridThickness;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public int getGridColor( )
	{
		return mGridColor;
	}

	public void setGridColor( int gridColor )
	{
		mGridColor = gridColor;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public float getAxisThickness( )
	{
		return mAxisThickness;
	}

	public void setAxisThickness( float axisThickness )
	{
		mAxisThickness = axisThickness;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public int getAxisColor( )
	{
		return mAxisColor;
	}

	public void setAxisColor( int axisColor )
	{
		mAxisColor = axisColor;
		initPaints( );
		ViewCompat.postInvalidateOnAnimation( this );
	}

	public float getDataThickness( )
	{
		return mDataThickness;
	}

	public void setDataThickness( float dataThickness )
	{
		mDataThickness = dataThickness;
	}

	public int getDataColor( )
	{
		return mDataColor;
	}

	public void setDataColor( int dataColor )
	{
		mDataColor = dataColor;
	}

	@Override
	public Parcelable onSaveInstanceState( )
	{
		Parcelable superState = super.onSaveInstanceState( );
		SavedState ss = new SavedState( superState );
		ss.viewport = mCurrentViewport;
		return ss;
	}

	@Override
	public void onRestoreInstanceState( Parcelable state )
	{
		if (!( state instanceof SavedState ))
		{
			super.onRestoreInstanceState( state );
			return;
		}

		SavedState ss = ( SavedState ) state;
		super.onRestoreInstanceState( ss.getSuperState( ) );

		mCurrentViewport = ss.viewport;
	}

	public static class SavedState extends BaseSavedState
	{
		private RectF viewport;

		public SavedState( Parcelable superState )
		{
			super( superState );
		}

		@Override
		public void writeToParcel( Parcel out, int flags )
		{
			super.writeToParcel( out, flags );
			out.writeFloat( viewport.left );
			out.writeFloat( viewport.top );
			out.writeFloat( viewport.right );
			out.writeFloat( viewport.bottom );
		}

		@Override
		public String toString( )
		{
			return "InteractiveLineGraphView.SavedState{"
					+ Integer.toHexString( System.identityHashCode( this ) ) + " viewport="
					+ viewport.toString( ) + "}";
		}

		public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat
				.newCreator( new ParcelableCompatCreatorCallbacks<SavedState>( )
				{
					@Override
					public SavedState createFromParcel( Parcel in, ClassLoader loader )
					{
						return new SavedState( in );
					}

					@Override
					public SavedState[ ] newArray( int size )
					{
						return new SavedState[ size ];
					}
				} );

		SavedState( Parcel in )
		{
			super( in );
			viewport = new RectF( in.readFloat( ), in.readFloat( ), in.readFloat( ), in.readFloat( ) );
		}
	}

	private static class AxisStops
	{
		float[ ] stops = new float[ ]
		{};
		int numStops;
		int decimals;
	}
}
