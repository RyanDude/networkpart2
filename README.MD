NETWORK HOMEWORK 1 CODE <br/>
set up another ip for PC:<br/>
For linux, input: sudo ip addr add 192.168.0.144/24 network_card_name<br>
And we will have 2 ip addresses in our pc. <br><br>
Due to the failure of using multiple threads, 
there is no router, we have tried to add router, but the situation is that sender
and receiver which are using different IP with same port 5000 share one router object,
however, it is like 