NETWORK HOMEWORK 1 CODE <br/>
set up another ip for PC:<br/>
<li>For linux, input: sudo ip addr add 192.168.0.144/24 network_card_name<br>
And we will have 2 ip addresses in our pc. <br><br>
<li>Due to the failure of using multiple threads, 
there is no router, we have tried to add router, but the situation is that sender
and receiver which are using different IP with same port 5000 share one router object,
however, it is like <br>
<br>
<li>comprehension of selective repeat:<br>
for example, we have an array data: String[] data = new data[N]; 0 =< i < j
, window [0..j]
is using Go_back_N, when the i th packet lost, the sender has to retransmit
the packets from i to j, but if it is for selective repeat, the sender only requires
to send the lost packet. 
<br><br>
<li>It is not necessary to use timer for each packet, since the response from 
receiver only has NACK that means the receiver only tells the sender which 
packet is lost. In this case, timer makes no sense, what I need is the lost 
packet sequence number. 