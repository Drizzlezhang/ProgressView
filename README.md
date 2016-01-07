## ProgressView
一个View来显示进度值
### 设置属性
    progress_text            //进度圆中心文字(默认为空)
    progress_text_size       //进度圆中心文字大小（默认12sp）
    progress_text_color      //进度圆中心文字颜色（默认黑色）
    progress_radius          //圆圈半径(默认30dp)
    progress_stroke_width    //外圆弧描边宽度(默认4dp)
    progress_stroke_color    //外圆弧进度值描边颜色
    progress_back_color      //外圆弧描边背景颜色
    need_back_circle         //设定是否需要背景圆弧,默认需要
    need_back_color          //设定是否需要进度圆颜色,默认需要
    back_color               //进度圆颜色
    start_location           //进度圆弧起始位置
### 也可以通过Java代码设置属性



    progressView.setText("...");  //可以设置的属性同上,文字不需要的话设置为空即可
    //在代码中设置进度值
    progressView.setProgress(...);
    //进度动画
    progressView.playAnimation(int start, int end, int duration);
    //结束动画
    progressView.finish();

### 进度监听器

    progressView.setOnProgressChangeListener(new OnProgressChangeListener() {
     @Override public void onProgressChange(int progress) {
        //TODO
     }
    });

> 效果图稍后放出。
