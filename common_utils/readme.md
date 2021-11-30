# common_utils

     此模块主要用于存放所有项目可能用到的公用的工具类

#### 例如

    FileTypeUtils.java
    文件类型判断工具类
    此工具用用于根据文件的魔数来判断文件大致是什么类型
    每个文件的前几个字节为魔数,用于定义文件类型,但是不同文件的魔数可能相同,所以只能大致判断
        如:Microsoft Word/Excel 2007以上版本文件,DOCX和XLSX具有相同的魔数:504B0304
    
    ImageCompactUtils.java
    图像压缩工具类：但是只能在Windows平台使用
    