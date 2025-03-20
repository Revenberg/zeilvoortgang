#!/bin/bash
cd ~    

sudo dpkg --configure -a

sudo apt update -y

sudo apt-get clean -y
sudo apt-get update -y
sudo apt-get autoremove -y

sudo apt-get install git ansible sshpass -y
sudo apt-get install docker-compose -y

git clone https://github.com/Revenberg/zeilvoortgang.git
cd ~/zeilvoortgang
git config --global pull.rebase false

cp -r ~/zeilvoortgang/ansible ~/ansible

cd ~/ansible

ansible-playbook helloworld.yml

mkdir /home/pi/ansible 2>/dev/null
mkdir /home/pi/.ssh 2>/dev/null

sudo ssh-keygen -l -f /etc/ssh/ssh_host_rsa_key
sudo ssh-keygen -l -f /etc/ssh/ssh_host_rsa_key
ifconfig eth0 | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | while read line;
do
    ssh-keyscan -H $line >> /home/pi/.ssh/known_hosts
done

ifconfig wlan0 | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | while read line;
do
    ssh-keyscan -H $line >> /home/pi/.ssh/known_hosts
done

sudo ssh-keygen -l -f /etc/ssh/ssh_host_rsa_key

pswrd=$(cat /home/pi/.pswrd)

echo "all:" > /home/pi/ansible/hosts
echo "  vars:" >> /home/pi/ansible/hosts
echo "    ansible_connection: ssh" >> /home/pi/ansible/hosts
echo "    ansible_ssh_user: pi" >> /home/pi/ansible/hosts
ansible-vault encrypt_string --vault-password-file /home/pi/.pswrd $pswrd --name '    ansible_ssh_pass'  >> /home/pi/ansible/hosts
echo "rpi:" >> /home/pi/ansible/hosts
echo "  hosts:" >> /home/pi/ansible/hosts

ifconfig wlan0 | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | while read line;
do
  echo "    $line:" >> /home/pi/ansible/hosts
  echo "      ansible_user: pi" >> /home/pi/ansible/hosts

  echo "$line" > /home/pi/ip
done

ifconfig eth0 | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | while read line;
do
  echo "    $line:" >> /home/pi/ansible/hosts
  echo "      ansible_user: pi" >> /home/pi/ansible/hosts

  echo "$line" > /home/pi/ip  
done

/sbin/ip route | awk '/default/ { print $3 }' | head -n 1 > /home/pi/gateway
curl ifconfig.me > /home/pi/ext_ip

touch /home/pi/.pswrd

cd /home/pi/home-assistant-setup
git pull
cd /home/pi

cp /home/pi/home-assistant-setup/alles.sh /home/pi/alles.sh;
chmod +x /home/pi/alles.sh;

ansible-playbook  /home/pi/home-assistant-setup/alles.yml --vault-password-file /home/pi/.pswrd -i /home/pi/ansible/hosts | tee /home/pi/alles.log

ret=$?
if [ $ret -ne 0 ]; then
    echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
else
    docker ps -a
fi

cat /var/docker-compose/docker-compose.yml | grep '\- 9' | grep -v ':'