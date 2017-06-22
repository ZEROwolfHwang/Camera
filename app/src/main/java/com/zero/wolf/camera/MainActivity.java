package com.zero.wolf.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果

    public static final String IMAGE_UNSPECIFIED = "image/*";
    ImageView imageView = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);


        /*button0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTOZOOM);
            }
        });

        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
                startActivityForResult(intent, PHOTOHRAPH);
            }
        });*/
    }

    public void shut(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTOHRAPH);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件
                imageView.setImageBitmap(photo);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }

    /*private String mFilePath;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" +System.currentTimeMillis()+".jpg";

        mImageView = (ImageView) findViewById(R.id.image_view);

    }

    public void shut(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f=new File(dir, localTempImgFileName);//localTempImgDir和localTempImageFileName是自己定义的名字
        Uri u=Uri.fromFile(f);
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        startActivityForResult(intent, 1);

    }

    *//*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
               *//**//* Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
                *//**//*
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }*//*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK )
        {
            switch(requestCode)
            {
                case 1:
                    File f=new File(Environment.getExternalStorageDirectory()
                            +"/"+localTempImgDir+"/"+localTempImgFileName);
                    try {
                        Uri u =
                                Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
                                        f.getAbsolutePath(), null, null));
                        //u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
