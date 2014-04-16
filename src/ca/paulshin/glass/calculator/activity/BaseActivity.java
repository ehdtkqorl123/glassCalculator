package ca.paulshin.glass.calculator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import butterknife.ButterKnife;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

/**
 * Base activity which handles common stuff like analytics.
 */
abstract public class BaseActivity extends Activity {

    private GestureDetector mGestureDetector;
    
    abstract protected void initViews();

    /**
     * Override in children.
     */
    protected int getLayoutId() {
        return -1;
    }

    /**
     * Override in children.
     */
    protected View getLayoutView() {
        return null;
    }

    /**
     * Override in children
     */
    protected void loadData() {

    }

    /**
     * You can override this in child. Default implemention is to open options menu.
     */
    protected void onTap() {
        openOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasUi = false;
        if (getLayoutId() == -1) {
            if (getLayoutView() != null) {
                setContentView(getLayoutView());
                hasUi = true;
            }
        } else {
            setContentView(getLayoutId());
            hasUi = true;
        }
        if (hasUi) {
            ButterKnife.inject(this);
            //TODO
            initViews();
            loadData();
            mGestureDetector = createGestureDetector(this);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (!BuildConfig.DEBUG) {
//            EasyTracker.getInstance(this).activityStart(this);
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (!BuildConfig.DEBUG) {
//            EasyTracker.getInstance(this).activityStop(this);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	loadData();
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    onTap();
                    return true;
                }
                return false;
            }
        });
        return gestureDetector;
    }

    /*
     * Send generic motion events to the gesture detector
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
}
