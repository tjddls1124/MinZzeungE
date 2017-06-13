package com.example.sungin.minzzeunge;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main3Activity_enroll extends AppCompatActivity {
    Spinner spn;
    Uri mImageCaptureUri;
    static final int PICK_FROM_CAMERA = 0;
    static final int PICK_FROM_ALBUM = 1;
    static final int CROP_FROM_iMAGE = 2;
    ImageView imageView;
    boolean SettingPhoto = false;
    Bitmap photo;
    String min_kind;
    String fileDir;
    Person enrollPerson = null;

    /*
    사진앨범이나 카메라를 이용하여 사진정보를 받아오는 법은 인터넷을 통해 소스코드를 가져와 변형하였습니다
    출처 : http://jeongchul.tistory.com/287
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_enroll);

        imageView = (ImageView) findViewById(R.id.imageView_photo);
        Intent intent = getIntent();
        enrollPerson = intent.getParcelableExtra("MSG_PERSONDATA");


        final String[] kind = new String[]{"주민등록증", "운전면허증", "학생증"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kind);
        spn = (Spinner) findViewById(R.id.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        spn.setPrompt("신분증 종류를 선택 하세요");


    }

    public void onClick(View v) {
        if (v.getId() == R.id.imageView_photo) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            String[] s = {"사진 앨범", "카메라", "취소"};
            dlg.setTitle("업로드 할 신분증 사진선택");
            dlg.setItems(s, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0)
                        doTakeAlbumAction();
                    if (which == 1)
                        doTakePhotoAction();
                    if (which == 2)
                        dialog.dismiss();
                }
            })
                    .show();
        }
        if (v.getId() == R.id.button_enroll) {
            if (SettingPhoto) {
                min_kind = (String) spn.getSelectedItem();
                Intent intent = new Intent(Main3Activity_enroll.this, Main4Activity_list.class);

                enrollPerson.kind = min_kind;
                enrollPerson.filePath = fileDir;
//                Toast.makeText(getApplicationContext(),enrollPerson.filePath,Toast.LENGTH_SHORT).show();
                intent.putExtra("remakemsg",enrollPerson);
                setResult(RESULT_OK,intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "신분증 사진을 등록하세요", Toast.LENGTH_SHORT).show();

        }
        if(v.getId()==R.id.button_cancel){
            Intent intent = getIntent();
            finish();
        }



    }

    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    public void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MinZzeungE";
        File directory_MinZzeungE = new File(dirPath);
        if (!directory_MinZzeungE.exists()) //디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우)
            directory_MinZzeungE.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);


            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("MinZzeungE", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 300); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 300); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE: {

                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/MinZzeungE/" + System.currentTimeMillis() + ".png";
                fileDir = getFilesDir()+""+System.currentTimeMillis()+".png";



                if (extras != null) {
                    photo = extras.getParcelable("data"); // CROP된 BITMAP
                    imageView.setImageBitmap(photo); // 레이아웃의 이미지뷰에 CROP된 BITMAP을 보여줌
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(fileDir);
                        photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    storeCropImage(photo,filePath);

                    SettingPhoto = true;
                    break;
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }

    }
}
