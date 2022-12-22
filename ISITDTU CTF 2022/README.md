 # **UploadEz**
 
- đầu tiên mình thấy họ cho 1 file docker để test, mình download về và nghịch thử

![1](https://user-images.githubusercontent.com/85442500/205485231-a4e32552-65a9-4903-a5fb-b6f892c1c264.png)

tiếp theo mình vào web  và thấy web chỉ hiện lên source code--> cái quái gì v

![image](https://user-images.githubusercontent.com/85442500/205485288-6a756789-83cd-41ec-a844-a9f2e845ca25.png)

mình thấy trong source có chức năng upload và 1 vài filter
để ý kĩ dòng này :file_put_contents($filename, $shell . "\nWelcome to ISITDTU CTF 2022");
đầu tiên mình thử upload shell thông qua url

![image](https://user-images.githubusercontent.com/85442500/205485555-25be63ae-a0e9-47f8-be1e-6d993e539c26.png)

mình đã up filename có chứa kí tự số và bị chặn nên mình sẽ thử lại

![image](https://user-images.githubusercontent.com/85442500/205485598-174801eb-7aba-45e6-9606-31c58f565835.png)

oke lần này đã dc, và mình sẽ quay trở lại index.php
mình thấy rằng shell không được thực thi bởi server, mình cần làm cho server chạy shell
vì vậy mình viết tệp .htaccess chứa giá trị php_auto_prepend_file sẽ làm cho shell chạy

![image](https://user-images.githubusercontent.com/85442500/205485736-be2215ce-d202-42ad-b860-0ddddd3f3cd0.png)

oke r nè. it is setting the php_value auto_prepend_file to "fucki"
then since the script is auto adding a sentence to every file we write, we need to make the .htaccess valid otherwise it will not run
so create a new line after the "fucki" and add a comment.(đoạn này viết tiếng anh cho khó hiểu)
RCE thành công server
quay trở lại index.php và thực thi shell thôi nào

![image](https://user-images.githubusercontent.com/85442500/205485829-b391aa5d-3153-4fee-8e90-90c485ac2908.png)

![image](https://user-images.githubusercontent.com/85442500/205485845-ab3b191d-4ac0-48d6-bbcf-53a944350ffa.png)

múa tiếp 

![image](https://user-images.githubusercontent.com/85442500/205485874-622e170f-8766-4da6-ad70-727814657241.png)
okila nè

![image](https://user-images.githubusercontent.com/85442500/205485890-a479c006-6956-4050-83c5-79f4bc55a99b.png)

flag nè, h thì làm lại trên server gốc của ban tổ chức là ra flag thật thôi.
