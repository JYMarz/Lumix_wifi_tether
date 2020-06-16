#HowTo

##Requirements:

The program was compiled for Windows 10, X64: I presume it will not work, using the provided jar in win32.

You will need Java Runtime Environment JRE 1.8.0 installed and the bin directory where java.exe is to be added to your PATH.

The installer is here:

https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html#license-lightbox

You will also need to have OpenCV installed, or at least the java dll for it.

For me, OpenCV 4.3.0 for windows X64

https://opencv.org/opencv-4-3-0/

###Objects detection:

If you want to use object detection, you will have to download YOLO V3 weights, cfg and names:

https://pjreddie.com/media/files/yolov3.weights

https://github.com/pjreddie/darknet/blob/master/cfg/yolov3.cfg

https://github.com/pjreddie/darknet/blob/master/data/coco.names

You can as well use the tiny version of yolov3 weights and cfg (less space on disk, faster, but less efficient):

https://pjreddie.com/media/files/yolov3-tiny.weights

https://github.com/pjreddie/darknet/blob/master/cfg/yolov3-tiny.cfg

and the same file for classes names.

https://github.com/pjreddie/darknet/blob/master/data/coco.names

Store the 3 files on your hard disk drive somewhereâ€¦

##Configuration files:

Apart from jre, you need to copy the following files, in the same directory:

Lumix_wifi_tether.jar

Launch.bat

menus.txt

If you want to use Bluetooth connected Arduino (or other device) for camera orientation,

blueconfig.txt

If you want to use YOLO detection (and have downloaded the above files):

yoloconfig.txt


Edit blueconfig.txt, yoloconfig.txt, and Launch.bat to match your configuration.

###blueconfig.txt

Leave the 2 first lines as comments;

The next is the hexadecimal address of your Bluetooth server.

The following lines are the commands to send when clicking U, D, L,R buttons (in my case, just these uppercase letters), and that which will be sent periodically (every 2 seconds) to eventually get info from the Bluetooth device (In my case, I use this command to ask for the position of the servomotors and the battery charge). The commands are completed by \r\n before being sent.

Leave the last line of the file as a comment line.

###yoloconfig.txt

Leave the 2 first lines as comments;

The 3 following lines are the absolute paths of the Yolo files, in the order .weights, .cfg, and the file containing the classes names.

The next lines contain each the name of the objects you want to detect.

Leave the last line of the file as a comment line.

###Launch.bat 

This file contains the directory where the opencv java dll is on your system: change it accordingly

This file contains the default values for different parameters that can however be passed as arguments as (Launch.bat â€œ192.168.54.1â€� â€œ12â€� and so on).

The first parameter is the IP address of the LX-100

The second is the subnet mask to find a free address on which the video flux will be read. 12 is fine

The third is the port number on which the handshake with the camera is made (see the doc), by default, it is 60606

Parameter number 4 is â€œtrueâ€� if you intend to use Bluetooth, â€œfalseâ€� otherwise.

Parameter number 5 is â€œtrueâ€� if you are using Yolo detection, â€œfalseâ€� otherwise.

Parameter 6 is â€œtrueâ€� if you want to connect to the camera, â€œfalseâ€� just for test 
purposes.

You must pass the parameters in this order.

##Usage

Once you have adapted the configuration and launcher files, 

1)	Start the wifi on your camera (new connection, remote operation)

2)	Connect your PC wifi to LX-100 server (if you never connected, see doc file)

3)	Double click Launch.bat or

4)	in a terminal (having changed the directory to where this program is):

Launch.bat 192.168.54.1 12 60606 true true true

From the name of the buttons, you can infer their usage, for most of them.

The four sliders (top left) allow to change the parameters of the image treatment used for motion detection. 

##Caveats

1)	For the size of the application screen to be scaled properly, you may have to change the properties of java.exe in the Jre/bin directory. right click on it, compatibility, modify PPP , replace PPP scaling, choose System.

2)	Although the program should work with most Panasonic cameras (untestedâ€¦), LX-100 has some limitations. All parameters that are set manually (aperture, speed, focus mode,â€¦) cannot be changed remotely (and appear greyed). 



