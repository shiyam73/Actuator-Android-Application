<?php
error_reporting(E_ALL);

/* Allow the script to hang around waiting for connections. */
set_time_limit(0);

/* Turn on implicit output flushing so we see what we're getting
 * as it comes in. */
ob_implicit_flush();

$myFile = "IDS.txt";
$myFile1 = "Schedule.txt";
$myFile2 = "date.txt";
static $response="SERVER LIVE";
static $response1="";
static $i = 0;
static $x=0;
static $file="";
static $a=0;
$home = $_SERVER['DOCUMENT_ROOT'];
$address = '127.0.0.1';
$port = 10002;

if (($sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === false) {
    echo "socket_create() failed: reason: " . socket_strerror(socket_last_error()) . "\n";
}

if (socket_bind($sock, $address, $port) === false) {
    echo "socket_bind() failed: reason: " . socket_strerror(socket_last_error($sock)) . "\n";
}

if (socket_listen($sock, 5) === false) {
    echo "socket_listen() failed: reason: " . socket_strerror(socket_last_error($sock)) . "\n";
}

do {
    if (($msgsock = socket_accept($sock)) === false) {
        echo "socket_accept() failed: reason: " . socket_strerror(socket_last_error($sock)) . "\n";
        break;
    }
    /* Send instructions. */
    $msg = "\nWelcome to the PHP Test Server. \n" .
        "To quit, type 'quit'. To shut down the server type 'shutdown'.\n";
    //socket_write($msgsock, $msg, strlen($msg));

    do {
        if (false === ($buf = socket_read($msgsock, 2048, PHP_NORMAL_READ))) {
            echo "socket_read() failed: reason: " . socket_strerror(socket_last_error($msgsock)) . "\n";
            break 2;
        }
        if (!$buf = trim($buf)) {
            continue;
        }
        if ($buf == 'quit') {
            break;
        }
        if ($buf == 'shutdown') {
            socket_close($msgsock);
            break 2;
        }
        
        echo "BUF ".$buf."\r\n";

            $rec = explode("|",$buf);
             if($rec[0] == 'KEY')
            {
              $res = $rec[1];

              echo $res." -key\r\n";
              if(!file_exists($myFile))
              {
                  $fh = fopen($myFile, 'w') or die("can't open file\n");
                  $count1 = fwrite($fh, $res."\r\n");
                 // echo $res[0]."\r\n".$res[1];
                  if($count1>0)
                  $response = "WEP Key Stored";
                  fclose($fh);
              }
              else
              {
                  $fh = fopen($myFile, 'a') or die("can't open file\n");
                  $count1 = fwrite($fh, $res."\r\n");
                 // echo $res[0]."\r\n".$res[1];
                  if($count1>0)
                  $response = "WEP Key Stored";
                  fclose($fh);
              }
            }

            if($rec[0] == 'SCHEDULE')
            {

              $from =  explode("-", $rec[1]);
              $end =  explode("-", $rec[2]);

              /*NEW*/
              //$devId =  $rec[3];

              $res1 = $from[5]." ".$from[4]." ".$from[1]." ".$from[0]." ".$from[3]." ".$home."/suSocket.php"." ".">/dev/null 2>&1";;
              $res2 = $end[5]." ".$end[4]." ".$end[1]." ".$end[0]." ".$end[3]." ".$home."/suSocket.php"." ".">/dev/null 2>&1";;

              /*NEW*/
              //$myFile1 = "Actuator_".$devId.".txt";

             // $file = "Registry_".$devId.".txt";

              echo "Inside schedule ".$myFile1."\r\n";

              $fh1 = fopen($myFile1, 'w') or die("can't open file\n");
              $count1 = fwrite($fh1, $res1."\r\n".$res2);

              if($count1>0)
              $response = "Data stored successfully".$home;
              fclose($fh1);
            }
            
            if($rec[0] == 'MESS')
            {




                sleep(5);
               if($x<13)
               {
               $response = $x;
               echo "I= ".$x."\r\n";
               $x++;
               }



            }

            if($rec[0] == 'DATE')
            {

              $mess = $rec[1];

              $fh1 = fopen($myFile2, 'w') or die("can't open file\n");
              $count1 = fwrite($fh1, $rec[1]);

              if($count1>0)
              $response = "Date and Time synchornised";
              fclose($fh1);


            }



               $a = socket_write($msgsock, $response, strlen($response));


            if($a>0)
                  {

                //    $response="Server Live";
             //   unset($clients[$key]);
               // socket_close($client);
              //  break;
                     if($x>=13)
              {
                  $x=0;
                  echo "X=".$x."\r\n";
              }

              echo "IF\r\n";
              break;
            }
            else
            {
              echo "ELSE\r\n";
            }

      //  $talkback = "PHP: You said '$buf'.\n";
     //   socket_write($msgsock, $talkback, strlen($talkback));
      //  echo "$buf\n";
    } while (true);
    socket_close($msgsock);
} while (true);

socket_close($sock);


?>