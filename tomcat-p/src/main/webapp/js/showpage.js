var itable = document.getElementById("list");
var totalRecordNum = itable.rows.length;//总记录条数
if(totalRecordNum == 0){
    document.getElementById("curPageNo").innerHTML = 0;
    document.getElementById("totalPageNo").innerHTML = 0;
}

var currentPageNumber = 1; //当前页号
if(totalRecordNum == 0) {
    currentPageNumber = 0;
}
var perPageRecordNum = 15; //每页记录条数
var totalPageNumber = Math.ceil(totalRecordNum/perPageRecordNum); //计算总页数
var startShowPage = (currentPageNumber - 1) * perPageRecordNum;  //开始显示页记录号数
var endShowPage = currentPageNumber * perPageRecordNum;    //结束显示页记录号数
//回车键和点击“go”的效果一样
function keyDown() {
    if(event.keyCode == 13) {
        goToPage();
    }
}

//跳到指定页
function goToPage() {
    if(totalRecordNum != 0) {
        var pageNo = document.getElementById("pageNum").value;
        if((/[^0-9]/gi.test(pageNo)) | pageNo.length == 0) {
            alert("页数必须为数字！");
            return;
        } else {
            if((pageNo > totalPageNumber) | (pageNo < 1)) {
                alert("输入的页数超出范围啦！");
                return;
            }
        }
        startShowPage = (pageNo - 1) * perPageRecordNum;
        endShowPage = pageNo * perPageRecordNum;
        exeShowPage();
        currentPageNumber = pageNo;
        document.getElementById("curPageNo").innerHTML = currentPageNumber;
        document.getElementById("totalPageNo").innerHTML = totalPageNumber;
    }
}
function goToFirstPage() {
    if(totalRecordNum != 0) {
        startShowPage = 0 * perPageRecordNum;
        endShowPage = 1 * perPageRecordNum;
        currentPageNumber = 1;
        exeShowPage();
        document.getElementById("curPageNo").innerHTML = currentPageNumber;
        document.getElementById("totalPageNo").innerHTML = totalPageNumber;
    }
}
function goToLastPage() {
    var tempVal;
    if(totalRecordNum != 0) {
        if(totalRecordNum % perPageRecordNum == 0) {
            tempVal = totalRecordNum / perPageRecordNum;
            startShowPage = (tempVal - 1) * perPageRecordNum;
            endShowPage = tempVal * perPageRecordNum;
        } else {
            tempVal = totalRecordNum % perPageRecordNum;  //得到余数，就是最后一页要显示的记录条数
            startShowPage = totalRecordNum - tempVal;
            endShowPage = totalRecordNum;
        }
        currentPageNumber = totalPageNumber;
        exeShowPage();
        document.getElementById("curPageNo").innerHTML = currentPageNumber;
        document.getElementById("totalPageNo").innerHTML = totalPageNumber;
    }
}
function goToNextPage() {
    if(totalRecordNum != 0) {
        if(currentPageNumber < totalPageNumber) {
            currentPageNumber = Number(currentPageNumber)+1;
            startShowPage = (currentPageNumber-1)*perPageRecordNum;
            endShowPage  = currentPageNumber*perPageRecordNum;
            exeShowPage();
            document.getElementById("curPageNo").innerHTML = currentPageNumber;
            document.getElementById("totalPageNo").innerHTML = totalPageNumber;
        } else {
            //alert("当前已经是最后一页了！");
        }
    }
}
function goToBeforePage() {
    if(totalRecordNum != 0) {
        if(currentPageNumber > 1) {
            currentPageNumber = currentPageNumber-1;
            startShowPage = (currentPageNumber-1)*perPageRecordNum;
            endShowPage  = currentPageNumber*perPageRecordNum;
            exeShowPage();
            document.getElementById("curPageNo").innerHTML = currentPageNumber;
            document.getElementById("totalPageNo").innerHTML = totalPageNumber;
        } else {
            alert("当前已经是第一页了！");
        }
    }
}
function exeShowPage() {
    for(var i = 0; i < totalRecordNum; i++) {
        var irow = itable.rows[i];
        if((i >= startShowPage) && (i < endShowPage)) {
            irow.style.display = "";
        } else {
            irow.style.display = "none";
        }
    }
}
