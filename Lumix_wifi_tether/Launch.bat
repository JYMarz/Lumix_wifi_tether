rem Usage: Control.bat [browser [camera_ip [camera_ip_mask]]] 
rem     Control.bat (opens the page in Internet Explorer, asks for IP address and network mask of the camera)
rem     Control.bat firefox (opens the page in firefox, asks for IP address and network mask of the camera)
rem     Control.bat firefox 192.168.0.1 (opens the page in firefox, doesn't ask for anything, assumes network mask /24)
rem     Control.bat firefox 192.168.0.1 16 (opens the page in firefox, doesn't ask for anything, uses the given network mask /16)
rem
rem For ease of use, you can make a shortcut to this .bat file that will launch it with your favorite parameters.

set ip= "192.168.54.1"
set mask="12"
set udpinit="60606"
set blue="false"
set yolo="false"
set notdev="true"

set scriptdir=%~dp0
set opencvdir=C:\\opencv-4.3.0\\opencv\\build\\java\\x64

if not "%1"=="" (
    set ip=%1
)

if not "%2"=="" (
    set mask=%2
)

if not "%3"=="" (
    set udpinit=%3
)
if not "%4"=="" (
    set blue=%4
)
if not "%5"=="" (
    set yolo=%5
)
if not "%6"=="" (
    set notdev=%6
)




rem if "%ip%" == "" goto :noip

start "" java -Xms512M -Xmx1024M -Djava.library.path=%opencvdir% ^
-jar Lumix_wifi_tether.jar %ip% %mask% %udpinit% %blue% %yolo% %notdev%
goto :eof

rem :noip
rem start "" "%browser%" "file://%scriptdir%Control.html"
rem start "" java -jar StreamViewer.jar

