package ctrls;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dluche.mlib.R;

import java.util.ArrayList;

/**
 * Created by d.luche on 16/09/2017.
 */

public class CtrlContainerList extends LinearLayout {

    private Context context;
    private TextView tv_ttl;
    private EditText et_new_item;
    private ImageView iv_add_item;
    private LinearLayout ll_qty;
    private TextView tv_qty_lbl;
    private TextView tv_qty_val;
    private LinearLayout ll_item_container;



    //propriedades
    private ArrayList<String> originMValue = new ArrayList<>();
    private ArrayList<String> mValue = new ArrayList<>();
    private String mTitle;
    private String mHint;
    private String mQtyLbl;
    private boolean caseSensitive;
    private boolean acceptDuplicatedItem;
    //interfaces
    private OnAddClickListner onAddClickListner;
    private CtrlTextViewDel.OnIvActionClickListner removeListner;

    public CtrlContainerList(Context context) {
        super(context);
        //
        initialeCtrl(context);
    }

    public CtrlContainerList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //
        initialeCtrl(context);
    }

    //Interface Add
    public interface OnAddClickListner{
        void OnAddClick(String new_item);
    }

    public void setAddClickListner(@Nullable OnAddClickListner listner) {
        this.onAddClickListner = listner;
    }

    private void initialeCtrl(Context context) {
        this.context = context;
        mTitle="";
        mHint="";
        mQtyLbl="";
        caseSensitive = false;
        acceptDuplicatedItem = false;
        removeListner = new CtrlTextViewDel.OnIvActionClickListner() {
            @Override
            public void OnIvActionClick(View view, String text) {
                mValue.remove(text);
                //
                ll_item_container.removeView(view);
            }
        };
        //Seta Layout do component
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_container_list,this,true);

        //Inicializa views do componens
        iniInnerViews();
        //
        iniInnerViewsAction();

    }

    private void iniInnerViews() {
        tv_ttl = findViewById(R.id.ctrl_container_list_tv_ttl);
        //
        et_new_item = findViewById(R.id.ctrl_container_list_et_new_item);
        //
        iv_add_item = findViewById(R.id.ctrl_container_list_iv_add_item);
        //
        ll_qty = findViewById(R.id.ctrl_container_list_ll_qty);
        //
        tv_qty_lbl = findViewById(R.id.ctrl_container_list_tv_qty_lbl);
        //
        tv_qty_val = findViewById(R.id.ctrl_container_list_tv_qty_val);
        //
        ll_item_container = findViewById(R.id.ctrl_container_list_ll_container);
    }

    private void iniInnerViewsAction() {
        //
        iv_add_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_item = et_new_item.getText().toString().trim();
                if (new_item.length() > 0) {

                    if (acceptDuplicatedItem) {
                        appendItem(new_item);
                    } else {
                        if (checkItemExists(new_item)) {
                            Toast.makeText(context, "Ja existe", Toast.LENGTH_SHORT).show();
                        } else {
                            appendItem(new_item);
                        }
                    }
                    //Limpa valor do Et
                    et_new_item.setText("");
                }else{

                }
            }
        });
        //
    }

    private void appendItem(String new_item) {
        CtrlTextViewDel viewDel = new CtrlTextViewDel(context);
        viewDel.setmValue(new_item);
        viewDel.setOnIvActionClickLister(removeListner);
        //
        mValue.add(new_item);
        ll_item_container.addView(viewDel);
        tv_qty_val.setText(String.valueOf(mValue.size()));
    }

    private boolean checkItemExists(String new_item) {
        for (int i = 0; i < mValue.size() ; i++) {
            if (caseSensitive) {
                if(new_item.equals(mValue.get(i))){
                    return true;
                }
            }else{
                if(new_item.equalsIgnoreCase(mValue.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getmValue() {
        return mValue;
    }

    public void setmValue(ArrayList<String> mValue, boolean... default_val) {
        this.mValue = mValue;
        //
        if(default_val[0]){
            originMValue.addAll(mValue);
        }
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle.trim();
        tv_ttl.setText(mTitle);
    }

    public void setmHint(String mHint) {
        this.mHint = mHint.trim();
        et_new_item.setHint(mHint);
    }

    public void setmQtyLbl(String mQtyLbl) {
        this.mQtyLbl = mQtyLbl.trim();
        tv_qty_lbl.setText(mQtyLbl);
    }

    public boolean isAcceptDuplicatedItem() {
        return acceptDuplicatedItem;
    }

    public void setAcceptDuplicatedItem(boolean acceptDuplicatedItem) {
        this.acceptDuplicatedItem = acceptDuplicatedItem;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public int itemCount(){
        return mValue.size();
    }

    public boolean hasChanged(){
        //
        if(originMValue.size() != mValue.size()){
            return true;
        }
        //
        for (int i = 0; i < originMValue.size(); i++) {
            boolean itemExists = false;
            //
            for (int j = 0; j < mValue.size() ; j++) {
                if(caseSensitive) {
                    if (originMValue.get(i).equals(mValue.get(j))) {
                        itemExists = true;
                        break;
                    }
                }else{
                    if (originMValue.get(i).equalsIgnoreCase(mValue.get(j))) {
                        itemExists = true;
                        break;
                    }
                }
            }
            //
            if(!itemExists){
               return true;
            }
        }
        //
        return false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        //
        ss._originMValue = originMValue;
        ss._mValue = mValue;
        ss._mTitle = mTitle;
        ss._mHint = mHint;
        ss._mQtyLbl = mQtyLbl;
        ss._caseSensitive = caseSensitive;
        ss._acceptDuplicatedItem = acceptDuplicatedItem;

        return ss;

    }

    private static class SavedState extends BaseSavedState{

        private ArrayList<String> _originMValue = new ArrayList<>();
        private ArrayList<String> _mValue = new ArrayList<>();
        private String _mTitle;
        private String _mHint;
        private String _mQtyLbl;
        private boolean _caseSensitive;
        private boolean _acceptDuplicatedItem;
        private LinearLayout _ll_item_container;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);

            _originMValue = in.createStringArrayList();
            _mValue = in.createStringArrayList();
            _mTitle = in.readString();
            _mHint = in.readString();
            _mQtyLbl = in.readString();
            _caseSensitive = in.readInt() == 1;
            _acceptDuplicatedItem = in.readInt() == 1;
            //_ll_item_container = in.readTypedObject(LinearLayout.class);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            //
            out.writeList(_originMValue);
            out.writeList(_mValue);
            out.writeString(_mTitle);
            out.writeString(_mHint);
            out.writeString(_mQtyLbl);
            out.writeInt(_caseSensitive ? 1 : 0);
            out.writeInt(_acceptDuplicatedItem ? 1 : 0);
            out.writeValue(_ll_item_container);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
