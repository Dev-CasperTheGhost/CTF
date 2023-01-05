# **NewYearBot**
Cách làm này mình có tham khảo ý tưởng của 1 a nước ngoài nickname là r.silva, mình làm lại theo hướng này để dễ hiểu hơn vì thời điểm hiện tại python mình khá non( nhưng mình sẽ cố gắng sau này ạ)

Đầu tiên mình vào web của btc xem qua 

![image](https://user-images.githubusercontent.com/85442500/210755169-bf746d82-4768-4c96-846e-01c1fc3a675f.png)

giao diện khá đơn giản có chức năng chọn yêu cầu gì đó và nó trả về cho mình những đoạn text khác nhau
mình có tải file đính kèm của challenge này về.

![image](https://user-images.githubusercontent.com/85442500/210755742-8e4d62c7-bc5b-4ba3-b48d-0dd1c9d9ea64.png)

server sử dụng Flask ( 1 framework của python)

![image](https://user-images.githubusercontent.com/85442500/210757093-2b4b9bb2-872c-403b-b559-9dedfccaa1d9.png)
![image](https://user-images.githubusercontent.com/85442500/210757188-2b3baf2b-8abc-4963-8cf9-c5f7b05b10ff.png)

mình đã lợi dụng biến greeting để nhập vào biến FL4G và dò từng kí tự của flag

![image](https://user-images.githubusercontent.com/85442500/210757478-f2f6aa9a-ef0b-4932-b7b1-b951e7af8b3d.png)

khi tới index =6 thì server không trả về kí tự flag nữa

![image](https://user-images.githubusercontent.com/85442500/210757997-de6fe856-7c9a-416f-941c-5345a5f38232.png)

mình kiểm tra hàm xác thực nó sẽ dựa vào bảng mã ascii k chấp nhận kí tự từ 57 tới 123

![image](https://user-images.githubusercontent.com/85442500/210758343-8d05bd1c-04da-4cc4-8a42-32d87771076b.png)
![image](https://user-images.githubusercontent.com/85442500/210759034-2ee55493-ec02-4653-adab-f1cdb4eec964.png)

đoạn này giải thích vì sao chúng ta nhập 6 thì k dc, nó sẽ trả về false, nhưng ở dòng 23 cho phép ta nối chuỗi nhập vào

mình sẽ lợi dụng toán tử NOT đảo ngược bit để giải tiếp câu này

![image](https://user-images.githubusercontent.com/85442500/210760019-66e9ca55-e000-4f62-9165-862032dee233.png)

mình có thử qua 1 vài chuỗi đơn giản để lấy các kí tự khác nhau mà k bị hàm trả về false
mn có thể đọc thêm ở đây https://www.geeksforgeeks.org/python-bitwise-operators/

![image](https://user-images.githubusercontent.com/85442500/210760356-cd403238-077b-4672-b070-be1ca12bb20b.png)

lần lượt lấy các kí tự tới cuối flag
TetCTF{JuSt_F0rFunn(^_^}
Cách này hơi tốn công nhưng vẫn ra
mình có xem payload của 1 a nào đó trong discord khá hay : curl phát ăn luôn flag (curl 'http://172.105.120.180:9999/?debug=import+__main__;__main__.fail=__main__.FL4G' -F 'type=FL4G' -F 'number=ｅｘｅｃ(ｄｅｂｕｇ)')
còn hơn cả tuyệt vời, đỉnh thật!!!!!!!!!
  
