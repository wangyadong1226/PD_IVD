/**
 * ������
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
  this.method = this.num >= 0;//+Ϊtrue,-Ϊfalse
  
  this.run = function(){
    //�����ʱ��
    clearInterval(this.timer);
    //���н���Ч��
    this.fade();
    //�жϽ����Ƿ����
    if(this.isFinish) {
      //ִ����ɺ���
      if(this.onFinish) {
        this.onFinish();
      }
    }
    else {
      //��ñ�ʵ��
      var thisO = this;
      //���ü�ʱ��ִ�б�ʵ����run����
      this.timer = setInterval(function(){
        thisO.run();
      }, this.interval);
    }
  };
  
  //���亯��
  this.fade = function(){
    
    //���ر��������
    var element = this.elementID ? $('#'+this.elementID) : this.element;
    //�ж�Ŀ��ֵ�Ƿ�����������
    if(this.finishNum == 'string') {
      //���������ʽֵ��ֵ��Ŀ��ֵ
      this.finishNum = (parseInt(element.css(this.style)) || 0) + this.num;
    }
    
    //ʵ������ʱ��¼ֵ
    var tempNum = parseInt(element.css(this.style)) || 0;
    
    //���Ŀ��ֵ������ʱ��¼ֵ,���ҷ���Ϊ+
    if(this.finishNum > tempNum && this.method)
    {
      tempNum += this.step;
      //�����ʱֵ����0,��ֹͣЧ��
      if(tempNum >= 0)
      {
        this.finishNum = tempNum = 0;
      }
    }
    //���Ŀ��ֵС����ʱֵ���ҷ���Ϊ-
    else if(this.finishNum < tempNum && !this.method)
    {
      tempNum -= this.step;
      //�����ʱֵ(��ֵ)��������ƶ�ֵ,��ֹͣЧ��,���ƶ�������ƶ�ֵ(��ֵ)��λ��
      if((tempNum*-1) >= this.maxMove)
        this.finishNum = tempNum = (this.maxMove*-1);
    }
    
    //���Ч��ִ�����,�������������
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