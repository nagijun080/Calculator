package sample.application.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends Activity {

	String strTemp="";
	String strResult="0";
	int operator=0;
	
	String str;
	Integer num1 = 0,num2 = 0;
	Boolean dot = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    public void numKeyOnClick(View v) {
    	
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
	
	public void functionKeyOnClick() {
		
		
	}
	
	public void operatorKeyOnClick(View v) {
		
		if(operator != 0) {
			if(strTemp.length() > 0) {
				strResult = doCalc();
				showNumber(strResult);
			}
		} else {
			if(strTemp.length() > 0) {
				strResult = strTemp;
			}
		}
		
		strTemp="";
		
		if(v.getId() == R.id.keypadEq) {
			operator = 0;
		} else {
			operator = v.getId();
		}
		
	}

	private String doCalc() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
