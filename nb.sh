#!/bin/sh

whlie :
do
  echo "Please input appName, required android_main.apk, android_truffle.apk, android_satellite.apk, only" 
  echo -n "appName:"
  read appname
  if [[$appname!="android_main.apk"]||[$appname!="android_truffle.apk"]||[$appname!="android_truffle.apk"]]; then
  	echo "appName is not valid"
  else
  	echo appname
  	break;
  fi
done
whlie :
do
  echo "Please input mode,required prod, pre, beta only" 
  echo -n "mode:"
  read mode
  if [[$mode!="prod"]||[$appname!="pre"]||[$appname!="beta"]]; then
  	echo "mode is not valid"
  else
  	echo mode
  	break;
  fi
done
whlie :
do
  echo "Please input mode,required prod, pre, beta only" 
  echo -n "mode:"
  read mode
  if [[$mode!="prod"]||[$appname!="pre"]||[$appname!="beta"]]; then
  	echo "mode is not valid"
  else
  	echo $mode
  	break;
  fi
done





# curl -v   
#         -H "Authorization: Bearer ZOltRGAw0xa3h130U8KGIkAhFJ5LTGx" 
#         -F client_id=iktm4LSxEszshTDrn46GNvh4V6aN6m9 
#         -F client_secret=TePT3cChX8DC0sENA12t978rk2keKM4 
#         -F newForceVersion=5 
#         -F newVersion=6 
#         -F file=@'meican-2.2.5-2015-12-10.apk' 
#         -F mode=prod 
#         -F appName=android_main.apk 
#         https://localapi.meican.com/v2.1/apps/uploadandroid

