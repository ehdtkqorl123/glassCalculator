package ca.paulshin.glass.calculator.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.speech.RecognizerIntent;
import android.util.Log;
import ca.paulshin.glass.calculator.App;

import com.google.android.glass.app.Card;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends Activity {

	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
		String command = null;
		if (voiceResults != null && voiceResults.size() > 0) {
			command = voiceResults.get(0);
		}

		String result = processCommand(command.toLowerCase());
		showResult(command, result);
	}

	private String processCommand(String command) {
		if (App.DEBUG) Log.d(App.TAG, "Command: " + command);
		
		Calculable calc = null;

		// Pi
//		if (command.equals("pi") || command.equals("hi"))
//			return String.valueOf(Math.PI);

		command = command.replace("plus", "+");
		command = command.replace("minus", "-");
		command = command.replace("times", "*");
		command = command.replace("X", "*");
		command = command.replace("divided by", "/");
		command = command.replace("divide by", "/");
		command = command.replace("square root of", "sqrt");
		command = command.replace("square root", "sqrt");
		command = command.replace("cubic root of", "cbrt");
		command = command.replace("cubic root", "cbrt");
		command = command.replace("to the power of", "^");
		
// The followings are not voice-recognized accurately
//		command = command.replace("modulo", "%");
//		command = command.replace("sine", "sin");
//		command = command.replace("cosine", "cos");
//		command = command.replace("tangent", "tan");
//		command = command.replace("arc sine", "asin");
//		command = command.replace("arc cosine", "acos");
//		command = command.replace("arc tangent", "atan");

		try {
			calc = new ExpressionBuilder(command)
			.withVariable("hi", Math.PI)
			.withVariable("pi", Math.PI)
			.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		double result = calc.calculate();

		if (App.DEBUG) Log.d(App.TAG, "result: " + result);
		if (result == (int)result)
			return String.valueOf((int)result);
		return String.valueOf(result);
	}

	private int factorial(int value) {
		return value == 1 ? 1 : value * factorial(value - 1);
	}
	
	private void showResult(String command, String result) {
		command = command.replace("hi", "Pi");
		
        Card card = new Card(this);
        card.setText(result);
        card.setFootnote(command);
        
        setContentView(card.getView());
	}
}
