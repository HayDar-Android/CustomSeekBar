#CustomSeekBar  

CustomSeekBar 是一个自定义的seekbar,可以把line分成几段，支持滑动选择
![](https://raw.githubusercontent.com/HayDar-Android/CustomSeekBar/master/a.gif "CustomSeekBar")
![](https://raw.githubusercontent.com/HayDar-Android/CustomSeekBar/master/b.gif "CustomSeekBar")

##Download

###Gradle

``` gradle
dependencies {
	compile 'io.haydar.csb:custom-seekbar:1.0'
}
```

#How do I use CustomSeekBar?

* **Layout**

``` xml
<io.haydar.csb.CustomSeekBar
        android:id="@+id/csb"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        app:default_selection="2"
        app:min_value="1"
        app:section="4"
        app:select="true">
</io.haydar.csb.CustomSeekBar>
```
* **Style**


``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="CustomSeekBar">

        <!-- 默认线条颜色 -->
        <attr name="normal_background" format="color"></attr>

        <!-- 选择颜色 -->
        <attr name="select_background" format="color"></attr>

        <!-- 总共分为几段-->
        <attr name="section" format="integer"></attr>

        <!-- 默认选中第几段 -->
        <attr name="default_selection" format="integer"></attr>

        <!-- 圆圈 -->
        <attr name="circle_src" format="reference"></attr>


        <!-- 最小值-->
        <attr name="min_value" format="integer"></attr>

        <!-- 是否靠近值 -->
        <attr name="select" format="boolean"></attr>
    </declare-styleable>
</resources>
```

* **java**

``` java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();
        csb = (CustomSeekBar) findViewById(R.id.csb);
        csb.setOnValueChanged(new CustomSeekBar.OnValueChanged() {
            @Override
            public void onValueChanged(int value) {
                Toast.makeText(MainActivity.this, value + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
```

##Getting Help

##License

```
BottomBar library for Android
Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


