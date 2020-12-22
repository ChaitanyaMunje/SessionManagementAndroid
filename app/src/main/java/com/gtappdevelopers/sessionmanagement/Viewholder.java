package com.gtappdevelopers.sessionmanagement;

import android.view.View;
import android.widget.TextView;

public class Viewholder {
    TextView textView;

    Viewholder(View view) {
        textView = view.findViewById(R.id.idTvnode);
    }

}
