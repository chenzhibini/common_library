package com.hdyg.common.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hdyg.common.widget.PhotoShowDialog;
import java.util.List;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.activity.MediaActivity;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.Logger;
import cn.finalteam.rxgalleryfinal.utils.PermissionCheckUtils;

/**
 * Created by Administrator on 2018/6/5.
 * 拍照、选择 - 工具类
 */

public class TakePhotoUtil {

    /**
     * 单例对象实例
     */
    private static class TakePhotoHolder {
        static final TakePhotoUtil INSTANCE = new TakePhotoUtil();
    }

    public static TakePhotoUtil getInstance() {
        return TakePhotoHolder.INSTANCE;
    }

    /**
     * private 的构造方法，避免外界直接使用new 来初始化对象
     */
    private TakePhotoUtil() {
    }

    /**
     * 拍照不裁剪
     * @param mActivity
     */
    public void openZKCamera(Object mActivity){
        if (PermissionCheckUtils.checkCameraPermission((Activity) mActivity, "", MediaActivity.REQUEST_CAMERA_ACCESS_PERMISSION)) {
            RxGalleryFinalApi.openZKCamera((Activity) mActivity);
        }
//        onActivityResult调用
//        case RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE://拍照回调
//        RxGalleryFinalApi.openZKCameraForResult(this, new MediaScanner.ScanCallback() {
//            @Override
//            public void onScanCompleted(String[] strings) {
//                LogUtils.d("拍照成功,图片存储路径:%s==>"+strings[0]);
//                        Logger.d("演示拍照后进行图片裁剪，根据实际开发需求可去掉上面的判断");
//                        RxGalleryFinalApi.cropScannerForResult(MainActivity.this, RxGalleryFinalApi.getModelPath(), strings[0]);//调用裁剪.RxGalleryFinalApi.getModelPath()为默认的输出路径
//            }
//        });
//        break;
    }

    /**
     * 拍照并裁剪
     * @param mActivity
     * @param onSuccess
     */
    public void openZKCameraCrop(final Activity mActivity,final OnTakePhotoListener onSuccess){
        SimpleRxGalleryFinal.get().init(
                new SimpleRxGalleryFinal.RxGalleryFinalCropListener() {
                    @NonNull
                    @Override
                    public Activity getSimpleActivity() {
                        return mActivity;
                    }

                    @Override
                    public void onCropCancel() {
                        Toast.makeText(getSimpleActivity(), "裁剪被取消", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCropSuccess(@Nullable Uri uri) {
                        onSuccess.onSuccess(uri);//回传Uri
                        Toast.makeText(getSimpleActivity(), "裁剪成功：" + uri, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCropError(@NonNull String errorMessage) {
                        Toast.makeText(getSimpleActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        ).openCamera();
//        onActivityResult调用
//        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 照片 - 单选 并裁剪
     *
     * @param mContext
     * @param onSuccess
     */
    public void selectPhotoCropSingle(Context mContext, final OnTakePhotoListener onSuccess) {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance((Activity) mContext);
        instance.openGalleryRadioImgDefault(
                new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        LogUtils.d("只要选择图片就会触发");
                    }
                })
                .onCropImageResult(
                        new IRadioImageCheckedListener() {
                            @Override
                            public void cropAfter(Object t) {
                                onSuccess.onSuccess(t);

                            }

                            @Override
                            public boolean isActivityFinish() {
                                LogUtils.d("返回false不关闭，返回true则为关闭");
                                return true;
                            }
                        });
    }

    /**
     * 照片 - 多选
     *
     * @param mContext
     * @param onTakeMultPhotoListener
     */
    public void selectPhotoMult(Context mContext, final OnTakeMultPhotoListener onTakeMultPhotoListener) {
        RxGalleryFinalApi
                .getInstance((Activity) mContext)
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_MULTI)
                .setImageMultipleResultEvent(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        Logger.i("多选图片的回调");
                        List<MediaBean> list = imageMultipleResultEvent.getResult();//获取到多选图片的集合
                        onTakeMultPhotoListener.onSuccess(list);
                    }
                }).open();
    }

    /**
     * 视频单选
     *
     * @param mContext
     * @param onSuccess
     */
    public void selectVideoSingle(Context mContext, final OnTakePhotoListener onSuccess) {
        RxGalleryFinalApi
                .getInstance((Activity) mContext)
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                .setVDRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        LogUtils.d("单选视频回调===" + imageRadioResultEvent.getResult().toString());
                        onSuccess.onSuccess(imageRadioResultEvent.getResult().getOriginalPath());//视频路径
                    }
                })
                .open();
    }

    /**
     * 视频多选
     *
     * @param mContext
     * @param onTakeMultPhotoListener
     */
    public void selectVideoMult(Context mContext, final OnTakeMultPhotoListener onTakeMultPhotoListener) {
        RxGalleryFinalApi
                .getInstance((Activity) mContext)
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_MULTI)
                .setVDMultipleResultEvent(
                        new RxBusResultDisposable<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                List<MediaBean> list = imageMultipleResultEvent.getResult();
                                onTakeMultPhotoListener.onSuccess(list);
                            }
                        }).open();
    }

    /**
     * 自定义多选 - 照片
     * 最多9张
     * @param mContext
     * @param count
     * @param onTakeMultPhotoListener
     */
    public void sustomPhotoMult(final Context mContext, int count, final OnTakeMultPhotoListener onTakeMultPhotoListener) {
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(mContext)
                .image()
                .multiple();
        rxGalleryFinal.maxSize(count)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        List<MediaBean> list = imageMultipleResultEvent.getResult();
                        onTakeMultPhotoListener.onSuccess(list);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(mContext, "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }

    /**
     *  查看大图
     * @param mContext 上下文
     * @param datas 图片数据
     * @param pos 当前位置
     */
    public void selectLargerImage(Context mContext,List<String> datas,int pos){
        PhotoShowDialog photoShowDialog = new PhotoShowDialog(mContext,datas,pos);
        photoShowDialog.show();
    }

    /**
     * 拍照正常回调 单张
     */
    public interface OnTakePhotoListener {
        void onSuccess(Object obj);
    }

    /**
     * 选择照片 多选
     */
    public interface OnTakeMultPhotoListener {
        void onSuccess(List<MediaBean> list);
    }

    /**
     * 拍照异常
     */
    public interface OnTakePhotoExeceptionListener {
        void onExeception();
    }
}
