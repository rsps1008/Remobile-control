import socket
import sys
from tkinter import *
import tkinter as tk
import threading
import pyautogui
import time
import math
import cmath
import random
import base64
import turtle
from urllib.request import urlopen

win=Tk()
win.title(u"Advance OS期末專題")
#win.geometry('250x400');
win.resizable(0,0)
msg2 = ""

def INITIALIZE():
	global ctcp2, passwd

	# 建立 socket 
	ctcp2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
	print ("已開啟CLIENT2")

	# 設定Server IP
	host = "140.117.169.145"
	#host = "218.161.105.93"

	# 設定Port
	port = 12345

	# 連接Server IP、Port
	ctcp2.connect_ex((host, port))

	#傳送User種類
	ctcp2.send("PC".encode())
	
	#passwd.insert ( 0, "KEY")

def CONNECT():
	global passwd, printmessage, password
	
	password =  random.randint(1000,9999)
	passwd.delete(0, END);  passwd.insert ( 0, password)
	#printmessage.insert(1.0,  password)
	LOGIN()
	thread = threading.Thread(target = MAIN)
	thread.start()

def RECIEVELOGININFO():
	global ctcp2, printmessage, passwd
	
	msg2 = ctcp2.recv(1024).decode('UTF-8')
	if msg2 == "Connect Fail":
		passwd.delete(0, END);
		temp = msg2 + ",請重新輸入密碼連線。"
		INITIALIZE()
		printmessage.delete('1.0', END)
		printmessage.insert(1.0,  temp)
	else:
		printmessage.delete('1.0', END)
		printmessage.insert(1.0,  msg2)
	
	

def LOGIN():
	global ctcp2, printmessage, password
	
	#登入密碼、連線資訊
	ctcp2.send(str(password).encode())
	
	#接收登入資訊
	thread = threading.Thread(target = RECIEVELOGININFO)
	thread.start()


def MAIN():
	global ctcp2, printmessage

	while True:
		msg = ctcp2.recv(1024).decode('UTF-8')
		if msg == "Android端斷線":
			temp = msg + ",請重新輸入密碼連線。"
			printmessage.delete('1.0', END)
			printmessage.insert(1.0,  temp)
			ctcp2.close();  INITIALIZE();
		else:
			if msg=="MouseInputS":
				controling = True
				while controling == True:
					msg = ctcp2.recv(1024).decode('UTF-8')
					if msg == "KeyboardInput":
						msg = ctcp2.recv(1024).decode('UTF-8')
						pyautogui.typewrite(msg)
					elif msg == "buttonL":
						pyautogui.click(button='left')
					elif msg == "buttonR":
						pyautogui.click(button='right')
					elif msg != "EndofKeyboard":
						s = int(msg)
						if s < 90 :
							pyautogui.moveRel(40-s/90*40, 0-s/90*40, duration=0.04)
						elif s < 180:
							pyautogui.moveRel(0-(s-90)/90*40, -40+(s-90)/90*40, duration=0.04)
						elif s < 240:
							pyautogui.moveRel(-40+(s-180)/90*40, (s-180)/90*40, duration=0.04)
						else :
							pyautogui.moveRel((s-240)/90*40, 40-(s-240)/90*40, duration=0.04)
					else:
						break;
			elif msg == "next":
				pyautogui.press('pagedown');
			elif msg == "previous":
				pyautogui.press('pageup');
			elif msg == "page":
				p = ctcp2.recv(1024).decode('UTF-8')
				pyautogui.typewrite(p, 0.15)
				pyautogui.press('enter');
			elif msg == "nextlink":
				pyautogui.press('tab');
			elif msg == "previouslink":
				pyautogui.keyDown('shiftleft');
				pyautogui.press('tab');
				pyautogui.keyUp('shiftleft')
			elif msg == "enter":
				pyautogui.press('enter');
			

			printmessage.delete('1.0', END)
			printmessage.insert(1.0,  msg)


if __name__ == '__main__':
	global ctcp2, password, printmessage, passwd
	
	b1 = tk.Button(win,text="Connect",command=INITIALIZE)
	b1.grid(padx=4,row=0,column=0)
	passwd= tk.Entry(win,width=20)
	passwd.grid(padx=4,row=1,column=0)
	#INITIALIZE()
	b2 = tk.Button(win,text="Display Pair Key",command=CONNECT)
	b2.grid(padx=4,row=2,column=0)
	printmessage = tk.Text(win, height=2, width=20)
	printmessage.grid(row=3,column=0)
	
	image_url = "http://db.cse.nsysu.edu.tw/~linjt/images/1.png"
	image_byt = urlopen(image_url).read()
	image_b64 = base64.encodestring(image_byt)
	photo=PhotoImage(data=image_b64)
	label=Label(image=photo)
	label.grid(row=0,column=1,rowspan=4,sticky=W+E+N+S, padx=5, pady=6)
	win.mainloop()
