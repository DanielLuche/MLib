package ctrls;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dluche.mlib.R;

/**
 * Created by d.luche on 16/09/2017.
 */

public class CtrlTextViewTest extends LinearLayout{

    private Context context;
    private TextView tv_main;
    private ImageView iv_action;
    private String mValue = "";
    private OnIvActionClickListner onIvActionClickLister;

    public interface OnIvActionClickListner{
        void OnIvActionClick(View view, String text);
    }

    public void setOnIvActionClickLister(OnIvActionClickListner listner) {
        this.onIvActionClickLister = listner;
    }

    public CtrlTextViewTest(Context context) {
        super(context);

        initializeCtrl(context);

    }

    public CtrlTextViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeCtrl(context);
    }

    private void initializeCtrl(Context context) {

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_textview_del,this);
        //IniView
        tv_main = findViewById(R.id.ctrl_textview_del_tv_main);
        //
        iv_action = findViewById(R.id.ctrl_textview_del_iv_action);
        //
        iniAction();

    }

    private void iniAction() {
          tv_main.setText("Inicializado");
          //
          checkIvIconChange();
         //
         iv_action.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {
                if(onIvActionClickLister != null){
                    onIvActionClickLister.OnIvActionClick(view, tv_main.getText().toString());
                }
             }
         });
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        tv_main.setText(mValue);
        this.mValue = mValue;
        //
        checkIvIconChange();
    }

    private void checkIvIconChange() {
        if(mValue.trim().length() == 0){
            iv_action.setImageDrawable(context.getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
            iv_action.setColorFilter(context.getResources().getColor(R.color.mLib_color_green_1));
            iv_action.setBackground(context.getDrawable(R.drawable.mlib_border_green_states));
        }else{
            iv_action.setImageDrawable(context.getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
            iv_action.setColorFilter(context.getResources().getColor(R.color.mLib_color_red_1));
            iv_action.setBackground(context.getDrawable(R.drawable.mlib_border_red_states));
        }
    }

    public void setMEnabled(boolean enabled){
        //
        if(enabled){
            iv_action.setEnabled(enabled);
            checkIvIconChange();
        }else{
            iv_action.setEnabled(enabled);
            iv_action.setColorFilter(context.getResources().getColor(R.color.mLib_color_gray_1));
            iv_action.setBackground(context.getDrawable(R.drawable.mlib_border_disabled));
        }
    }

}
