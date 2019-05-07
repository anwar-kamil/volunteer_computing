#include<time.h>
#include<stdio.h>

int main()
{
time_t startTime = time(NULL); // return current time in seconds
int numPackets = 0;
while (time(NULL) - startTime < 60)
{
printf("Sending %d packets\n", numPackets);
    numPackets++;
}
printf("Sent %d packets\n", numPackets);
return 0;
}
