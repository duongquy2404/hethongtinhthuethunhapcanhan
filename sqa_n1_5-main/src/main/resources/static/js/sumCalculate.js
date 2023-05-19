//Tinh tong 1
var TNVN = document.getElementsByName("thuNhapTaiVietNam")[0];
var TNQT = document.getElementsByName("thuNhapNgoaiVietNam")[0];
TNVN.addEventListener('change', tongThuNhap);
TNQT.addEventListener('change', tongThuNhap);

function tongThuNhap() {
    var tongThuNhap = [TNVN, TNQT];
    document.getElementsByName("tongThuNhap")[0].value = calculateSum(tongThuNhap);
}

//Tinh tong 2
var GTCN = document.getElementsByName("giamTru_CaNhan")[0];
var GTNPT = document.getElementsByName("giamTru_NguoiPhuThuoc")[0];
var GTTT = document.getElementsByName("giamTru_TuThien")[0];
var GTBH = document.getElementsByName("giamTru_BaoHiem")[0];

var ds_GT = [GTCN, GTNPT, GTTT, GTBH];

for (var i = 0; i < ds_GT.length; i++) {
    ds_GT[i].addEventListener('change', tongGiamTru);
}

function tongGiamTru() {
    document.getElementsByName("DSGT")[0].value = calculateSum(ds_GT);
}



//Ham tinh tong
function calculateSum(numbers) {
    var sum = 0;
    for (var i = 0; i < numbers.length; i++) {
        sum += parseInt(numbers[i].value);
    }
    return sum;
}