package mz.ac.luis.seia.finacieme.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private int mCardHeight;

    public CustomItemDecoration(int cardHeight) {
        mCardHeight = cardHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mCardHeight * (parent.getAdapter().getItemCount() - 1);
        } else {
            outRect.bottom = mCardHeight;
        }
    }
}

