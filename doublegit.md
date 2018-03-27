1,创建两个git的key  
ssh-keygen -t rsa -C "user1@email.com"  
ssh-kegen -t rsa -C "user2@email.com"  
2，在.ssh 下创建config  
Host github.com  
    HostName github.com  
    User easesstone  
    PreferredAuthentications publickey  
    IdentityFile ~/.ssh/id_rsa  
Host github1  
    HostName github.com  
    User unnunique  
    PreferredAuthentications publickey  
    IdentityFile ~/.ssh/id_rsa_947538642  

3,执行如下命令  
ssh-agent bash  
ssh-add ~/.ssh/id_rsa_work  
ssh-add ~/.ssh/id_rsa_personal  

4,记得在github 中添加public key（如果还不行，就在~/.gitignore 下添加用户名和邮箱） 

4,在访问github1,即unnunique 的时候，
 把git@github.com:unnunique@demo.git 换成
 git@github1:easesstone.git
