package com.yar.datargx.Utils;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public class DateTextInputUtils implements TextWatcher {
    private EditText inputEditText;
    private String current = "";
    private String mmddyyyy = "MMDDYYYY";
    private Calendar cal = Calendar.getInstance();

    public DateTextInputUtils(EditText inputEditText) {
        this.inputEditText = inputEditText;
        this.inputEditText.addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
            String cleanC = current.replaceAll("[^\\d.]|\\.", "");
            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanC)) sel--;
            if (clean.length() < 8) {
                clean = clean + mmddyyyy.substring(clean.length());
            } else {
                int mon = Integer.parseInt(clean.substring(0, 2));
                int day = Integer.parseInt(clean.substring(2, 4));
                int year = Integer.parseInt(clean.substring(4, 8));
                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                cal.set(Calendar.MONTH, mon - 1);
                year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                cal.set(Calendar.YEAR, year);
                day = (day > cal.getActualMaximum(Calendar.DATE)) ?
                        cal.getActualMaximum(Calendar.DATE) : day;
                clean = String.format("%02d%02d%02d", mon, day, year);
            }
            clean = String.format("%s-%s-%s", clean.substring(0, 2), clean.substring(2, 4),
                    clean.substring(4, 8));
            sel = sel < 0 ? 0 : sel;
            current = clean;
            if (current.equalsIgnoreCase("mm-dd-yyyy")) current = "";
            inputEditText.setText(current);
            inputEditText.setSelection(sel < current.length() ? sel : current.length());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
