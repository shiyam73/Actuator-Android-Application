•	In a master/slave model of communication, the smart phone will be the master and the actuator will be the slave

•	The smart phone will initiate all communication to the actuator. Having initiated the smart phone will wait to receive response

•	A simple command based communication protocol will be implemented

      1.Scheduling
I.	Two schedules are implemented. Week day and week end. The smart phone will gather the input of From date & time, and To date & time, and initiate the connection to the actuator. The actuator will respond saying "Ready" (meaning Ready to receive instructions)

II.	Smart phone will send schedule information for both FROM & TO in a CRON TAB format as below. 
Minute (0-59)
Hour (0 -23)
day of month (1- 31)
month (1-12) 

III.	Actuator will receive, process schedule with exception reporting, and if the format is acceptable, it will store the schedule in its registers and operate a clock to actually execute the schedule when the time arrives.

IV.	Smart phone will store a copy of the schedule and perform validation of the inputted schedule so that a duplicate scheduling request can be avoided. By doing so, the smart phone will save a round trip to the actuator to query the schedule.

       2. Open instruction
I.	Smart phone will initiate this command with a string "Open". 

II.	Actuator will acknowledge receipt of command

III.	Smart phone will simulate the turning of the stepper motor, by moving a visible meter like widget on screen with the prior knowledge that it will take approximately 60 seconds to complete the action. As it gets closer to 60 seconds, the smart phone will initiate a valve state readout and receive the answer "Open" (completed) or "Opening" from the actuator. If the answer is "Open" the smart phone will end the simulation by positioning the needle of the meter widget at the end point to indicate completed action. If the answer is "Opening", smart phone will continue simulating the movement.

        3. Close instruction
Will behave the same way as #2 (Open) only in the reverse direction.

        4. Opening , Closing state
These are read out queries, where the actuator will respond with the words "Opening" or "Closing" depending on the status.

        5. Interrogate and set actuator time
The smart phone will initiate both queries in that order. If the response to the question "what time is it" is consistent with the smart phone's time, then the smart phone will not set the time. Otherwise it will initiate a dialog to set the time, and the actuator will store the time in UTC with a conversion to local time for display purposes.

        6. Set WEP key - WIFI Direct only feature

      This is a feature using WIFI direct to initialize the WIFI connectivity of the actuator. To be able to connect to the WIFI router at home, the actuator requires the pass phrase aka WEP key to be available. Using this interaction the smart phone will initiate a communication with the actuator for the first time (and possibly subsequently as well), over WIFI Direct and transmit the WEP key thus entered on the smart phone to the actuator. The actuator will store this key for all future TCP/IP , WIFI based internet communication. For the use of WIFI Direct there will be no internet communication available and it is necessary for the smart phone and the actuator device to be within WIFI Direct range (approximate 600 ft or 200 meters)
