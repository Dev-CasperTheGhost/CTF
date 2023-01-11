hello tất cả mn, mình có làm 1 số challenge và thấy có 1 challenge hay nên mình viết lại cho zui!!!

**old_06**

![image](https://user-images.githubusercontent.com/85442500/211837102-6c56a42c-c6ae-4105-9505-1a344d15abe0.png)

mở lên web thì có hiện giao diện với vài dòng text gồm ID và PW, mình sẽ bấm vào view source

![image](https://user-images.githubusercontent.com/85442500/211837628-bdfef37b-56b0-4c32-82d8-2760f0a22bbf.png)

đọc qua mình nhận thấy web này đã encode user "guest" và password và set vào cookie

![image](https://user-images.githubusercontent.com/85442500/211837993-fe3c0e5a-9f0d-4747-8b5b-24f39b6d55bd.png)

vậy nếu mình áp dụng ngược lại với user "admin" thì sao

bên trên thì đã có vòng for encode 20 lần và hàm replace 1 vài 1 kí tự sau đó khi vào cookie sẽ là urlencode 

oke bắt đầu nè, file code mình sẽ để ở trên (có gì mn cứ chat fb mình nha)

![image](https://user-images.githubusercontent.com/85442500/211839878-d2530cc8-fcb4-4d4a-9118-954ef1e9056d.png)

 
mình sử dụng 1 vài thư viện trên py để xử lí bài này

![image](https://user-images.githubusercontent.com/85442500/211840080-ecbb56d8-0562-4fe0-b7a2-8634b8fd25c0.png)

value của user và password đã có thì mình set cookie 
![image](https://user-images.githubusercontent.com/85442500/211840447-88532675-7d8f-4ba5-8697-0ae07f0352d0.png)


![image](https://user-images.githubusercontent.com/85442500/211840851-4302784b-e105-47a9-ab95-438d919d10cb.png)

oke pwned;


