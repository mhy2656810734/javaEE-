function getParamByKey(key) {
    // 拿到 quarystring 中从?开头的字符串
    let param = location.search;
    // 去掉 ?
    param = param.substring(1);
    // 根据 & 进行分割
    let paramArr = param.split("&");
    // 遍历数组
    for (let elem of paramArr) {
        // 每一个数据都是一个 key = value 键值对
        // 根据等于号再分割
        let elemArr = elem.split("=");
        if (elemArr.length == 2 && elemArr[0] == key) {
            return elemArr[1]; // 返回value 值
        } 
    }
    return null;
}
