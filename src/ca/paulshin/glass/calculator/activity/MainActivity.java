package ca.paulshin.glass.calculator.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.speech.RecognizerIntent;
import android.util.Log;
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
		

		processCommand(command);
	}

	private String processCommand(String command) {
		Calculable calc = null;

		// Pi
		if (command.equalsIgnoreCase("Pi"))
			return String.valueOf(Math.PI);

		// Integer Factorial - eg. 3 factorial
		if (command.contains("factorial")) {
			String numberStr = command.substring(0, command.indexOf(" "));
			int number = Integer.parseInt(numberStr);
			return String.valueOf(factorial(number));
		}

		command = command.replace("plus", "+").replace("Plus", "+");
		command = command.replace("minus", "-").replace("Minus", "-");
		command = command.replace("times", "*").replace("Times", "*");
		command = command.replace("X", "*").replace("x", "*");
		command = command.replace("divided by", "/").replace("Divided by", "/");
		command = command.replace("divide by", "/").replace("Divide by", "/");
		command = command.replace("square root of", "sqrt").replace("Square root of", "/");
		command = command.replace("square root", "sqrt").replace("Square root", "/");
		command = command.replace("cubic root of", "cbrt").replace("Cubic root of", "/");
		command = command.replace("cubic root", "cbrt").replace("Cubic root", "/");
		command = command.replace("to the power of", "^").replace("To the power of", "^");
		
//		command = command.replace("modulo", "%").replace("Modulo", "%");
//		command = command.replace("sine", "sin");
//		command = command.replace("cosine", "cos");
//		command = command.replace("tangent", "tan");
//		command = command.replace("arc sine", "asin");
//		command = command.replace("arc cosine", "acos");
//		command = command.replace("arc tangent", "atan");
		
		Log.d("log", "command: " + command);

		try {
			calc = new ExpressionBuilder(command).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		double result = calc.calculate();
		Log.d("log", "result: " + result);
		return "";
	}

	private int factorial(int value) {
		return value == 1 ? 1 : value * factorial(value - 1);
	}
}
