package sample.application.calculator;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity {

	public String strTemp="";
	public String strResult="0";
	public Integer operator=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        readPreferences();
    }

    @Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		writePreferences();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    public void numKeyOnClick(View v) {
    	((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
    	
    	TextView sp = (TextView)findViewById(R.id.subPanel);
    	String strSp = sp.getText().toString();
    	if (strSp.indexOf("=") == strSp.length() - 1) sp.setText("");
    	
    	String strInkey = ((Button)v).getText().toString();
    	
    	if(strInkey.equals(".")) {	//定数.equalsでやったほうがnullが入った場合おかしくなる
    		if (this.strTemp.length() == 0) {
    			this.strTemp="0.";
    		} else {
    			if(this.strTemp.indexOf(".") == -1) {
    				this.strTemp = this.strTemp+".";
    			}
    		}
    	} else {
    		this.strTemp = this.strTemp+strInkey;
    	}
    	//TODO　インスタンス変数わたしとるわ
    	this.showNumber(this.strTemp);
    }

	private void showNumber(String strNum) {
		//this.strTemp
		DecimalFormat form = new DecimalFormat("#,##0");
		String strDecimal = "";
		String strInt = "";
		String fText = "";
		
		if (strNum.length() > 0) {
			int decimalPoint = strNum.indexOf(".");
			if(decimalPoint > -1) {
				strDecimal = strNum.substring(decimalPoint);
				strInt = strNum.substring(0,decimalPoint);
			} else {
				strInt = strNum;
			}
			fText = form.format(Double.parseDouble(strInt)) + strDecimal;
		} else {
			fText = "0";
		}
		((TextView)findViewById(R.id.displayPanel)).setText(fText);
	}
	
	public void functionKeyOnClick(View v) {
		
		((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
		
		switch(v.getId()) {
		case R.id.keypadAC:
			strTemp = "";
			strResult = "0";
			operator = 0;
			((TextView)findViewById(R.id.subPanel)).setText("");
			break;
		case R.id.keypadC:
			strTemp = "";
			break;
		case R.id.keypadBC:
			if (strTemp.length() == 0) {
				return;
			} else {
				strTemp = strTemp.substring(0,strTemp.length() - 1);
			}
			break;
		case R.id.keypadCopy:
			ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			cm.setText(((TextView)findViewById(R.id.displayPanel)).getText());
			return;
		}
		showNumber(strTemp);
	}
	
	public void operatorKeyOnClick(View v) {
		
		((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
		
		TextView sp = (TextView) findViewById(R.id.subPanel);
		String op2 = ((Button)findViewById(v.getId())).getText().toString();
		
		if(this.operator != 0) {
			String op1 = ((Button)findViewById(operator)).getText().toString();
			if(this.strTemp.length() > 0) {
				sp.setText(strResult + op1 + strTemp + op2);
				this.strResult = this.doCalc();
				this.showNumber(this.strResult);
			} else {
				sp.setText(strResult + op2);
			}
		} else {
			if(this.strTemp.length() > 0) {
				this.strResult = this.strTemp;
			}
			sp.setText(strResult + op2);
		}
		
		this.strTemp="";//String型の長さ０のインスタンスが入っている。
		
		if(v.getId() == R.id.keypadEq) {
			this.operator = 0;
		} else {
			this.operator = v.getId();
		}
		
	}

	private String doCalc() {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal bd1 = new BigDecimal(strResult);
		BigDecimal bd2 = new BigDecimal(strTemp);
		BigDecimal result = BigDecimal.ZERO;
		
		switch(operator) {
		case R.id.keypadAdd:
			result = bd1.add(bd2);
			break;
		case R.id.keypadSub:
			result = bd1.subtract(bd2);
			break;
		case R.id.keypadMulti:
			result = bd1.multiply(bd2);
			break;
		case R.id.keypadDiv:
			if(!bd2.equals(BigDecimal.ZERO)) {
				result = bd1.divide(bd2,12,3);
			} else {
				Toast toast = Toast.makeText(this,R.string.toast_div_by_zero,1000);
				toast.show();
			}
			break;
		}
		
		if (result.toString().indexOf(".") >= 0) {
			return result.toString().replace("¥¥.0+$|0+$", "");
		} else {
			return result.toString();
		}
	}
	
	public void writePreferences() {
		SharedPreferences prefs = getSharedPreferences("CalcPrefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("strTemp", strTemp);
		editor.putString("strResult", strResult);
		editor.putInt("operator", operator);
		editor.putString("strDisplay", ((TextView) findViewById(R.id.displayPanel)).getText().toString());
		editor.putString("strSubDfisplay",((TextView)findViewById(R.id.subPanel)).getText().toString());
		editor.commit();
	}
	
	public void readPreferences() {
		SharedPreferences prefs = getSharedPreferences("CalcPrefs",MODE_PRIVATE);
		strTemp = prefs.getString("strTemp", "");
		strResult = prefs.getString("strResult", "0");
		operator = prefs.getInt("operator", 0);
		((TextView)findViewById(R.id.displayPanel)).setText(prefs.getString("strDisplay","0"));
		((TextView)findViewById(R.id.subPanel)).setText(prefs.getString("strSubDisplay","0"));
	}

}
