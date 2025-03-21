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

cp -r ~/zeilvoortgang/ansible ~/

cd ~/ansible

ansible-playbook db.yml

