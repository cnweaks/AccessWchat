package com.auto.mm;

/**

 currentThread() 
 返回对当前正在执行的线程对象的引用(实现接口方式时使用)。
 sleep(long millis) 
 在指定的毫秒数内让当前正在执行的线程休眠(暂停执行)。
 本线程不会去抢，除非sleep结束。
 多个线程之间都会去抢执行权限，不会考虑优先级。
 _yield() 
 暂停当前正在执行的线程对象，并执行其他线程。
 只给本类或者优先级大于本类优先级的线程去抢。
 join() 
 等待该线程终止。    
 放在start()前面则没有用处。
 setDaemon(boolean on) 
 将该线程标记为守护线程，守护线程需要依赖其他线程，会在虚拟机停止的时候停止。










 * QQ
 群助手ID
 android:id/text1

 群列表
 com.tencent.qqlite:id/relativeItem


 群列表右上角按钮
 com.tencent.qqlite:id/ivTitleBtnRightImage


 最近聊友列表
 com.tencent.qqlite:id/relativeItem




 群入口详情
 com.tencent.qqlite:id/ivTitleBtnRightImage


 返回框
 com.tencent.qqlite:id/ivTitleBtnLeft



 消息输入框
 com.tencent.qqlite:id/input


 发送按钮(绿)
 com.tencent.qqlite:id/fun_btn



 消息table
 com.tencent.qqlite:id/0





 class类名



 包:com.tencent.qqlite


 消息窗口类:
 com.tencent.mobileqq.activity.SplashActivity




 群队列


 com.tencent.mobileqq.activity.TroopAssistanActivity






 群聊天窗口

 com.tencent.mobileqq.activity.ChatActivity




 群资料页
 com.tenc.mobileqq.activity.ChatSettingForTroop





 群成员列表


 com.tencent.mobileqq.activity.TroopMemberListActivity


 群成员单员昵称ID
 com.tencent.qqlite:id/tv_name


 加为好友
 ID com.tencent.qqlite:id/txt


 验证消息编辑框
 ID
 com.tencent.qqlite:id/0
 CLASS
 android.widget.EditText



 下一步

 com.tencent.qqlite:id/ivTitleBtnRightText


 发送
 com.tencent.qqlite:id/ivTitleBtnRightText




 android View 详解

 android.View.View(即View)类是以矩形的方式显示在屏幕上，View是用户界面控件的基础。View的继承层次关系如下图：


 可以看到所有的界面控件都是View的子类。简单证实一下，每当你用findViewByIds(R.id.xx)时总要将其强转，因为该方法返回的是一个View实例，有木有!!!其中不得不提View的subClass ViewGroup。Android系统中的所有UI类都是建立在View和ViewGroup这两个类的基础上的。所有View的子类成为”Widget”，所有ViewGroup的子类成为”Layout”。View和ViewGroup之间采用了组合设计模式，可以使得“部分-整体”同等对待。ViewGroup作为布局容器类的最上层，布局容器里面又可以有View和ViewGroup。通过这种方式，我们获得了UI界面的组合方式。

 ViewGroup的子类用不同的方式来管理容器中View控件的摆放位置以及显示方式；但是，对于view控件具体摆放到什么位置，以及大小等属性则需要每个布局类的内部类LayoutParams来进行处理，该类是ViewGroup的内部类，有该类的子类具体实现LayoutParams类。

 可以看到android中所有的UI控件都是View的子类，所以你可以通过继承View类来实现自定义控件，例如之前博客中提到的圆形进度条就是一个例子。注意。此时你需要重载View的构造函数。View的构造函数有三个，但常用的是第一个和第二个，比较简单，有需要的可以查下文档。

 一.而动态创建View和ViewGroup一般有以下2种方式可以实现。

 1.每创建一个UI控件就添加到布局中，但是这种方式有个缺点，当你要动态添加的按钮过多时，就会显得过于麻烦。

 ViewParent viewParent = this.findViewById(R.id.text1).getParent();

 RelativeLayout relativeLayout = (RelativeLayout) viewParent;

 Button button = new Button(this);

 button.setText("Fuck");

 relativeLayout.addView(button,300,200);button,100,100);

 2.从一个XML文件中创建控件，再添加到布局中。

 LayoutInflater layoutInflater = this.getLayoutInflater();

 layoutInflater.inflate(R.layout.mybviewlayout, (ViewGroup)this.findViewById(R.layout.activity_main));

 在第二句代码中关联了一个布局文件mybviewlayout.xml,在这里使用inflate方法将这个布局文件装载到内存中后被转化为View对象，，然后将这个View对象添加到activity_main.xml布局文件中。

 二.常用布局

 1.LinearLayout

 相信大家对LinearLayout一定不会陌生吧，这里有一点需要注意：Linearlayout 不允许精准的控制它子view的关系，子view只能简单的一个接一个排成行。更好的方法是使用relativelayout.

 2.RelativeLayout

 该布局下我们可以非常灵活的来控制UI控件的摆放位置，在现实生活中我们最常用的应该是就是RelativeLayout与LinearLayout的结合使用了吧。这里就需要注意一下view的各种layout_XXX,margin_XX,等属性的使用了，他会帮你定制非常灵活的布局文件。我会在文章末尾附上。

 3.TableLayout

 TableLayout是用表格来进行UI界面的布局，个人感觉不太常用，使用的标签是，而且TableLayout的属性是垂直排列，若想水平排列则应该使用TableRow。其他就不过多介绍了。

 4.FrameLayout和AbsoluteLayout

 FrameLayout和AbsoluteLayout分别是框架布局和绝对布局。frameLayout中UI控件默认显示在屏幕左上角，我记得Web浏览器中也是这样的，可用于图片扩散展示之类的。而AbsoluteLayout的用途相比看看名称就知道了，就我个人而言，目前并未使用到。

 最后，就附上一些常用属性的说明吧，希望对大家有用。

 android:alpha 关联方法: setAlpha(float) 属性说明: 视图透明度，值在0-1之间。0为完全透明，1为完全不透明。

 android:background 关联方法: setBackgroundResource(int) 属性说明: 视图背景

 android:clickable 关联方法: setClickable(boolean) 属性说明: 视图是否可点击

 android:contentDescription 关联方法: setContentDescription(CharSequence) 属性说明: 设置View的备注说明，作为一种辅助功能提供,为一些没有文字描述的View提供说明

 android:drawingCacheQuality 关联方法: setDrawingCacheQuality(int) 属性说明: "设置绘图时半透明质量。有可以取以下3个值 auto——默认，由框架决定 high——高质量，使用较高的颜色深度，消耗更多的内存 low——低质量，使用较低的颜色深度，但是用更少的内存"

 android:duplicateParentState 关联方法: 属性说明: 如果设置此属性，将直接从父容器中获取绘图状态（光标，按下等）

 android:fadeScrollbars 关联方法: setScrollbarFadingEnabled(boolean) 属性说明: 定义在ScrollBar没有使用时，是否褪色。

 android:fadingEdgeLength 关联方法: getVerticalFadingEdgeLength() 属性说明: 设置边框渐变的长度。

 android:filterTouchesWhenObscured 关联方法: setFilterTouchesWhenObscured(boolean) 属性说明: view所在窗口被其它可见窗口遮住时，是否过滤触摸事件。

 android:fitsSystemWindows 关联方法: setFitsSystemWindows(boolean) 属性说明: 设置布局调整时是否考虑系统窗口（如状态栏）

 android:focusable 关联方法: setFocusable(boolean) 属性说明: 设置是否获得焦点。若有requestFocus()被调用时，后者优先处理。注意在表单中想设置某一个如EditText获取焦点，光设置这个是不行的，需要将这个EditText前面的focusable都设置为false才行。在Touch模式下获取焦点需要设置focusableInTouchMode为true。

 android:focusableInTouchMode 关联方法: setFocusableInTouchMode(boolean) 属性说明: 设置在Touch模式下View是否能取得焦点。

 android:hapticFeedbackEnabled 关联方法: setHapticFeedbackEnabled(boolean) 属性说明: 是否启用触摸反馈，启用后就是在点击等操作时会有震动等反馈效果

 android:id 关联方法: setId(int) 属性说明: 给当前View设置一个在当前layout.xml中的唯一编号，可以通过调用View.findViewById() 或Activity.findViewById()根据这个编号查找到对应的View。不同的layout.xml之间定义相同的id不会冲突。

 android:importantForAccessibility 关联方法: setImportantForAccessibility(int) 属性说明: 设置可达性的重要性

 android:isScrollContainer 关联方法: setScrollContainer(boolean) 属性说明: 设置当前View为滚动容器。这里没有测试出效果来，ListView/ GridView/ ScrollView根本就不用设置这个属性，而EdidText设置android:scrollbars也能出滚动条

 android:keepScreenOn 关联方法: setKeepScreenOn(boolean) 属性说明: 视图在可见的情况下是否保持唤醒状态。

 android:layerType 关联方法: setLayerType(int,Paint) 属性说明: "设置指定层的类型，可以取以下3个值： none——不指定 software——软件层。 hardware——硬件层。使用硬件加速。"

 android:layoutDirection 关联方法: setLayoutDirection(int) 属性说明: 定义布局图纸的方向

 android:longClickable 关联方法: setLongClickable(boolean) 属性说明: 是否响应长点击事件

 android:minHeight 关联方法: setMinimumHeight(int) 属性说明: 设置视图最小高度

 android:minWidth 关联方法: setMinimumWidth(int) 属性说明: 设置视图最小宽度

 android:nextFocusDown 关联方法: setNextFocusDownId(int) 属性说明: 向下移动焦点时，下一个获取焦点的view的id

 android:nextFocusForward 关联方法: setNextFocusForwardId(int) 属性说明: 下一个获取焦点的view的id

 android:nextFocusLeft 关联方法: setNextFocusLeftId(int) 属性说明: 向左移动焦点时，下一个获取焦点的view的id

 android:nextFocusRight 关联方法: setNextFocusRightId(int) 属性说明: 向右移动焦点时，下一个获取焦点的view的id

 android:nextFocusUp 关联方法: setNextFocusUpId(int) 属性说明: 向上移动焦点时，下一个获取焦点的view的id

 android:onClick 关联方法: 属性说明: 点击时，要调用的方法的名称。

 android:padding 关联方法: setPaddingRelative(int,int,int,int) 属性说明: 设置上下左右的边距

 android:paddingBottom 关联方法: setPaddingRelative(int,int,int,int) 属性说明: 下边距

 android:paddingEnd 关联方法: setPaddingRelative(int,int,int,int) 属性说明: 与android:paddingRight相同

 android:paddingLeft 关联方法: setPadding(int,int,int,int) 属性说明: 左边距

 android:paddingRight 关联方法: setPadding(int,int,int,int) 属性说明: 右边距

 android:paddingStart 关联方法: setPaddingRelative(int,int,int,int) 属性说明: android:paddingLeft相同

 android:paddingTop 关联方法: setPaddingRelative(int,int,int,int) 属性说明: 上边距

 android:requiresFadingEdge 关联方法: setVerticalFadingEdgeEnabled(boolean) 属性说明: 定义滚动时边缘是否褪色

 android:rotation 关联方法: setRotation(float) 属性说明: 旋转度数

 android:rotationX 关联方法: setRotationX(float) 属性说明: 水平旋转度数

 android:rotationY 关联方法: setRotationY(float) 属性说明: 竖直旋转度数

 android:saveEnabled 关联方法: setSaveEnabled(boolean) 属性说明: 在配置改变等情况出现时是否保存view的状态数据。如果你的view有id，那默认系统就会帮你保存。

 android:scaleX 关联方法: setScaleX(float) 属性说明: 水平方向缩放比例

 android:scaleY 关联方法: setScaleY(float) 属性说明: 竖直方向缩放比例

 android:scrollX 关联方法: 属性说明: x方向的滚动偏移。即在水平方向滚动了多少距离

 android:scrollY 关联方法: 属性说明: y方向的滚动偏移。即在竖直方向滚动了多少距离

 android:scrollbarAlwaysDrawHorizontalTrack 关联方法: 属性说明: 是否总是绘制水平滚动条的滚动轨道

 android:scrollbarAlwaysDrawVerticalTrack 关联方法: 属性说明: 是否总是绘制竖直滚动条的滚动轨道

 android:scrollbarDefaultDelayBeforeFade 关联方法: setScrollBarDefaultDelayBeforeFade(int) 属性说明: 滚动条在n毫秒后开始淡出。

 android:scrollbarFadeDuration 关联方法: setScrollBarFadeDuration(int) 属性说明: 滚动条用多长时间淡出完毕。

 android:scrollbarSize 关联方法: setScrollBarSize(int) 属性说明: 设置滚动条的尺寸。垂直滚动条的宽度、水平滚动条的高度

 android:scrollbarStyle 关联方法: setScrollBarStyle(int) 属性说明: "滚动条的风格。共4组值： insideOverlay——内贴图 insideInset——内插图 outsideOverlay——外贴图 outsideInset——外插图。 inside就是滚动条在绘制在padding以内；outside就是不需要绘制在padding内（即view的边界处）；Overlay是贴图，就是直接覆盖在内容的上方，这样内容可能会显示到滚动条下方去；Inset是插图，就是会在对应padding上加上滚动条的宽度，以不让内容显示到滚动条下面去。"

 android:scrollbarThumbHorizontal 关联方法: 属性说明: 水平滚动块的drawable对象

 android:scrollbarThumbVertical 关联方法: 属性说明: 竖直滚动块的drawable对象

 android:scrollbarTrackHorizontal 关联方法: 属性说明: 水平滚动条滚动轨道的drawable对象

 android:scrollbarTrackVertical 关联方法: 属性说明: 竖直滚动条滚动轨道的drawable对象

 android:scrollbars 关联方法: 属性说明: "设置可显示的滚动条。有3个取值: none——不显示滚动条 horizontal——显示水平滚动条 vertical——显示竖直滚动条"

 android:soundEffectsEnabled 关联方法: setSoundEffectsEnabled(boolean) 属性说明: 点击或触摸该view时，是否需要有声音效果

 android:tag 关联方法: 属性说明: string标识。类似id，id是整数标识。

 android:textAlignment 关联方法: setTextAlignment(int) 属性说明: 设置文本的显示方式。

 android:textDirection 关联方法: setTextDirection(int) 属性说明: 设置文本的显示方向。

 android:transformPivotX 关联方法: setPivotX(float) 属性说明: 水平方向偏转量

 android:transformPivotY 关联方法: setPivotY(float) 属性说明: 竖直方向偏转量

 android:translationX 关联方法: setTranslationX(float) 属性说明: 水平方向的移动距离

 android:translationY 关联方法: setTranslationY(float) 属性说明: 竖直方向的移动距离

 android:visibility 关联方法: setVisibility(int) 属性说明: "view的可见性。有3个取值： gone——不可见，同时不占用view的空间； invisible——不可见，但占用view的空间； visible——可见"

 TextView属性说明

 下面对TextView的属性进行说明 android:autoLink 关联方法: setAutoLinkMask(int) 属性说明: 设置是否“当文本为URL链接/email/电话号码/map时，文本显示为可点击的链接”。可选值(none/web/email/phone/map/all)

 android:autoText 关联方法: setKeyListener(KeyListener) 属性说明: 如果设置，将自动执行输入值的拼写纠正。此处无效果，在显示输入法并输入的时候起作用。

 android:bufferType 关联方法: setText(CharSequence,TextView.BufferType) 属性说明: 指定getText()方式取得的文本类别。选项editable 类似于StringBuilder可追加字符，也就是说getText后可调用append方法设置文本内容。

 android:capitalize 关联方法: setKeyListener(KeyListener) 属性说明: 设置自动大写属性。比如设置为2，自动大写单词首字符；设置为1，自动大写每句话的首字母等等。

 android:cursorVisible 关联方法: setCursorVisible(boolean) 属性说明: 设定光标为显示/隐藏，默认显示。

 android:digits 关联方法: setKeyListener(KeyListener) 属性说明: 设置允许输入哪些字符。如“1234567890.+\/%\n()”

android:drawableBottom 关联方法: setCompoundDrawablesWithIntrinsicBounds(int,int,int,int) 属性说明: 在text的下方输出一个drawable。如果指定一个颜色的话会把text的背景设为该颜色，并且同时和background使用时覆盖后者。

android:drawableEnd 关联方法: setCompoundDrawablesRelativeWithIntrinsicBounds(int,int,int,int) 属性说明: 在文本结尾处显示drawable对象。它的值可以是其它资源的引用，比如，"@[+][package:]type:name"或者"?[package:][type:]name"；也可以是颜色值，如"#rgb", "#argb", "#rrggbb", or "#aarrggbb"。

android:drawableLeft 关联方法: setCompoundDrawablesWithIntrinsicBounds(int,int,int,int) 属性说明: 在text的左边输出一个drawable。

android:drawablePadding 关联方法: setCompoundDrawablePadding(int) 属性说明: 设置text与drawable的间隔，与drawableLeft、drawableRight、drawableTop、drawableBottom一起使用，可设置为负数，单独使用没有效果。

android:drawableRight 关联方法: setCompoundDrawablesWithIntrinsicBounds(int,int,int,int) 属性说明: 在text的右边输出一个drawable。

android:drawableStart 关联方法: setCompoundDrawablesRelativeWithIntrinsicBounds(int,int,int,int) 属性说明: 在文本开始处显示drawable对象。它的值可以是其它资源的引用，比如，"@[+][package:]type:name"或者"?[package:][type:]name"；也可以是颜色值，如"#rgb", "#argb", "#rrggbb", or "#aarrggbb"。

android:drawableTop 关联方法: setCompoundDrawablesWithIntrinsicBounds(int,int,int,int) 属性说明: 在text的正上方输出一个drawable。

android:editable 关联方法: 属性说明: 设置是否可编辑。这里无效果，在EditView中才有效果。

android:editorExtras 关联方法: setInputExtras(int) 属性说明: 设置文本的额外的输入数据。在EditView中才有效果。

android:ellipsize 关联方法: setEllipsize(TextUtils.TruncateAt) 属性说明: 设置当文字过长时,该控件该如何显示。有如下值设置：”start”—–省略号显示在开头；”end”——省略号显示在结尾；”middle”—-省略号显示在中间；”marquee” ——以跑马灯的方式显示(动画横向移动)

android:ems 关联方法: setEms(int) 属性说明: 设置TextView的宽度为N个字符的宽度。

android:fontFamily 关联方法: setTypeface(Typeface) 属性说明: 文本的字形体系。

android:freezesText 关联方法: setFreezesText(boolean) 属性说明: 设置保存文本的内容以及光标的位置。

android:gravity 关联方法: setGravity(int) 属性说明: 设置文本位置，如设置成“center”，文本将居中显示。

android:height 关联方法: setHeight(int) 属性说明: 设置文本区域的高度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)

android:hint 关联方法: setHint(int) 属性说明: Text为空时显示的文字提示信息，可通过textColorHint设置提示信息的颜色。

android:imeActionId 关联方法: setImeActionLabel(CharSequence,int) 属性说明: 设置IME动作ID。

android:imeActionLabel 关联方法: setImeActionLabel(CharSequence,int) 属性说明: 设置IME动作标签。在EditView再做说明。

android:imeOptions 关联方法: setImeOptions(int) 属性说明: 附加功能，设置右下角IME动作与编辑框相关的动作，如actionDone右下角将显示一个“完成”，而不设置默认是一个回车符号。

android:includeFontPadding 关联方法: setIncludeFontPadding(boolean) 属性说明: 设置文本是否包含顶部和底部额外空白，默认为true。

android:inputMethod 关联方法: setKeyListener(KeyListener) 属性说明: 为文本指定输入法，需要完全限定名（完整的包名）。例如：com.google.android.inputmethod.pinyin，但是这里报错找不到。

android:inputType 关联方法: setRawInputType(int) 属性说明: 设置文本的类型，用于帮助输入法显示合适的键盘类型。在EditView中再详细说明，这里无效果。

android:lineSpacingExtra 关联方法: setLineSpacing(float,float) 属性说明: 设置行间距。

android:lineSpacingMultiplier 关联方法: setLineSpacing(float,float) 属性说明: 设置行间距的倍数。如”1.2”

android:lines 关联方法: setLines(int) 属性说明: 设置文本的行数，设置两行就显示两行，即使第二行没有数据。

android:linksClickable 关联方法: setLinksClickable(boolean) 属性说明: 设置链接是否点击连接，即使设置了autoLink。

android:marqueeRepeatLimit 关联方法: setMarqueeRepeatLimit(int) 属性说明: 在ellipsize指定marquee的情况下，设置重复滚动的次数，当设置为marquee_forever时表示无限次。

android:maxEms 关联方法: setMaxEms(int) 属性说明: 设置TextView的宽度为最长为N个字符的宽度。与ems同时使用时覆盖ems选项。

android:maxHeight 关联方法: setMaxHeight(int) 属性说明: 设置文本区域的最大高度

android:maxLength 关联方法: setFilters(InputFilter) 属性说明: 限制显示的文本长度，超出部分不显示。

android:maxLines 关联方法: setMaxLines(int) 属性说明: 设置文本的最大显示行数，与width或者layout_width结合使用，超出部分自动换行，超出行数将不显示。

android:maxWidth 关联方法: setMaxWidth(int) 属性说明: 设置文本区域的最大宽度

android:minEms 关联方法: setMinEms(int) 属性说明: 设置TextView的宽度为最短为N个字符的宽度。与ems同时使用时覆盖ems选项。

android:minHeight 关联方法: setMinHeight(int) 属性说明: 设置文本区域的最小高度

android:minLines 关联方法: setMinLines(int) 属性说明: 设置文本的最小行数，与lines类似。

android:minWidth 关联方法: setMinWidth(int) 属性说明: 设置文本区域的最小宽度

android:numeric 关联方法: setKeyListener(KeyListener) 属性说明: 如果被设置，该TextView有一个数字输入法。此处无用，设置后唯一效果是TextView有点击效果，此属性在EdtiView将详细说明。

android:password 关联方法: setTransformationMethod(TransformationMethod) 属性说明: 以小点”.”显示文本

android:phoneNumber 关联方法: setKeyListener(KeyListener) 属性说明: 设置为电话号码的输入方式。

android:privateImeOptions 关联方法: setPrivateImeOptions(String) 属性说明: 设置输入法选项，在EditText中才有作用。

android:scrollHorizontally 关联方法: setHorizontallyScrolling(boolean) 属性说明: 设置文本超出TextView的宽度的情况下，是否出现横拉条。

android:selectAllOnFocus 关联方法: setSelectAllOnFocus(boolean) 属性说明: 如果文本是可选择的，让他获取焦点而不是将光标移动为文本的开始位置或者末尾位置。TextView中设置后无效果。

android:shadowColor 关联方法: setShadowLayer(float,float,float,int) 属性说明: 指定文本阴影的颜色，需要与shadowRadius一起使用。

android:shadowDx 关联方法: setShadowLayer(float,float,float,int) 属性说明: 设置阴影横向坐标开始位置。

android:shadowDy 关联方法: setShadowLayer(float,float,float,int) 属性说明: 设置阴影纵向坐标开始位置。

android:shadowRadius 关联方法: setShadowLayer(float,float,float,int) 属性说明: 设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好。

android:singleLine 关联方法: setTransformationMethod(TransformationMethod) 属性说明: 设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示。如android:text="test_ singleLine " android:singleLine="true" android:layout_width="20dp"将只显示“t…”。如果不设置singleLine或者设置为false，文本将自动换行

android:text 关联方法: setText(CharSequence,TextView.BufferType) 属性说明: 设置显示文本.

android:textAllCaps 关联方法: setAllCaps(boolean) 属性说明: 设置文本全为大写。值为"true"或"false"。

android:textAppearance 关联方法: 属性说明: 设置文字外观。如“?android:attr/textAppearanceLargeInverse

android:textColor 关联方法: setTextColor(int) 属性说明: 设置文本颜色

android:textColorHighlight 关联方法: setHighlightColor(int) 属性说明: 被选中文字的底色，默认为蓝色

android:textColorHint 关联方法: setHintTextColor(int) 属性说明: 设置提示信息文字的颜色，默认为灰色。与hint一起使用。

android:textColorLink 关联方法: setLinkTextColor(int) 属性说明: 文字链接的颜色.

android:textIsSelectable 关联方法: isTextSelectable() 属性说明: 设置非编辑文本可否被选择。值为"true"或"false"。

android:textScaleX 关联方法: setTextScaleX(float) 属性说明: 设置文字之间间隔，默认为1.0f。

android:textSize 关联方法: setTextSize(int,float) 属性说明: 设置文字大小，推荐度量单位”sp”，如”15sp”

android:textStyle 关联方法: setTypeface(Typeface) 属性说明: 设置字形[bold(粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2] 可以设置一个或多个，用“|”隔开

android:typeface 关联方法: setTypeface(Typeface) 属性说明: 设置文本字体，必须是以下常量值之一：normal 0, sans 1, serif 2, monospace(等宽字体) 3]

android:width 关联方法: setWidth(int) 属性说明: 设置文本区域的宽度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)。

android:fadingEdgeLength

设置淡入淡出边缘的长度，可以接受大小值的单位是：px、dp、sp、in、mm，也可以参考大小值资源

android:fitsSystemWindows

是否适合系统窗体，取值为true或false。该属性只对不是子组件的组件有效

android:focusable

是否可以获取焦点，取值true或false

android:focusableInTouchMode

是否可以在触摸模式下获取焦点，true或false

android:hapticFeedbackEnabled

是否允许触摸反馈效果，true或false

android:id

提供该组件的标识名，可以借助Activity或View实例的findViewById方法通过id获取对应的组件实例对象，其属性值的形式为：android:id=”@+id/id”

android:isScrollContainer

设置该组件是否设置为滚动条容器，true或false

android:keepScreenOn

控制该组件在显示的时候保持在屏幕显示，true或false

android:longClickable

是否响应长时间点击事件，true或false

android:minHeight

组件的最小高度，取值同android:fadingEdgeLength

android:minWidth

组件的最小宽度，取值同android:fadingEdgeLength

android:nextFocusDown

设置下一个向下获取焦点的组件，取值为id

android:nextFocusLeft

设置下一个向左获取焦点的组件，取值为id

android:nextFocusRight

设置下一个向右获取焦点的组件，取值为id

android:nextFocusUp

设置下一个向上获取焦点的组件，取值为id

android:padding

设置上、下、左、右4个边缘的填充距离，必须是一个大小值，取值同android:fadingEdgeLength

android:paddingBottom

设置下端边缘的填充距离，取值同android:padding

android:paddingLeft

设置左端边缘的填充距离，取值同android:padding

android:paddingRight

设置右端边缘的填充距离，取值同android:padding

android:paddingTop

设置上端边缘的填充距离，取值同android:padding

android:saveEnabled

是否允许保存状态，取值为true或false

android:scrollX

设置垂直滚动条的位移量，必须是一个大小值，取值同android:padding

android:scrollY

设置水平滚动条的位移量，必须是一个大小值，取值同android:padding

android:scrollbarAlwaysDrawHorizontalTrack

是否总是设置水平滚动条滑块，true或false

android:scrollbarAlwaysDrawVerticalTrack

是否总是设置垂直滚动条滑块，true或false

android:scrollbarSize

设置垂直滚动条的宽度和水平滚动条的长度，必须是一个大小值，取值同android:padding

android:scrollbarStyle

设置滚动条的样式，取值为下列之一：

insideOverlay在填充区域内，覆盖形式

insideInset在填充区域内，插进形式（凹进）

outsideOverly在绑定组件边缘，覆盖形式

outsideInset在绑定组件边缘，插进形似

android:scrollbarThumbHorizontal

设置水平滚动条按钮的绘制资源，必须引用可绘制资源

android:scrollbarThumbVertical

设置垂直滚动条按钮的绘制资源，必须引用可绘制资源

android:scrollbarTrackHorizontal

设置水平滚动条轨道的绘制资源，必须引用可绘制资源

android:scrollbarTrackVertical

设置水平滚动条轨道的绘制资源，必须引用可绘制资源

android:scrollbars

设置滚动显示，可以为一下一个或多个值：

none不显示滚动条

horizontal只显示水平滚动条

vertical只显示垂直滚动条

android:soundEffectsEnabled

是否允许音效，取值为true或false

android:tag

设置标记内容，可以通过View类实例的getTag方法获取该组件的标记内容，或者使用findViewByTag通过标记来查找相应的子组件

android:visibility

设置初始化可见状态，取值为以下之一：

visible可见（默认值）

invisible不可见（其所占空间将留出）

gone完全不可见（其所占空间都不会留出）

线性布局LinearLayout组件属性列表

属性

说明

android:baselineAligned

基线对齐

android:baselineAlignedChildIndex

以指定子组件作为基线对齐

android:gravity

指定该物体放入其容器的重心位置，取值为下列之一：

top上方，物体大小不变

bottom下方，物体大小不变

left左方，物体大小不变

right右方，物体大小不变

center_vertical垂直方向的中间，物体大小不变

fill_vertical填满垂直方向，自动进行大小调整

center_horizontal水平方向的中间，大小不变

fill_horizontal填满水平方向，自动进行大小调整

center居中（既是水平也是垂直方向的中间）

fill填满整个容器

clip_vertical

clip_horizontal

android:orientation

布局方向，取值为下列之一：

horizontal水平的

vertical垂直的（默认值）

android:weightSum

组件的比重和

线性布局参数LinearLayout_Layout

属性

说明

android:layout_gravity

当前子组件的心位置

android:layout_height

当前子组件的高度

android:layout_weight

当前子组件的空间比重，取值为浮点数

android:layout_width

当前子组件的宽度

相对布局RalativeLayout

属性

说明

android:gravity

设置添加组件的重心

android:ignoreGravity

忽略布局重心的影响

相对布局参数RalativeLayout_Layout

属性

说明

android:layout_above

将当前组件的下边缘放置于参照组件之上，该属性为参照组件的ID

android:layout_alignBaseline

当前组件与参照组件的基线对齐，该属性为参照组件的ID

android:layout_alignBottom

当前组件与参照组件的下边界对齐，该属性为参照组件的ID

android:layout_alignLeft

当前组件与参照组件的左边界对齐，该属性为参照组件的ID

android:layout_alignParenBottom

当前组件与父组件的下边界对齐，true或false

android:layout_alignParentLeft

当前组件与父组件的左边界对齐，true或false

android:layout_alignParentRight

当前组件与父组件的右边界对齐，true或false

android:layout_alignParentTop

当前组件与父组件的上边界对齐，true或false

android:layout_alignRight

当前组件与参照组件的右边界对齐，该属性为参照组件的ID

android:layout_alignTop

当前组件与参照组件的上边界对齐，该属性为参照组件的ID

android:layout_alignWithParentIfMissing

true或false

android:layout_below

将当前组件的上边缘放置于参照组件之下，该属性为参照组件的ID

android:layout_centerHorizontal

当前组件放置到父组件的水平居中的位置

android:layout_centerInParent

当前组件放置到父组件的重心位置

android:layout_centerVertical

当前组件放置到父组件垂直居中的位置

android:layout_toLeftOf

将当前组件的右边缘放置于参照组件之下，该属性为参照组件的ID

android:layout_toRightOf

将当前组件的左边缘放置于参照组件之下，该属性为参照组件的ID

绝对布局参数AbsoluteLayout_Layout

属性

说明

android:layout_x

当前组件的x坐标位置（从左到右方向）

android:layout_y

当前组件的y坐标位置（从上到下方向）

框布局FrameLayout

属性

说明

android:foreground

前置图片

android:foregroundGravity

前置图片重心

android:measureAllChildren

在切换显示时是否侧重所有子组件的大小

android:layout_gravity

添加组件的重心

框布局参数FrameLayout_Layout

属性

说明

android:layout_gravity

当前子组件所添加的重心位置

表格布局TableLayout

属性

说明

android:collapseColumns

设置允许折叠的列编号，列编号基于0，属性值可以是单个或多个列编号，编号与编号直接用逗号”,”分隔

android:shrinkColumns

设置允许收缩的列编号，列编号基于0，属性值可以是单个或多个列编号，编号与编号直接用逗号”,”分隔

android:stretchColumns

设置允许伸展的列编号，列编号基于0，属性值可以是单个或多个列编号，编号与编号直接用逗号”,”分隔

表格行的单元TableRow_Cell

属性

说明

android:layout_column

设置该单元格的列编号（基于0）

android:layout_span

指明该单元格可以跨越的列数

抽象列表视图组件AbsListView

属性

说明

android:cacheColorHint

设置缓冲颜色

android:drawSelectorOnTop

是否将选择器绘制在备选条目上方，取值为true或false

android:fastScrollEnabled

允许快速滚动

android:listSelector

指示选择器的内容

android:scrollingCache

滚动时是否使用绘制缓冲，true或false

android:smoothScrollbar

平滑滚动条

android:stackFromBottom

从下方堆叠条目

android:textFilterEnbled

是否允许过滤

android:transcriptMode

设置抄本模式

列表视图组件ListView

属性

说明

android:choiceMode

选择模式

android:divider

分割线颜色或组件的参考

android:dividerHeight

分割线高度

android:entries

指定绑定到当前列表视图的一个数组资源

android:footerDividersEnabled

是否允许页脚分割线

android:headerDividersEnabled

是否允许页眉分割线

格子视图组件GridView

属性

说明

android:columnWidth

指定列宽

android:gravity

添加组件的重心位置

android:horizontalSpacing

水平空间

android:numColumns

指定列数

android:strechMode

伸展模式

android:verticalSpacing

垂直空间

画廊视图组件Gallery

属性

说明

android:animationDuration

动画持续时间

android:gravity

添加组件的重心位置

android:spacing

间隔空间

android:unselectedAlpha

非选择条目的透明度

文本组件TextView

属性

说明

android:autoLink

是否自动链接（内容是网址或是电子邮件时）

android:autoText

自动更新拼音错误

android:bufferType

设置缓冲区类型

android:capitalize

自动大写

android:cursorVisible

光标是否可见，true或false

android:digits

所接受的数字字符

android:drawableBottom

在文本下方绘制

android:drawableLeft

在文本左方绘制

android: drawablePadding

绘制填充区

android: drawableRight

在文本右方绘制

android: drawableTop

在文本上方绘制

android:editable

是否可编辑，true或false

android:editorExtras

android:ellipsize

当内容过长时会自动打断单词内容

android:ems

android:enabled

是否可用，true或false

android:freezesText

是否冻结文本

android:gravity

指明文本的重心位置

android:height

高度值

android:hint

指示内容

android:imeActionId

android:imeActionLabel

android:imeOptions

输入法选项

android:includeFontPadding

android:inputMethod

指定输入法

android:inputType

输入类型，取值为下列之一：

none

text普通文本

textCapCharacters大写字符

textCapWords单词首字母大写

textCapSentences句子首字母大写

textAutoCorret自动更正

textAutoComplete自动完成

textMultiLine多行内容

textUri，Uri

textEmailAddress电子邮件地址

textEmailSubject电子邮件主题

textShortMessage短消息

textLongMessage长消息

textPersonName个人姓名

textPostalAddress邮政地址

textPassword密码

textVIsiblePassword可见的密码

textWebEditText网页格式

textFilter过滤字符串

textPhonetic语言发音

number数字

numberSigned有符号数字

numberDecimal十进制数字

phone电话号码

datetime日期时间

date日期

time时间

android:lineSpacingExtra

android:lineSpacingMultiplier

android:lines

设置文本行数

android:linksClickable

android:marqueeRepeatLimit

来回移动的动画次数

android:maxEms

android:maxHeight

物体的最大高度

android:maxLength

最大文本长度

android:maxLines

最大行数

android:minWidth

物体的最大宽度

android:minEms

android:minHeight

物体的最小高度

android:minLines

最小文本行数

android:minWidth

物体的最小宽度

android:numeric

是否使用数字输入方式

android:password

是否使用密码输入方式

android:phonenumber

是否使用电话号码输入方式

android:privateImeOptions

android:scrollHorizontally

android:selectAllOnFocus

android:shadowColor

文本阴影颜色

android:shadowDx

阴影的水平偏移

android:shadowDy

阴影的垂直偏移

android:shadowRadius

阴影的半径

android:singleLine

是否单行（不自动换行）

android:text

显示的文本内容

android:textApperance

基本字体颜色、字样、大小和样式

android:textColor

文本颜色

android: textColorHighlight

文本高亮颜色

android: textColorHint

文本提示颜色

android:textColorLink

文本链接颜色

android:textScaleX

文本缩放因数

android:textSize

文本大小

android:textStyle

文本样式，取值为下列之一：

bold粗体

italic斜体

bolditalic粗斜体

android:typeface

字样

android:width

物体的高度

自动完成文本框AutoCompleteTextView

属性

说明

android:completionHint

显示提示

android:completionHintView

提示视图

android:completionThreshold

设置开始提示的字符数

android:dropDownAnchor

下拉框链接视图

android:dropDownSelector

下拉框选择器

android:dropDownWIdth

下拉框宽度

图片视图ImageView

属性

说明

android:adjustViewBounds

是否调整视图范围

android:baselineAlignBottom

是否按照下端基线对齐

android:cropToPadding

是否按照填充进行裁剪

android:maxHeight

设置最大高度

android:maxWidth

设置最大宽度

android:scaleType

缩放类型，取值为下列之一：

matrix图片真实大小

fitXY适合图片大小

fitStart

fitCenter

fitEnd

center居中显示

centerCrop

centerInside

android:src

设置绘制用内容

android:tint

设置染色颜色值

android:layout_above="@id/xxx" --将控件置于给定ID控件之上 android:layout_below="@id/xxx" --将控件置于给定ID控件之下

android:layout_toLeftOf="@id/xxx" --将控件的右边缘和给定ID控件的左边缘对齐 android:layout_toRightOf="@id/xxx" --将控件的左边缘和给定ID控件的右边缘对齐

android:layout_alignLeft="@id/xxx" --将控件的左边缘和给定ID控件的左边缘对齐 android:layout_alignTop="@id/xxx" --将控件的上边缘和给定ID控件的上边缘对齐 android:layout_alignRight="@id/xxx" --将控件的右边缘和给定ID控件的右边缘对齐 android:layout_alignBottom="@id/xxx" --将控件的底边缘和给定ID控件的底边缘对齐 android:layout_alignParentLeft="true" --将控件的左边缘和父控件的左边缘对齐 android:layout_alignParentTop="true" --将控件的上边缘和父控件的上边缘对齐 android:layout_alignParentRight="true" --将控件的右边缘和父控件的右边缘对齐 android:layout_alignParentBottom="true" --将控件的底边缘和父控件的底边缘对齐 android:layout_centerInParent="true" --将控件置于父控件的中心位置 android:layout_centerHorizontal="true" --将控件置于水平方向的中心位置 android:layout_centerVertical="true" --将控件置于垂直方向的中心位置



 */
public class README {
}
