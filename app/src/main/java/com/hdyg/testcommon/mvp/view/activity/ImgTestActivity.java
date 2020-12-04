package com.hdyg.testcommon.mvp.view.activity;

import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.SimpleRxGalleryFinal;
import com.hdyg.common.util.TakePhotoUtil;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.testcommon.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.OnClick;

/**
 * @author CZB
 * @describe
 * @time 2020/9/18 11:45
 */
public class ImgTestActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.img_test;
    }

    @Override
    protected void initView() {
        setTopTitle("照片选择器演示");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }
    List<String> paths;
    @OnClick({R.id.tv_img_1, R.id.tv_img_2, R.id.tv_img_3, R.id.tv_img_4, R.id.tv_img_5,
            R.id.tv_img_6, R.id.tv_img_7, R.id.tv_img_8})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_img_1: //照片单选裁剪
                TakePhotoUtil.getInstance().selectPhotoCropSingle(mContext, obj -> ToastUtil.show("照片路径-->" + obj.toString()));
                break;
            case R.id.tv_img_2: //照片多选
                paths = new ArrayList<>();
                TakePhotoUtil.getInstance().selectPhotoMult(mContext, list -> {
                    for (int i=0; i<list.size(); i++){
                        paths.add(list.get(i).getOriginalPath());
                        LogUtils.d("图片path---->"+list.get(i).getOriginalPath());
                    }
                });
                break;
            case R.id.tv_img_3: //拍照不裁剪
                //有问题  暂时不用
                ToastUtil.show("有问题  暂时不用");
//                TakePhotoUtil.getInstance().openZKCamera(mContext);
                break;
            case R.id.tv_img_4: //拍照并裁剪
                TakePhotoUtil.getInstance().openZKCameraCrop(this,obj -> ToastUtil.show("照片path-->" + obj.toString()));
                break;
            case R.id.tv_img_5: //视频单选
                TakePhotoUtil.getInstance().selectVideoSingle(mContext,obj -> ToastUtil.show("视频路径-->" + obj.toString()));
                break;
            case R.id.tv_img_6: //视频多选
                TakePhotoUtil.getInstance().selectVideoMult(mContext,list -> {
                    for (int i=0; i<list.size(); i++){
                        LogUtils.d("视频path---->"+list.get(i).getOriginalPath());
                    }
                });
                break;
            case R.id.tv_img_7: //自定义照片多选
                int max = 9;
                TakePhotoUtil.getInstance().sustomPhotoMult(mContext,max,list -> {
                    for (int i=0; i<list.size(); i++){
                        LogUtils.d("path---->"+list.get(i).getOriginalPath());
                    }
                });
                break;
            case R.id.tv_img_8: //查看大图
                if (paths != null){
                    TakePhotoUtil.getInstance().selectLargerImage(mContext,paths,0);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }
}
