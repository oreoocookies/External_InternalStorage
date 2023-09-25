package com.example.externalinternalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class EksternalActivity extends AppCompatActivity {
    public static final String FILENAME = "filex.txt"; // Nama file yang ingin Anda operasikan
    public static final int REQUEST_CODE_STORAGE = 100;

    Button buatFile, ubahFile, bacaFile, deleteFile;
    public int selectEvent = 0;

    TextView TextBaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eksternal);

        buatFile = findViewById(R.id.buttonBuatFileEksternal);
        ubahFile = findViewById(R.id.buttonUbahFileEksternal);
        bacaFile = findViewById(R.id.buttonBacaFileEksternal);
        deleteFile = findViewById(R.id.buttonHapusFileEksternal);
        TextBaca = findViewById(R.id.textBacaEksternal);

        buatFile.setOnClickListener(this::onClick);
        ubahFile.setOnClickListener(this::onClick);
        bacaFile.setOnClickListener(this::onClick);
        deleteFile.setOnClickListener(this::onClick);
    }

    private boolean periksaIzinPenyimpanan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    jalankanPerintah(selectEvent);
                } else {
                    Toast.makeText(this, "Izin penyimpanan ditolak.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void buatFile() {
        String isiFile = "Halo ini isi file eksternal";
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file, true);
                outputStream.write(isiFile.getBytes());
                outputStream.flush();
                outputStream.close();

                Toast.makeText(this, "File eksternal berhasil dibuat", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Tidak dapat mengakses eksternal storage.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ubahFile() {
        String isiFile = "Sudah diubah :)";
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false); // Mode append
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "File eksternal berhasil diubah", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bacaFile() {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, FILENAME);
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
                Toast.makeText(this, "File eksternal berhasil dibaca", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextBaca.setText(text.toString());
        }
    }

    private void hapusFile() {
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        if (file.exists()) {
            file.delete();
            Toast.makeText(this, "File eksternal berhasil dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    private void jalankanPerintah(int id) {
        if (id == R.id.buttonBuatFileEksternal) {
            buatFile();
        } else if (id == R.id.buttonBacaFileEksternal) {
            bacaFile();
        } else if (id == R.id.buttonUbahFileEksternal) {
            ubahFile();
        } else if (id == R.id.buttonHapusFileEksternal) {
            hapusFile();
        }
    }

    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.buttonBuatFileEksternal || viewId == R.id.buttonBacaFileEksternal || viewId == R.id.buttonUbahFileEksternal || viewId == R.id.buttonHapusFileEksternal) {
            if (periksaIzinPenyimpanan()) {
                selectEvent = viewId;
                jalankanPerintah(viewId);
            }
        }
    }
}
