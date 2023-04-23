package mz.ac.luis.seia.finacieme.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import mz.ac.luis.seia.finacieme.R;

public class PdfViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        PDFView pdfView = findViewById(R.id.pdfView);
        Bundle bundle = getIntent().getExtras();
        int capitulo = bundle.getInt("capitulo");

        try{
            pdfView.fromAsset("capitulo"+capitulo+".pdf")
                    .pages(0, 2) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .password(null)
                    .scrollHandle(null)
                    .load();
        }catch (NullPointerException e){
            Toast.makeText(this, "Erro ao abrir o ficheiro", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}