package com.dluche.mlib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

import ctrls.CtrlContainerList;
import ctrls.CtrlTextViewTest;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private CtrlTextViewTest viewDel;
    private EditText et_text;
    private CtrlContainerList containerList;
    private Switch sw_dup;
    private Switch sw_case;
    private Button btn_check_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniTest();
    }

    private void iniTest() {
        context = this;

        et_text = (EditText) findViewById(R.id.main_et_text);
        et_text.setVisibility(View.GONE);
        //
        viewDel = (CtrlTextViewTest) findViewById(R.id.main_ctrl_001);
        viewDel.setVisibility(View.GONE);
        //
        //
        viewDel.setmValue("Daniel");
        //
//        viewDel.setOnIvActionClickLister(new CtrlTextViewTest.OnIvActionClickListner() {
//            @Override
//            public void OnIvActionClick(View view, String text) {
//               // Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                if(text.equalsIgnoreCase("disable")){
//                    viewDel.setMEnabled(false);
//                }else {
//                    if (text.trim().length() > 0) {
//                        viewDel.setmValue("");
//                    } else {
//                        viewDel.setmValue(et_text.getText().toString().trim());
//                    }
//                }
//            }
//        });
        sw_dup = (Switch) findViewById(R.id.main_sw_dup);

        sw_case = (Switch) findViewById(R.id.main_sw_case);

        sw_dup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                containerList.setAcceptDuplicatedItem(b);
            }
        });
        //
        sw_case.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                containerList.setCaseSensitive(b);
            }
        });
        //
        btn_check_val = (Button) findViewById(R.id.main_btn_check_val);
        //
        btn_check_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String>tst = containerList.getmValue();

                tst.size();
            }
        });

        //
        containerList = (CtrlContainerList) findViewById(R.id.main_ctrl_002);
        //
        containerList.setmTitle("Titulo Noob");
        containerList.setmHint("Digite Aqui");
        containerList.setmQtyLbl("Qtd");
        containerList.setCaseSensitive(sw_case.isChecked());
        containerList.setAcceptDuplicatedItem(sw_dup.isChecked());

    }
}
