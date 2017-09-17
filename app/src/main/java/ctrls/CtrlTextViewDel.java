package ctrls;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

public class CtrlTextViewDel extends LinearLayout{

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

    public CtrlTextViewDel(Context context) {
        super(context);

        initializeCtrl(context);

    }

    public CtrlTextViewDel(Context context, @Nullable AttributeSet attrs) {
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
         //
         iv_action.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {
                showConfirmDialog(CtrlTextViewDel.this);
             }
         });
    }

    private void showConfirmDialog(final CtrlTextViewDel ctrlTextViewDel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setTitle("Deseja remover o item ?")
                .setMessage("Deseja realmente excluir esse item?\nEssa ação é irreverssivel !")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(onIvActionClickLister != null){
                            onIvActionClickLister.OnIvActionClick(ctrlTextViewDel,tv_main.getText().toString().trim());
                        }
                    }
                })
                .setNegativeButton("Não",null);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        tv_main.setText(mValue);
        this.mValue = mValue;
    }

    private static class SavedState extends BaseSavedState{

        public SavedState(Parcelable superState) {
            super(superState);
        }
    }

}
