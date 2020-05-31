



# 扫雷游戏

# 引言

《扫雷》是一款大众类的益智小游戏，于1992年发行。游戏目标是在最短的时间内根据点击格子出现的数字找出所有非雷格子，同时避免踩雷，踩到一个雷即全盘皆输。

《扫雷》作为windows xp操作系统上最为经典的游戏之一，众人皆知，相信大部分人也都有游戏的体验。尽管随着计算机的发展，windows的更新，这款游戏变得不再如此流行，但其仍然是一款永不过时的经典。

在windows10中的扫雷，更加现代化，拥有美丽的背景、复杂的游戏设定等，更加符合当下人们的审美。但其游戏核心却从未改变。本次项目，使用java原生开发，不借助任何图片等游戏素材，来实现扫雷。

游戏虽然简单，但功能齐全，提供了地雷计数器，计时器，支持左键单击展开格子，右键单击，进行地雷标记或者不确定标记，也可以右键取消标记。游戏难度完全由用户指定，包括地图大小和地雷数目。

# 设计依据及框图

## 设计思想

### 面向对象

java作为一门完全的面向对象的语言，本游戏设计时充分考虑面向对象的特征，进行了类的封装。

其中，主要窗口为`MineFrame`，继承自`JFrame`，是整个游戏的窗口。其包含有`toolbar`用来进行排列按钮、标签或者文本框，设置了**开始游戏**，**查看提示**，**重开本局**，**难度设定**等四个功能选项，**剩余雷数**和**游戏用时**两个信息提示。

其中间为`MineMap`，继承自`JPanel`，是扫雷的游戏区域。这个类记录了一系列游戏状态，包括游戏格子的宽和高，地雷数，是否进行提示，是否结束等，还包括了一个二维数组，数组内放置按钮，相当于单元格。

为了更好的适应游戏，设计了`MineCell`继承自`JButton`，作为按钮，供点击，同时添加了一些额外属性。如，其位置索引，是否是地雷，其当前标记状态等。

用户首先进行**难度设定**，输入地图尺寸和地雷数目，然后进行初始化地图，初始化地雷等。之后点击**开始游戏**后，计时器启动，开始计时。游戏过程中，左键单击进行区域展开，如果是地雷则游戏失败，同时显示完整地图；否则展开。左键单击进行标记，因此，又设计了枚举类型。

`CellType`作为枚举类，枚举了几种状态，标识某个格子的状态。此外，还对用户的操作类型进行了封装，`OperationType`枚举了用户不同的操作类型。

为了设计样式，重点考虑颜色设置，因此设计了`ColorType`封装了不同状态对应的颜色。

计时功能则使用了线程，`TimeThread`实现了`Runnable`接口，进行计时，同时显示在面板上。

当用户展开了全部的安全区域，即获得胜利。用户可以中途选择**重开一局**，会以当前游戏难度进行地雷重新分配，重新开始游戏，并重新计时。

## 系统总体结构框图

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1588743381693.png" alt="1588743381693" style="zoom: 80%;" />

# 各模块功能实现

## 地图生成

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1588744159052.png" alt="1588744159052" style="zoom:50%;" />

1. 输入地图尺寸和地雷数目：使用JOptionPane，借助文本输入框，获得用户输入（String)，利用字符串分隔转化为数值，进行赋值，设置游戏属性。

2. 添加按键设置：遍历二维按键数组，初始化按键，设置其位置，并添加按键点击事件，然后将其加入到JPanel中。

   其中，每个单元格有如下属性：

   ```java
   	int px; // 在地图中的位置x
       int py; // 在地图中的位置y
       int num; // 周边8个单元格的地雷的数目
       boolean mine = false; // 是否有地雷
       CellType cellType = CellType.EMPTY; // 标记状态
   ```

3. 初始化地雷：产生`numMines`个地雷，地雷的坐标`x`和`y`使用随机数生成，范围在`[0, w)`和`[0, h)`之间，确保位置不重合。

   ```java
   for(int i = 0; i < numMine; i++){
       Random random = new Random();
       int xw = random.nextInt(w);
       int xh = random.nextInt(h);
       if(mineCells[xw][xh].isMine()){
       	i--;
       }else{
       	mineCells[xw][xh].setMine(true);
       //  mineCells[xw][xh].setBackground(ColorType.MINE); // 显示地雷
       }
   }	
   ```

   

4. 格子计算：进行周边地雷数计算，调用`setNumAround`方法计算，遍历单元格x所在的九宫格：

   ```java
       private void setNumAround(){
           int i, j, k, num, x, y;
           for (i = 0; i < h; i++) {
               for (j = 0; j < w; j++) {
                   num = 0;
                   if (!mineCells[i][j].isMine()) {
                       for (x = i - 1; x <= i + 1; x++) {
                           for (y = j - 1; y <= j + 1; y++) {
                               if (x >= 0 && x < w && y >= 0 && y < h && mineCells[x][y].isMine())  //判断数组是否发生越界以及是否有雷
                                   num++;
                           }
                       }
                       mineCells[i][j].setNum(num);
                   }
               }
           }
       }
   ```

   至此，地图完全生成，包括地雷位置和每个格子的属性。

## 扫雷展开

区域展开按照以下原则：

- 该位置所在九宫格无地雷，即`num = 0`。

- 该位置没有越界，且没有被展开过，即

  ```java
  x >= 0 && x < w && y >= 0 && y < h && mineCells[x][y].getCellType() == CellType.EMPTY
  ```

展开设定有如下操作：

- 绝对安全区，进行展开

  ```java
  if (mineCells[x][y].getNum() == 0) {
      setSafetyUI(mineCells[x][y]);
      extendSafeZone(x, y);//判断是否越界、是否已经展开、是否为雷区{
  }	
  ```

- 相对安全区，即无地雷，但是周围有地雷，即达到了边界：

  ```java
  mineCells[i][j].setCellType(CellType.SAFETY);
  setBorderUI(mineCells[i][j]);
  ```

其中设置样式，封装函数：

- 边界区域：

  ```java
   private void setBorderUI(MineCell mineCell){
          mineCell.setText(mineCell.getNum() + "");
          mineCell.setForeground(Color.lightGray);
  
          int num = mineCell.getNum();
          Color color;
          switch (num){
              case 1: color = ColorType.ONE; break;
              case 2: color = ColorType.TWO; break;
              case 3: color = ColorType.THREE; break;
              case 4: color = ColorType.FOUR; break;
              case 5: color = ColorType.FIVE; break;
              case 6: color = ColorType.SIX; break;
              case 7: color = ColorType.SEVEN; break;
              case 8: color = ColorType.NIGHT; break;
              default: color = ColorType.SAFETY;
          }
          mineCell.setBackground(color);
      }
  ```

  

- 安全区域：

  ```java
      private void setSafetyUI(MineCell mineCell){
          mineCell.setText(mineCell.getNum() + "");
          mineCell.setForeground(Color.lightGray);
          mineCell.setBackground(ColorType.SAFETY);
      }
  
  ```

## 类型标记

借助JButton的事件响应，添加鼠标事件：

```java
mineCells[x][y].addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        isClicked(e);
	}
});
```

- 游戏未开始：提示用户点击开始游戏

- 为提示模式：点击单元格提示用户位置是地雷还是安全区域

- 右键得到点击的单元格，获取单元格的状态，根据状态判断操作类型：

  - `EMPTY`：设置为`FLAG`表示标记了雷，显示为`$`
  - `FLAG`：设置为`UNSURE`，表示未知，显示为`?`
  - `UNKNOW`：设置为`CANCEL`，表示取消标记

- 根据操作类型设置属性：设置对应的样式和状态。

  ```java
  case FLAG: setFlagUI(mineCell); mineCell.setCellType(CellType.FLAG);break;
  case UNSURE: setUnknownUI(mineCell); mineCell.setCellType(CellType.UNKNOWN);break;
  case CANCEL: setEmptyUI(mineCell); mineCell.setCellType(CellType.EMPTY);break;
  ```

## 进程判定

- 当左键点击单元格，单元格为地雷时，失败，结束计时，显示所有地雷和单元格的属性，游戏结束。

  ```java
  if(mineCell.isMine()){
      JOptionPane.showMessageDialog(null, "你输掉了");
      showAll();
      if(timeThread != null){
          timeThread.setStart(false);
          System.out.println("end");
      }
  	return;
  }
  ```

- 当地图中状态为`SAFETY`的单元格数目加上地雷数等于全部格子数时，游戏胜利：

  ```java
      private boolean win(){
          int cnt = 0;
          for(int i = 0; i < h; i++){
              for(int j = 0; j < w; j++){
                  if(mineCells[i][j].getCellType() == CellType.SAFETY){
                      cnt++;
                  }
              }
          }
          System.out.println(cnt + " " + numMine + " = " + cnt + numMine);
          return cnt + numMine == h * w;
  
      }
  ```

## 游戏计时

设计类`TimeThread`实现了`Runnable`接口，重写`run()`方法。每隔1秒刷新时间，并显示在文本框内。

```java
    @Override
    public void run() {
        while (start){
            long endTime =  System.currentTimeMillis();
            long usedTime = (endTime - startTime) / 1000;
            textField.setText(usedTime + " 秒");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
```

在点击**游戏开始**后，初始化线程，开始计时；点击**重开一局**，重新设置线程的`startTime`，重新计时。当胜利或者失败后，设置`start = false`，结束计时，线程退出。

游戏计时使用`JTextField`显示，设置其不可被用户编辑。

```java
     costTime.setEditable(false);
```



# 运行测试

1. 启动`MineFrame`中的`main()`函数，显示窗口，设置游戏难度。

  ![](https://frozenwhale.oss-cn-beijing.aliyuncs.com/img/smine.png)

2. 点击开始游戏，进行状态标记(绿色为地雷标记，黄色为不确定标记)

   ![](https://frozenwhale.oss-cn-beijing.aliyuncs.com/img/mine2.png)
   
3. 如果点击了地雷，输掉，游戏结束。

![](https://frozenwhale.oss-cn-beijing.aliyuncs.com/img/mine3.png)