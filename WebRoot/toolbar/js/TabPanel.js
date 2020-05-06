/**
 * 选项卡类
 * Marker.King
 */
TabPanel = function(config){
  this.renderTo = config.renderTo || $(document.body);//承载容器
  this.border = config.border;//是否显示边框
  this.render = typeof this.renderTo == 'string' ? $('#'+this.renderTo) : this.renderTo;//容器对象
  this.widthResizable = config.width ? false : true;//没有设置width并且容器宽度为百分比的时候才能自动缩放
  this.heightResizable = config.height ? false : true;//同上
  this.width = config.width || '100%';//宽度
  this.height = config.height || '100%';//高度
  this.items = config.items;//选项卡
  this.active = config.active || 0;//激活哪个
  this.tabs = [];//选项卡数组
  this.scrolled = false;//是否显示了滚动条
  this.tabWidth = 110 + 4;//样式.tabpanel_mover li中width+4
  this.fixNum = 2;//右边补丁2像素
  this.scrollFinish = true;//是否滚动完毕
  this.maxLength = config.maxLength || -1;//最大选项卡数量
  this.maxzindex = 0;//层高
  
  this.init();//初始化
};

TabPanel.prototype = {
  //初次加载渲染界面
  init : function(){
    
    var tabEntity = this;
	
    if(this.widthResizable || this.heightResizable){
  	  this.render.css('overflow', 'hidden');
  	  $(window).resize(function(){
  	    tabEntity.resize();
  	  });
    }
  
    this.render.width(this.width);
    this.render.height(this.height);
    
    this.tabpanel = $('<DIV></DIV>');
    this.tabpanel.addClass('tabpanel');
    this.tabpanel.width(this.width);
    this.tabpanel.height(this.height);
    this.tabpanel.appendTo(this.render);
  
    //实例化选项卡承载层
    this.tabpanel_tab_content = $('<DIV></DIV>');
    this.tabpanel_tab_content.addClass('tabpanel_tab_content');
    this.tabpanel_tab_content.appendTo(this.tabpanel);
    
    //实例化左滚动
    this.tabpanel_left_scroll = $('<DIV></DIV>');
    this.tabpanel_left_scroll.bind('click',function(){tabEntity.moveLeft();});
    this.tabpanel_left_scroll.addClass('tabpanel_left_scroll');
    this.tabpanel_left_scroll.addClass('display_none');
    this.tabpanel_left_scroll.bind('mouseover', function(){
      var l = $(this);
      l.addClass('tabpanel_scroll_over');
      l.bind('mouseout', function(){
        l.unbind('mouseout');
        l.removeClass('tabpanel_scroll_over');
      });
    })
    this.tabpanel_left_scroll.appendTo(this.tabpanel_tab_content);
    
    //实例化右移动
    this.tabpanel_right_scroll = $('<DIV></DIV>');
    this.tabpanel_right_scroll.bind('click',function(){tabEntity.moveRight();});
    this.tabpanel_right_scroll.addClass('tabpanel_right_scroll');
    this.tabpanel_right_scroll.addClass('display_none');
    this.tabpanel_right_scroll.bind('mouseover', function(){
      var r = $(this);
      r.addClass('tabpanel_scroll_over');
      r.bind('mouseout', function(){
        r.unbind('mouseout');
        r.removeClass('tabpanel_scroll_over');
      });
    })
    this.tabpanel_right_scroll.appendTo(this.tabpanel_tab_content);
    
    //实例化移动承载层
    this.tabpanel_move_content = $('<DIV></DIV>');
    this.tabpanel_move_content.addClass('tabpanel_move_content');
    this.tabpanel_move_content.appendTo(this.tabpanel_tab_content);
    
    //实例化卡片承载容器
    this.tabpanel_mover = $('<UL></UL>');
    this.tabpanel_mover.addClass('tabpanel_mover');
    this.tabpanel_mover.appendTo(this.tabpanel_move_content);
    
    //实例化分隔条
    this.tabpanel_tab_spacer = $('<DIV></DIV>');
    this.tabpanel_tab_spacer.addClass('tabpanel_tab_spacer');
    this.tabpanel_tab_spacer.appendTo(this.tabpanel_tab_content);
    
    //实例化内容层
    this.tabpanel_content = $('<DIV></DIV>');
    this.tabpanel_content.addClass('tabpanel_content');
    this.tabpanel_content.appendTo(this.tabpanel);
    
    var t_w = this.tabpanel.width();
    var t_h = this.tabpanel.height();

    if(this.border=='none')
    {
      this.tabpanel_tab_content.css('border-top', 'none');
      this.tabpanel_tab_content.css('border-left', 'none');
      this.tabpanel_tab_content.css('border-right', 'none');
      this.tabpanel_content.css('border-left', 'none');
      this.tabpanel_content.css('border-right', 'none');
      this.tabpanel_content.css('border-bottom', 'none');
      //计算选项卡承载层和内容层宽度(重点是要去除边框的宽度)
      this.tabpanel_tab_content.width(t_w);
      this.tabpanel_content.width(t_w);
			this.tabpanel_content.height(t_h-this.tabpanel_tab_content.get(0).offsetHeight);
    }
    else
    {
      //计算选项卡承载层和内容层宽度(重点是要去除边框的宽度)
      this.tabpanel_tab_content.width(t_w-2);
      this.tabpanel_content.width(t_w-2);
			this.tabpanel_content.height(t_h-this.tabpanel_tab_content.get(0).offsetHeight-1);
    }
    
    this.update();
    
    for(var i=0; i<this.items.length; i++)
    {
      this.addTab(this.items[i]);
    }
    //激活规定的选项卡
    if(this.active>0)
      this.show(this.active, false);
  },
  //向左移动
  moveLeft : function(){
    if(this.scrollFinish)
    {
      this.disableScroll();
      this.scrollFinish = false;
      Fader.apply(this, new Array({
        element:this.tabpanel_mover,
        style:'marginLeft',
        num:this.tabWidth,
        maxMove:this.maxMove,
        onFinish : this.useableScroll
      }));
      this.run();
    }
  },
  //向右移动
  moveRight : function(){
    if(this.scrollFinish)
    {
      this.disableScroll();
      this.scrollFinish = false;
      Fader.apply(this, new Array({
        element:this.tabpanel_mover,
        style:'marginLeft',
        num:this.tabWidth*-1,
        maxMove:this.maxMove,
        onFinish : this.useableScroll
      }));
      this.run();
    }
  },
  //向左移动到头
  moveToLeft : function(){
    //未显示滚动条
    if(!this.scrolled && this.scrollFinish)
    {
      this.disableScroll();
      this.scrollFinish = false;
      var marginLeft = parseInt(this.tabpanel_mover.css('marginLeft'))*-1;
      Fader.apply(this, new Array({
        element : this.tabpanel_mover,
        style : 'marginLeft',
        num : marginLeft, 
        maxMove : this.maxMove,
        interval : 20,
        onFinish : this.useableScroll
      }));
      this.run();
    }
  },
  //向右移动到头
  moveToRight : function(){
    //显示了滚动条
    if(this.scrolled && this.scrollFinish)
    {
      this.disableScroll();
      this.scrollFinish = false;
      //向左移动了多少像素
      var marginLeft = parseInt(this.tabpanel_mover.css('marginLeft'))*-1;
      //目前总选项卡宽度
      var liWidth = this.tabpanel_mover.children().length*this.tabWidth;
      //选项卡移动容器宽度
      var cWidth = this.tabpanel_move_content.width();
      //需要移动多少
      var num = (liWidth - cWidth - marginLeft + this.fixNum)*-1;
      Fader.apply(this, new Array({
        element:this.tabpanel_mover,
        style:'marginLeft',
        num:num,
        maxMove:this.maxMove,
        onFinish : this.useableScroll
      }));
      this.run();
    }
  },
  //移动到可见位置/////////////////////////////////////////////////////////
  moveToSee : function(position){
    //是否显示了滚动条
    if(this.scrolled)
    {
      //得到选项卡位置
      var liWhere = this.tabWidth * position;
      //得到当前LI容器所在位置
      var ulWhere = parseInt(this.tabpanel_mover.css('marginLeft'));
      //移动距离
      var moveNum;
      //如果当前LI容器位置小于零
      if(ulWhere<=0)
      {
        //移动位置 = (LI容器当前位置 - 选项卡位置)转换成负值
        moveNum = (ulWhere + liWhere)*-1;
        //如果移动位置大于最大移动量,则移动到最右边
        if(((moveNum+ulWhere)*-1) >= this.maxMove)
          this.moveToRight();
        else
        {
          this.disableScroll();
          this.scrollFinish = false;
          Fader.apply(this, new Array({
            element:this.tabpanel_mover,
            style:'marginLeft',
            num:moveNum,
            maxMove:this.maxMove,
            onFinish : this.useableScroll
          }));
          this.run();
        }
      }
      else
      {
        //移动位置 = (选项卡位置 - LI容器当前位置)转换成负值
        moveNum = (liWhere - ulWhere) * -1;
        //如果移动位置大于最大移动量,则移动到最右边
        if((moveNum*-1) >= this.maxMove)
          this.moveToRight();
        else
        {
          this.disableScroll();
          this.scrollFinish = false;
          Fader.apply(this, new Array({
            element:this.tabpanel_mover,
            style:'marginLeft',
            num:moveNum,
            maxMove:this.maxMove,
            onFinish : this.useableScroll
          }));
          this.run();
        }
      }
    }
  },
  //左右滚动都禁用
  disableScroll : function(){
    this.tabpanel_left_scroll.addClass('tabpanel_left_scroll_disabled');
    this.tabpanel_left_scroll.attr('disabled',true);
    this.tabpanel_right_scroll.addClass('tabpanel_right_scroll_disabled');
    this.tabpanel_right_scroll.attr('disabled', true);
  },
  //判断是否移动到了极限
  useableScroll : function(){
    var tabEntity = this;
    //如果有滚动条
    if(this.scrolled)
    {
      //如果在最左边
      if(parseInt(tabEntity.tabpanel_mover.css('marginLeft')) == 0)
      {
        //左滚动不能用
        tabEntity.tabpanel_left_scroll.addClass('tabpanel_left_scroll_disabled');
        tabEntity.tabpanel_left_scroll.attr('disabled',true);
        //右滚动可以用
        tabEntity.tabpanel_right_scroll.removeClass('tabpanel_right_scroll_disabled');
        tabEntity.tabpanel_right_scroll.removeAttr('disabled');
      }
      //如果在最右边
      else if(parseInt(tabEntity.tabpanel_mover.css('marginLeft'))*-1 == tabEntity.maxMove)
      {
        //左滚动可以用
        tabEntity.tabpanel_left_scroll.removeClass('tabpanel_left_scroll_disabled');
        tabEntity.tabpanel_left_scroll.removeAttr('disabled',true);
        //右滚动不能用
        tabEntity.tabpanel_right_scroll.addClass('tabpanel_right_scroll_disabled');
        tabEntity.tabpanel_right_scroll.attr('disabled');
      }
      //全部能用
      else
      {
        tabEntity.tabpanel_left_scroll.removeClass('tabpanel_left_scroll_disabled');
        tabEntity.tabpanel_left_scroll.removeAttr('disabled',true);
        tabEntity.tabpanel_right_scroll.removeClass('tabpanel_right_scroll_disabled');
        tabEntity.tabpanel_right_scroll.removeAttr('disabled');
      }
    }
    
    tabEntity.scrollFinish = true;
  },
  //更新样式
  update : function(){
    var cWidth = this.tabpanel_tab_content.width();
    //如果有滚动条,则减去滚动条的宽度
    if(this.scrolled)
      cWidth -= (this.tabpanel_left_scroll.width()+this.tabpanel_right_scroll.width());
    //设置选项卡移动承载容器宽度
    this.tabpanel_move_content.width(cWidth);
    //计算最大移动量
    this.maxMove = (this.tabpanel_mover.children().length*this.tabWidth) - cWidth + this.fixNum;
  },
  //判断选项卡总长度是否超过了选项卡容器总长度,如果超过了则显示左右移动按钮
  showScroll : function(){
    //计算LI总宽度
    var liWidth = this.tabpanel_mover.children().length*this.tabWidth;
    //计算选项卡容器总宽度
    var tabContentWidth = this.tabpanel_tab_content.width();
    
    //如果LI总宽度大于选项卡容器总宽度,并且未显示选项卡滚动条
    if(liWidth > tabContentWidth && !this.scrolled)
    {
      //左右margin出18px
      this.tabpanel_move_content.addClass('tabpanel_move_content_scroll');
      //显示滚动条
      this.tabpanel_left_scroll.removeClass('display_none');
      this.tabpanel_right_scroll.removeClass('display_none');
      //滚动条已显示
      this.scrolled = true;
    }
    //如果LI总宽度小于选项卡容器总宽度,并且已显示选项卡滚动条
    else if(liWidth < tabContentWidth && this.scrolled)
    {
      //清除左右margin
      this.tabpanel_move_content.removeClass('tabpanel_move_content_scroll');
      //隐藏滚动条
      this.tabpanel_left_scroll.addClass('display_none');
      this.tabpanel_right_scroll.addClass('display_none');
      //滚动条已隐藏
      this.scrolled = false;
      
      //向左移动到头
      this.moveToLeft();
    }
  },
  //创建标签页的标题
  //@item : 标签页对象(id,title,html,closable,disabled,icon)
  //@index : 标签页的索引
  addTab : function(tabitem){
  
    if(this.maxLength!=-1 && this.maxLength<=this.tabs.length)
    {
      alert('超过了最大选项卡数量，请关闭不用的选项卡');
      return false;
    }
  
    tabitem.id = tabitem.id || Math.uuid();
    
    //如果已经存在相同ID的选项卡,则直接显示
    if($('#'+tabitem.id).length>0)
    {
      this.show(tabitem.id, false);
    }
    //当没有选项卡时,如果已经滚动结束,则添加选项卡
    else if(this.scrollFinish)
    {
      var tabEntity = this;
  
      //添加选项卡LI元素
      var tab = $('<LI></LI>');
      tab.attr('id', tabitem.id);

      tab.appendTo(this.tabpanel_mover);
  
      //添加title元素
      var title = $('<DIV></DIV>');
      title.text(tabitem.title);
      title.appendTo(tab);
      if(tabitem.icon) {
        title.addClass('icon_title');
        title.css('background-image', 'url("'+tabitem.icon+'")');
      } else {
        title.addClass('title');
      }
      
      //添加closer元素
      var closer = $('<DIV></DIV>');
      closer.addClass('closer');
      closer.attr('title', '关闭');
      closer.appendTo(tab);
      
      //添加content元素
      var content = $('<DIV></DIV>');
      content.addClass('html_content');
      content.appendTo(this.tabpanel_content);

      var child_frame = content.find('iframe');
      /*强制加上ID和NAME
      if(child_frame.length==1)
      {
        child_frame.attr('id', tabitem.id+'Frame');
        child_frame.attr('name', tabitem.id+'Frame');
      }*/
      
      //得到当前被激活的选项卡的下标
      var activedTabIndex = this.tabpanel_mover.children().index(this.tabpanel_mover.find('.active')[0]);
      
      //有可能没有被激活的选项卡,返回的是-1
      if(activedTabIndex < 0)
        activedTabIndex = 0;
      //将新添加的选项卡的父级ID设置为当前激活的选项卡ID
      if(this.tabs.length > activedTabIndex)
        tabitem.preTabId = this.tabs[activedTabIndex].id
      else
        tabitem.preTabId = '';
      
      tabitem.tab = tab;//LI对象
      tabitem.title = title;//标题
      tabitem.closer = closer;//关闭按钮
      tabitem.content = content;//内容
      tabitem.disable = tabitem.disable==undefined ? false : tabitem.disable;//是否可用
      tabitem.closable = tabitem.closable==undefined ? true : tabitem.closable;//可否关闭
      
      //是否可以关闭
      if(tabitem.closable==false)
        closer.addClass('display_none');
      
      //是否可用
      if(tabitem.disabled==true) {
        tab.attr('disabled', true);
        title.addClass('.disabled');
      }
  
      //将选项卡对象放入数组中
      this.tabs.push(tabitem);
      
      //绑定激活事件
      tab.bind('click', function(position){
        return function(){
          tabEntity.show(position, false);
        };
      }(this.tabs.length-1));
      
      //绑定关闭事件
      closer.bind('click', function(position){
        return function(){
          tabEntity.kill(position);
        };
      }(this.tabs.length-1));
      
      //绑定双击关闭事件
      if(tabitem.closable)
      {
        tab.bind('dblclick', function(position){
          return function(){
            tabEntity.kill(position);
          };
        }(this.tabs.length-1));
      }
      
      //是否直接显示刚添加的选项卡
      if(!tabitem.lazyload) {
        this.show(this.tabs.length-1, false);
      }
      
      //判断显示滚动条
      this.showScroll();
      //更新宽度
      this.update();
      
      //向左移动到头
      if(!tabitem.lazyload) {
        this.moveToRight();
      }
    }
  },
  //如果位置是字符串,则根据ID得到选项卡下标
  getTabPosision : function(tabId){
    if(typeof tabId == 'string')
    {
      for(var i=0; i<this.tabs.length; i++)
      {
        if(tabId == this.tabs[i].id)
        {
          tabId = i;
          break;
        }
      }
    }
    return tabId;
  },
  //重新加载content
  flush : function(position)
  {
    //得到下标
    position = this.getTabPosision(position);
    //如果没有该选项卡,则不执行刷新
    if(typeof position == 'string')
      return false;
    else
    {
      //如果有IFRAME,则执行递归刷新
      var iframes = this.tabs[position].content.find('iframe');
      if(iframes.length>0)
      {
        var frameId = this.tabs[position].id+'Frame';
        this.iterateFlush(window.frames[frameId]);
      }
    }
  },
  //递归刷新
  iterateFlush : function(iframeObj) {
    /**必须使用frames才能得到相应对象*/
    
    //如果当前frame中有多个frame,则再次递归刷新
    if(iframeObj.window.frames.length>0)
    {
      for(var i=0; i<iframeObj.window.frames.length; i++)
      {
        this.iterateFlush(iframeObj.window.frames[i]);
      }
    }
    else
    {
      //将frame中的所有form提交
      if(iframeObj.document.forms.length>0)
      {
        for(var i=0; i<iframeObj.document.forms.length; i++)
        {
          //form提交时遇到异常,则将该页面刷新
          try {
            iframeObj.document.forms[i].submit();
          }
          catch(e) {
            iframeObj.location.reload();
          }
        }
      }
      //没有form,直接刷新
      else
      {
        iframeObj.location.reload();
      }
    }
  },
  //显示选项卡
  show : function(position, notExecuteMoveSee){
    //得到下标
    position = this.getTabPosision(position);
    if(typeof position == 'string')
      position = 0;
    //如果没有选项卡则停止方法执行
    if(this.tabs.length<1)
      return false;

    //是否滚动结束
    if(this.scrollFinish)
    {
      //目标下标如果大,则激活0
      if(position >= this.tabs.length)
      {
        position = 0;
      }
      //将目标面板显示
      this.tabs[position].content.css('z-index', ++this.maxzindex);
      //如果选项卡没有打开
      if(this.tabs[position].tab.hasClass('active'))
      {
        //选项卡移动到可见位置
        if(!notExecuteMoveSee)
        {
          this.moveToSee(position);
        }
      }
      else
      {
        //如果选项卡没有加载过页面则加载
        if(this.tabs[position].content.html()=='') {
          this.tabs[position].content.html(this.tabs[position].html);
        }
        //清除掉已经打开的选项卡激活样式
        this.tabpanel_mover.find('.active').removeClass('active');
        //将目标选项卡样式设置为激活
        this.tabs[position].tab.addClass('active');
        //选项卡移动到可见位置
        if(!notExecuteMoveSee)
        {
          this.moveToSee(position);
        }
      }
    }
  },
  //关闭选项卡
  kill : function(position){
  
    var tabEntity = this;
    //得到下标
    position = this.getTabPosision(position);
    
    var preTabId = this.tabs[position].preTabId;
    
    //按照DOM顺序移除
    this.tabs[position].closer.remove();
    this.tabs[position].title.remove();
    this.tabs[position].tab.remove();
    this.tabs[position].content.remove();
    //将数组中的元素删除
    this.tabs.splice(position,1);
    
    //因下标改变,重新为选项卡绑定事件
    for(var i=0 ; i<this.tabs.length; i++)
    {
      this.tabs[i].tab.unbind('click');
      this.tabs[i].tab.bind('click', function(i){
        return function(){
          tabEntity.show(i, false);
        };
      }(i));
      this.tabs[i].closer.unbind('click');
      this.tabs[i].closer.bind('click', function(i){
        return function(){
          tabEntity.kill(i);
        };
      }(i));
      if(this.tabs[i].closable)
      {
        this.tabs[i].tab.unbind('dblclick');
        this.tabs[i].tab.bind('dblclick', function(i){
          return function(){
            tabEntity.kill(i);
          };
        }(i));
      }
    }
    
    //如果没有已打开的选项卡,则显示上次打开的选项卡
    if(this.getActiveTab() == null)
      this.show(preTabId, false);
    else
      this.moveToSee(position);
    //更新宽度
    this.update();
    //更新滚动条
    this.showScroll();
  },
  
  //获取标签的数量
  getTabsCount : function(){
    return this.tabs.length;
  },

  //设置标签的标题（title属性）
  setTitle : function(position,title){
    position = this.getTabPosision(position);
    if(position < this.tabs.length)
      this.tabs[position].title.text(title);
  },

  //获取标签的标题（title属性）
  getTitle : function(position){
    position = this.getTabPosision(position);
    return this.tabs[position].title.text();
  },

  //设置标签的内容（html属性）
  setContent : function(position,content){
    position = this.getTabPosision(position);
    if(position < this.tabs.length)
      this.tabs[position].content.html(content);
  },

  //获取标签的内容（html属性）
  getContent : function(position){
    position = this.getTabPosision(position);
    return this.tabs[position].content.html();
  },

  //设置标签是否可以使用（disable属性）
  setDisable : function(position,disable){
    position = this.getTabPosision(position);
    if(position < this.tabs.length){
      this.tabs[position].disable = disable;
      if(disable){
        this.tabs[position].tab.attr('disabled',true);
        this.tabs[position].title.addClass('.disabled');
      }else{
        this.tabs[position].tab.removeAttr('disabled');
        this.tabs[position].title.removeClass('.disabled');
      }
    }
  },

  //获取标签使用的状态（disable属性）
  getDisable : function(position){
    position = this.getTabPosision(position);
    return this.tabs[position].disable;
  },

  //设置标签是否可以关闭（closable属性）
  setClosable : function(position,closable){
    position = this.getTabPosision(position);
    if(position < this.tabs.length){
      this.tabs[position].closable = closable;
      if(closable){
        this.tabs[position].closer.addClass('display_none');
      }else{
        this.tabs[position].closer.addClass('closer');
        this.tabs[position].closer.removeClass('display_none');
      }
    }
  },

  //获取标签关闭的状态（closable属性）
  getClosable : function(position){
    position = this.getTabPosision(position);
    return this.tabs[position].closable;
  },
	
	getActiveIndex : function(){
		return this.tabpanel_mover.children().index(this.tabpanel_mover.find('.active')[0]);	
	},
  
  getActiveTab : function(){
    var activeTabIndex = this.tabpanel_mover.children().index(this.tabpanel_mover.find('.active')[0]);
    if(this.tabs.length > activeTabIndex)
      return this.tabs[activeTabIndex];
    else
      return null;
  },
  resize : function(){

  	//计算选项卡承载层和内容层宽度(重点是要去除边框的宽度)
  	var re_w_fix = this.border == 'none' ? 0 : 2;
		var re_h_fix = this.border == 'none' ? 0 : 1;
  
  	if(this.widthResizable)
  	{
  	  this.width = this.render.width();
  	  this.tabpanel.width(this.width);
  	  this.tabpanel_tab_content.width(this.width-re_w_fix);
  	  this.tabpanel_content.width(this.width-re_w_fix);
  	}
  	if(this.heightResizable)
  	{
  	  this.tabpanel.height(this.render.height());
  	  this.height = this.render.height();
  	  this.tabpanel_content.height(this.height-this.tabpanel_tab_content.get(0).offsetHeight-re_h_fix);
  	}
  
    this.update();
  	this.showScroll();
  	this.useableScroll();
		
		var entity = this;
		setTimeout(function(){entity.moveToSee(entity.getActiveIndex());}, 200);
		
  }
};