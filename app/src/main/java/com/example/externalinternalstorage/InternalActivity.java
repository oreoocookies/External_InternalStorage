package com.example.externalinternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import android.os.Bundle;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FILENAME = "namafile.txt";
    Button buatFile, ubahFile, bacaFile, deleteFile;
    TextView textBaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        buatFile = findViewById(R.id.buttonBuatFile);
        ubahFile = findViewById(R.id.buttonUbahFile);
        bacaFile = findViewById(R.id.buttonBacaFile);
        deleteFile = findViewById(R.id.buttonHapusFile);
        textBaca = findViewById(R.id.textBaca);

        buatFile.setOnClickListener(this);
        ubahFile.setOnClickListener(this);
        bacaFile.setOnClickListener(this);
        deleteFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        jalankanPerintah(v.getId());
    }

    public void jalankanPerintah(int id) {
        if (id == R.id.buttonBuatFile) {
            buatFile();
        } else if (id == R.id.buttonBacaFile) {
            bacaFile();
        } else if (id == R.id.buttonUbahFile) {
            ubahFile();
        } else if (id == R.id.buttonHapusFile) {
            hapusFile();
        }
    }

    public void buatFile() {
        String isiFile = "Coba Isi Data File Text";
        File file = new File(this.getFilesDir(), FILENAME);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bacaFile() {
        File file = new File(this.getFilesDir(), FILENAME);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textBaca.setText(text.toString());
    }

    public void ubahFile() {
        String isiFileBaru = "Ini adalah isi file yang telah diubah.";

        File file = new File(this.getFilesDir(), FILENAME);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file, false); // false untuk menghapus konten sebelum menulis ulang
            outputStream.write(isiFileBaru.getBytes());
            outputStream.flush();
            outputStream.close();

            textBaca.setText("File telah diubah.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hapusFile() {
        File file = new File(this.getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
            textBaca.setText("File dihapus.");
        } else {
            textBaca.setText("File tidak ditemukan.");
        }
    }
}