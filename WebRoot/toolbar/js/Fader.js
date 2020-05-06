/**
 * 渐变类
 * Marker.King
 */
Fader = function(config){
  this.element = config.element;
  this.elementID = config.elementID;
  this.style = config.style;
  this.num = config.num;
  this.maxMove = config.maxMove;
  this.finishNum = 'string';
  this.interval = config.interval || 10;
	this.step = config.step || 10;
  this.onFinish = config.onFinish;
  this.isFinish = false;
  this.timer = null;
  this.method = this.num >= 0;//+为true,-为false
  
  this.run = function(){
    //清除计时器
    clearInterval(this.timer);
    //运行渐变效果
    this.fade();
    //判断渐变是否完成
    if(this.isFinish) {
      //执行完成后函数
      if(this.onFinish) {
        this.onFinish();
      }
    }
    else {
      //获得本实例
      var thisO = this;
      //设置计时器执行本实例的run函数
      this.timer = setInterval(function(){
        thisO.run();
      }, this.interval);
    }
  };
  
  //渐变函数
  this.fade = function(){
    
    //加载被渐变对象
    var element = this.elementID ? $('#'+this.elementID) : this.element;
    //判断目标值是否是数字类型
    if(this.finishNum == 'string') {
      //将对象的样式值赋值给目标值
      this.finishNum = (parseInt(element.css(this.style)) || 0) + this.num;
    }
    
    //实例化临时记录值
    var tempNum = parseInt(element.css(this.style)) || 0;
    
    //如果目标值大于临时记录值,并且方法为+
    if(this.finishNum > tempNum && this.method)
    {
      tempNum += this.step;
      //如果临时值大于0,则停止效果
      if(tempNum >= 0)
      {
        this.finishNum = tempNum = 0;
      }
    }
    //如果目标值小于临时值并且方法为-
    else if(this.finishNum < tempNum && !this.method)
    {
      tempNum -= this.step;
      //如果临时值(正值)大于最大移动值,则停止效果,并移动到最大移动值(负值)的位置
      if((tempNum*-1) >= this.maxMove)
        this.finishNum = tempNum = (this.maxMove*-1);
    }
    
    //如果效果执行完毕,则清空所有内容
    if((this.finishNum <= tempNum && this.method) || (this.finishNum >= tempNum && !this.method))
    {
      element.css(this.style, this.finishNum + 'px');
      this.isFinish = true;
      this.finishNum = 'string';
    }
    else
      element.css(this.style, tempNum + 'px');
  };
};