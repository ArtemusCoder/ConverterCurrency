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

public class bottom_sheet_main extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_main, container, false);
        final Button button_rub = v.findViewById(R.id.rub_button_main);
        Button button_usd = v.findViewById(R.id.usd_button_main);
        Button button_euro = v.findViewById(R.id.euro_button_main);

        button_rub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("rub", "РУБ");
                dismiss();
            }
        });

        button_usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("usd", "USD");
                dismiss();
            }
        });

        button_euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("euro", "EURO");
                dismiss();
            }
        });

        return v;

    }

    public interface BottomSheetListener
    {
        void onButtonClicked(String text, String name);

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
