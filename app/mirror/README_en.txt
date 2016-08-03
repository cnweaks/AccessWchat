举例目录布局
-----------------------------------------------
 从版本2.3.2后，镜像使用不同的目录结构的"mirror"支持不同的数据样本构建变体。 最初的文件 位于"镜子"已被移动到"镜像/主"。特定变体 必要时将创建目录以存储您的变异具体屏幕文件(。 镜子的xml)。 镜像处理这些目录中 与Gradle/下载地址如何处理sourceset目录相同的方式(详情请参阅下文)。 如果您不使用任何味道，生成类型或变异具体sourcesets，你可以找到你所有的布局屏幕文件在"镜像/主"。

 下面是一个新的目录结构的示例：
    mirror/
        .res/                 <-- 只有镜子预览资源
        .gen/                 <-- 生成的镜像文件，不编辑
        debug/                <-- screen files for layouts in "src/debug/res"
            build_type.xml
        free/                 <-- screen files for layouts in "src/free/res"
            variant.xml
        freeDebug/
            variant.xml
        main/                 <-- screen files for layouts in "src/main/res"
            activity_main.xml
            image.png
            sample.xml
        paid/
            image.png
            sample.xml
            variant.xml
        paidDebug/
            flavor.xml
            image.png
        paidRelease/
            image.png
        release/
            build_type.xml
            sample.xml
 如前所述，镜像处理这些目录一样多Gradle/下载地址处理sourceset目录。屏幕布局文件 在"main"和变异体(包括口味和生成类型)会合并前发送到设备。

例如，如果选定的变形是 paidRelease 那么 "paidRelease/image.png",
"release/build_type.xml", "release/sample.xml", "paid/variant.xml",
"main/activity_main.xml" 将推送到装置。

如果存在具有相同名称的文件在不同的目录中，镜像将发送最特定目录中的文件。 镜子假设的顺序相同Gradle/下载地址(按从最特定到最不特定的)： 
               variant > build type > flavor > main

我们使用一个简单的方式来合并镜子目录中的文件：只有将审议有关当前选定风味目录如果存在具有相同名称的文件路径或相对路径不同目录，只有将包括更具体的目录中的文件，其余的将被忽略。

下面是一个更广义的目录结构：

    mirror/
        .res/
        .gen/
        main/
屏幕具有示例数据的文件，
你把这里的文件或目录来帮助你构造你的样品数据，
风味特定的目录
生成类型特定的目录
变量的特定目录