package com.artemus.currency;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottom_sheet_second extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_second, container, false);
        Button button_rub = v.findViewById(R.id.rub_button_second);
        Button button_usd = v.findViewById(R.id.usd_button_second);
        Button button_euro = v.findViewById(R.id.euro_button_second);

        button_rub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedSecond("rub", "RUB");
                dismiss();
            }
        });

        button_usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedSecond("usd", "USD");
                dismiss();
            }
        });

        button_euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedSecond("euro", "EURO");
                dismiss();
            }
        });

        return v;

    }

    public interface BottomSheetListener
    {
        void onButtonClickedSecond(String text, String name);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()
                    + "must implement BottomSheetListener");
        }

    }
}
