//出现滚动条的对齐处理
//表主体高度超出父元素高度(出现滚动条)则将表头table宽度设置等于表主体宽度
var parentHeight=$(".tablebodyparent").height();
var contentHeight=$(".tablelistbody").height();
var contentWidth=$(".tablelistbody").width();
if(contentHeight>parentHeight){
    $(".tablelisthead").width(contentWidth);
}