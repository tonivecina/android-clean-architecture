package dev.tonivecina.cleanarchitecture.views.textviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Toni Vecina on 7/2/17.
 */

public final class DateTextView extends AppCompatTextView {

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //region Setters
    public void setDate(final Date date, final String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String dateFormatted = simpleDateFormat.format(date);

        setText(dateFormatted);
    }
    //endregion
}
