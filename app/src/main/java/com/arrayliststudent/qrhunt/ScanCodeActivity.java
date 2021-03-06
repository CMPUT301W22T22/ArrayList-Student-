package com.arrayliststudent.qrhunt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Size;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ScanCodeActivity
 * @author vparab
 * Uses camera and barcode scanner
 * Scans a barcode
 * Displays raw data in bottom left corner
 * Displays score in bottom right corner
 * Takes Picture when button is pressed
 */
public class ScanCodeActivity extends AppCompatActivity implements UploadCodeFragment.OnFragmentInteractionListener, SavedCodeFragment.OnFragmentInteractionListener{
    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView cameraView;
    private MyImageAnalyzer analyzer;
    private TextView showData;
    private TextView showScore;
    private Button takePicture;
    FirebaseStorage storage;
    StorageReference photoColletion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        showData = findViewById(R.id.display_data);
        showData.setText("NO_data");
        showScore = findViewById(R.id.display_score);
        showData.setText("0");
        takePicture = findViewById(R.id.take_Picture);

        cameraView = findViewById(R.id.camerapreview);
        this.getWindow().setFlags(1024, 1024);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture =  ProcessCameraProvider.getInstance(this);
        analyzer = new MyImageAnalyzer();

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try{
                    if(ActivityCompat.checkSelfPermission(ScanCodeActivity.this, Manifest.permission.CAMERA) != (PackageManager.PERMISSION_GRANTED)){
                        ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
                    }else{
                        ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                        bindpreview(processCameraProvider);
                    }
                }catch (ExecutionException e){
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor((this)));

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101 && grantResults.length > 0){
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bindpreview(processCameraProvider);
        }
    }

    private void bindpreview(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(cameraView.getSurfaceProvider());
        ImageCapture imageCapture = new ImageCapture.Builder()
                .setTargetResolution(new Size(1280, 720))
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(cameraExecutor, analyzer);
        processCameraProvider.unbindAll();
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                File photo = capturePhoto(imageCapture);
                new UploadCodeFragment(showData.getText().toString(), Integer.valueOf(showScore.getText().toString()), photo)
                        .show(getSupportFragmentManager(),"Upload");
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private File capturePhoto(ImageCapture imageCapture) {

        File photoDir = new File ("/mnt/sdcard/Pictures/QRHUNT");
        if (!photoDir.exists()){
            photoDir.mkdir();
        }
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        //String photoFilePath = getApplicationContext().getExternalCacheDir() + "/" + timestamp + ".jpg";
        String photoFilePath = photoDir + "/" + timestamp + ".jpg";

        File photoFile = new File(photoFilePath);

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(ScanCodeActivity.this, "Photo has been saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(ScanCodeActivity.this, "Error Photo was not saved: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return photoFile;
    }

    @Override
    public void onUploadPressed(String codeName, String hash, int score, File photo, Boolean location_info, Boolean photo_info) {
        ScannableCode code;
        code =  ScanCodePresenter.createScannableCode(codeName, score, codeName);

        if(photo_info){
            uploadPhoto(code, photo);
        }
        if (location_info){
            code.setLocation(getApplicationContext());
        }
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);
        new SavedCodeFragment(code, photo).show(getSupportFragmentManager(), "Barcode successfully uploaded");

    }

    public void uploadPhoto(ScannableCode code, File photo){
        storage  = FirebaseStorage.getInstance();
        photoColletion = storage.getReference();

        Uri filePath = Uri.fromFile(photo);
        code.setPhotoLink("gs://arraylist-student.appspot.com/" + filePath.toString());
        StorageReference ref = photoColletion.child(filePath.toString());

        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        code.setPhotoLink(uri.toString());
                    }
                });
            }
        });
    }


    /**
     * MyImageAnalyzer
     * Contains methods to perform various tasks for ScanCodeActivity
     */
    public class MyImageAnalyzer implements ImageAnalysis.Analyzer {

        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanbarcode(image);
        }


        private void scanbarcode(ImageProxy image) {

            @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_ALL_FORMATS)
                            .build();

            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            // Task completed successfully
                            readerBarcodeData(barcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            image.close();
                        }
                    });
        }

        private void readerBarcodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                Rect bounds = barcode.getBoundingBox();
                Point[] corners = barcode.getCornerPoints();
                String rawValue = barcode.getRawValue();
                String hash = ScanCodePresenter.getHash(rawValue.getBytes(StandardCharsets.UTF_8));
                int score = ScanCodePresenter.calculateScore(hash);
                showData.setText(hash);
                showScore.setText(Integer.toString(score));

            }
        }
    }
}


